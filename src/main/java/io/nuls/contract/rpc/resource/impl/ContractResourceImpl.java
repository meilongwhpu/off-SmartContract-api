package io.nuls.contract.rpc.resource.impl;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import io.nuls.base.RPCUtil;
import io.nuls.base.basic.AddressTool;
import io.nuls.base.data.Address;
import io.nuls.base.data.CoinData;
import io.nuls.base.data.NulsHash;
import io.nuls.base.signture.P2PHKSignature;
import io.nuls.base.signture.TransactionSignature;
import io.nuls.contract.account.model.bo.Account;
import io.nuls.contract.account.utils.AccountTool;
import io.nuls.contract.helper.ContractTxHelper;
import io.nuls.contract.model.BalanceInfo;
import io.nuls.contract.model.ContractInfo;
import io.nuls.contract.model.RpcErrorCode;
import io.nuls.contract.model.RpcResult;
import io.nuls.contract.model.tx.CallContractTransaction;
import io.nuls.contract.model.tx.CreateContractTransaction;
import io.nuls.contract.model.tx.DeleteContractTransaction;
import io.nuls.contract.model.txdata.CallContractData;
import io.nuls.contract.model.txdata.CreateContractData;
import io.nuls.contract.model.txdata.DeleteContractData;
import io.nuls.contract.rpc.resource.ContractResource;
import io.nuls.contract.service.AccountService;
import io.nuls.contract.service.ContractService;
import io.nuls.contract.service.TransactionService;
import io.nuls.contract.utils.ContractUtil;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.log.Log;
import io.nuls.core.model.FormatValidUtils;
import io.nuls.core.model.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AutoJsonRpcServiceImpl
public class ContractResourceImpl implements ContractResource {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ContractTxHelper contractTxHelper;

    @Override
    public RpcResult createContract(int chainId, int assetId, String sender,String password, String contractCode,String alias, Object[] args, long gasLimit, long price,String remark) {
        if (gasLimit < 0 || price < 0) {
            return RpcResult.paramError("[price/gasLimit] is inValid");
        }
        if (!AddressTool.validAddress(chainId, sender)) {
            return RpcResult.paramError("[address] is inValid");
        }
        if (StringUtils.isBlank(contractCode)) {
            return RpcResult.failed(RpcErrorCode.NULL_PARAMETER);
        }
        if(FormatValidUtils.validAlias(alias)){
            return RpcResult.paramError("[alias] is inValid");
        }

        Account account=accountService.getAccount(chainId,sender);
        if(account==null){
            return RpcResult.failed(RpcErrorCode.ACCOUNT_IS_NOT_EXIST);
        }
        //账户密码验证
        boolean validate= accountService.validationPassword(chainId,sender,password);
        if(!validate){
            return RpcResult.failed(RpcErrorCode.VALIADE_PW_ERROR);
        }
        byte[] contractCodeBytes = HexUtil.decode(contractCode);
        String[] argTypes=null;

        boolean isSuccess=true;
        try{
            argTypes=contractService.getContractConstructorArgsTypes(chainId,contractCode);
            if (argTypes==null){
                return RpcResult.paramError("get contract constructor wrong");
            }
            isSuccess=contractService.validateContractCreate(chainId,sender,gasLimit,price,contractCode,args);
        }catch (JsonRpcClientException e){
            Log.error(e);
            return RpcResult.paramError(e.getData().toString());
        }catch (Throwable e){
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }

        if(isSuccess){
            Address contract = AccountTool.createContractAddress(chainId);
            byte[] contractAddressBytes = contract.getAddressBytes();

            String contractAddress=AddressTool.getStringAddressByBytes( contract.getAddressBytes());
            byte[] senderBytes = AddressTool.getAddress(sender);

            CreateContractTransaction tx = new CreateContractTransaction();
           try{
               int gamLimit=contractService.imputedContractCreateGas(chainId,sender,contractCode,args);
                 if (StringUtils.isNotBlank(remark)) {
                     tx.setRemark(remark.getBytes(StandardCharsets.UTF_8));
                 }
                 tx.setTime(System.currentTimeMillis()/ 1000);

                 String[][] convertArgs= ContractUtil.twoDimensionalArray(args, argTypes);
                 //组装txData
                 CreateContractData createContractData= contractTxHelper.getCreateContractData(senderBytes,contractAddressBytes,BigInteger.ZERO,gamLimit,price,contractCodeBytes,alias,convertArgs);

                 // 计算CoinData
                 BalanceInfo balanceInfo=accountService.getAccountBalance(chainId,assetId,sender);
                 CoinData coinData = contractTxHelper.makeCoinData(chainId,assetId,senderBytes, contractAddressBytes,gasLimit,price,BigInteger.ZERO,tx.size(),createContractData,balanceInfo.getNonce(),balanceInfo.getBalance());
                 if(coinData==null){
                     return RpcResult.failed(RpcErrorCode.INSUFFICIENT_BALANCE);
                 }

                 tx.setTxDataObj(createContractData);
                 tx.setCoinDataObj(coinData);
                 tx.serializeData();
                 tx.setHash(NulsHash.calcHash(tx.serializeForHash()));

               // 签名、发送交易到交易模块
                 P2PHKSignature signature = accountService.signDigest(tx.getHash().getBytes(),chainId,sender,password);
                 if (null == signature || signature.getSignData() == null) {
                     return RpcResult.failed(RpcErrorCode.SIGNATURE_ERROR);
                 }
               TransactionSignature transactionSignature = new TransactionSignature();
               List<P2PHKSignature> p2PHKSignatures = new ArrayList<>();
               p2PHKSignatures.add(signature);
               transactionSignature.setP2PHKSignatures(p2PHKSignatures);
               tx.setTransactionSignature(transactionSignature.serialize());
               String txData = RPCUtil.encode(tx.serialize());
               boolean result= transactionService.broadcastTx(chainId,txData);
               if(result){
                   Map<String,String> map = new HashMap<String,String>();
                   map.put("contractAddress",contractAddress);
                   return RpcResult.success(map);
               }else {
                   return RpcResult.failed(RpcErrorCode.BROADCAST_TX_ERROR);
               }
             }catch (JsonRpcClientException e){
               Log.error(e);
               return RpcResult.paramError(e.getData().toString());
           }catch (Throwable e){
               Log.error(e);
               return RpcResult.failed(RpcErrorCode.CONTRACT_TX_CREATE_ERROR);
           }
        }else {
            return RpcResult.failed(RpcErrorCode.CONTRACT_VALIDATION_FAILED);
        }
    }

    @Override
    public RpcResult getContractConstructor(int chainId, String contractCode) {
        try {
            Map result=contractService.getContractConstructor(chainId,contractCode);
            if(result!=null){
                return RpcResult.success(result);
            }else{
                return RpcResult.paramError("get Contract Constructor info  form service error");
            }
        } catch (Throwable e) {
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }
    }

    @Override
    public RpcResult getContract(int chainId,String contractAddress) {
        try {
            ContractInfo contractInfo=contractService.getContract(chainId,contractAddress);
            if(contractInfo!=null){
                Map<String,ContractInfo> map = new HashMap<String,ContractInfo>();
                map.put("contractInfo",contractInfo);
                return RpcResult.success(map);
            }else {
                return RpcResult.paramError("get Contract info form service  error");
            }
        } catch (Throwable e) {
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }
    }

    @Override
    public RpcResult callContract(int chainId, int assetId, String sender,String password, String contractAddress, BigInteger value, String methodName, String methodDesc, Object[] args, long gasLimit, long price,String remark) {
        if (value.compareTo(BigInteger.ZERO) < 0) {
            return RpcResult.paramError("[value] is inValid");
        }
        if (gasLimit < 0 || price < 0) {
            return RpcResult.paramError("[price/gasLimit] is inValid");
        }
        if (!AddressTool.validAddress(chainId, sender)) {
            return RpcResult.paramError("[address] is inValid");
        }
        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return RpcResult.paramError("[contractAddress] is inValid");
        }
        if (StringUtils.isBlank(methodName)) {
            return RpcResult.paramError("[methodName] is inValid");
        }
        //账户密码验证
        boolean validate= accountService.validationPassword(chainId,sender,password);
        if(!validate){
            return RpcResult.failed(RpcErrorCode.VALIADE_PW_ERROR);
        }
        byte[] contractAddressBytes = AddressTool.getAddress(contractAddress);
        String[] argsTypes=null;
        try{
            argsTypes= contractService.getContractMethodArgsTypes(chainId, contractAddress, methodName);
        }catch (JsonRpcClientException e){
            Log.error(e);
            return RpcResult.paramError(e.getData().toString());
        }catch (Throwable e){
            return RpcResult.failed(RpcErrorCode.GET_CONTRACT_METHODARGS_EEROR);
        }
        String[][] convertArgs = ContractUtil.twoDimensionalArray(args, argsTypes);
        try{
            validate=contractService.validateContractCall(chainId,sender,value,gasLimit,price,contractAddress,methodName,methodDesc,args);
        }catch (JsonRpcClientException e){
            Log.error(e);
            return RpcResult.paramError(e.getData().toString());
        }catch (Throwable e){
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }
        if(!validate){
            return RpcResult.failed(RpcErrorCode.VALIADE_CONTRACT_CALL_ERROR);
        }

        CallContractTransaction tx = new CallContractTransaction();
        if (StringUtils.isNotBlank(remark)) {
            tx.setRemark(remark.getBytes(StandardCharsets.UTF_8));
        }
        tx.setTime(System.currentTimeMillis()/ 1000);
        byte[] senderBytes = AddressTool.getAddress(sender);

        try{
            //组装txData
            CallContractData createContractData= contractTxHelper.getCallContractData(senderBytes, contractAddressBytes,value,gasLimit,price,methodName,methodDesc, convertArgs);

            BalanceInfo balanceInfo=accountService.getAccountBalance(chainId,assetId,sender);
            CoinData coinData = contractTxHelper.makeCoinData(chainId,assetId,senderBytes, contractAddressBytes,gasLimit,price,value,tx.size(),createContractData,balanceInfo.getNonce(),balanceInfo.getBalance());
            if(coinData==null){
                return RpcResult.failed(RpcErrorCode.INSUFFICIENT_BALANCE);
            }
            tx.setTxDataObj(createContractData);
            tx.setCoinDataObj(coinData);

            tx.serializeData();
            tx.setHash(NulsHash.calcHash(tx.serializeForHash()));

            // 签名、发送交易到交易模块
            P2PHKSignature signature = accountService.signDigest(tx.getHash().getBytes(),chainId,sender,password);
            if (null == signature || signature.getSignData() == null) {
                return RpcResult.failed(RpcErrorCode.SIGNATURE_ERROR);
            }
            TransactionSignature transactionSignature = new TransactionSignature();
            List<P2PHKSignature> p2PHKSignatures = new ArrayList<>();
            p2PHKSignatures.add(signature);
            transactionSignature.setP2PHKSignatures(p2PHKSignatures);
            tx.setTransactionSignature(transactionSignature.serialize());
            String txData = RPCUtil.encode(tx.serialize());
            boolean result= transactionService.broadcastTx(chainId,txData);
            if(result){
                Map<String,String> map = new HashMap<String,String>();
                map.put("txHash",tx.getHash().toHex());
                return RpcResult.success(map);
            }else {
                return RpcResult.failed(RpcErrorCode.BROADCAST_TX_ERROR);
            }
        }catch (JsonRpcClientException e){
            Log.error(e);
            return RpcResult.paramError(e.getData().toString());
        }catch (Throwable e) {
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }
    }

    @Override
    public RpcResult deleteContract(int chainId, int assetId, String sender,String password, String contractAddress,String remark) {
        if (!AddressTool.validAddress(chainId, sender)) {
            return RpcResult.paramError("[address] is inValid");
        }
        //账户密码验证
        boolean validate= accountService.validationPassword(chainId,sender,password);
        if(!validate){
            return RpcResult.failed(RpcErrorCode.VALIADE_PW_ERROR);
        }
        //账户密码验证
        validate= accountService.validationPassword(chainId,sender,password);
        if(!validate){
            return RpcResult.failed(RpcErrorCode.VALIADE_PW_ERROR);
        }
        try{
            validate =contractService.validateContractDelete(chainId, sender, contractAddress);
        }catch (JsonRpcClientException e){
            Log.error(e);
            return RpcResult.paramError(e.getData().toString());
        }catch (Throwable e){
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }

        if(!validate){
            return RpcResult.failed(RpcErrorCode.VALIADE_CONTRACT_DELETE_ERROR);
        }
        byte[] contractAddressBytes = AddressTool.getAddress(contractAddress);

        DeleteContractTransaction tx = new DeleteContractTransaction();
        if (StringUtils.isNotBlank(remark)) {
            tx.setRemark(remark.getBytes(StandardCharsets.UTF_8));
        }
        tx.setTime(System.currentTimeMillis()/ 1000);
        byte[] senderBytes = AddressTool.getAddress(sender);
        try {
            DeleteContractData deleteContractData= contractTxHelper.getDeleteContractData(contractAddressBytes,senderBytes);
            BalanceInfo balanceInfo=accountService.getAccountBalance(chainId,assetId,sender);
            CoinData coinData = contractTxHelper.makeCoinData(chainId,assetId,senderBytes, contractAddressBytes,0L, 0L, BigInteger.ZERO,tx.size(),deleteContractData,balanceInfo.getNonce(),balanceInfo.getBalance());
            if(coinData==null){
                return RpcResult.failed(RpcErrorCode.INSUFFICIENT_BALANCE);
            }
            tx.setTxDataObj(deleteContractData);
            tx.setCoinDataObj(coinData);

            tx.serializeData();
            tx.setHash(NulsHash.calcHash(tx.serializeForHash()));

            // 签名、发送交易到交易模块
            P2PHKSignature signature = accountService.signDigest(tx.getHash().getBytes(),chainId,sender,password);
            if (null == signature || signature.getSignData() == null) {
                return RpcResult.failed(RpcErrorCode.SIGNATURE_ERROR);
            }
            TransactionSignature transactionSignature = new TransactionSignature();
            List<P2PHKSignature> p2PHKSignatures = new ArrayList<>();
            p2PHKSignatures.add(signature);
            transactionSignature.setP2PHKSignatures(p2PHKSignatures);
            tx.setTransactionSignature(transactionSignature.serialize());
            String txData = RPCUtil.encode(tx.serialize());
            boolean result= transactionService.broadcastTx(chainId,txData);
            if(result){
                Map<String,String> map = new HashMap<String,String>();
                map.put("msg","delete contract success");
                return RpcResult.success(map);
            }else {
                return RpcResult.failed(RpcErrorCode.BROADCAST_TX_ERROR);
            }
        }catch (JsonRpcClientException e){
            Log.error(e);
            return RpcResult.paramError(e.getData().toString());
        }catch (Throwable e) {
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }
    }

    @Override
    public RpcResult invokeContractViewMethod(int chainId, String contractAddress, String methodName, String methodDesc, Object[] args) {
        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return RpcResult.paramError("[contractAddress] is inValid");
        }
        if (StringUtils.isBlank(methodName)) {
            return RpcResult.paramError("[methodName] is inValid");
        }
        try {
            String invokeResult= contractService.invokeView(chainId,contractAddress,methodName,methodDesc,args);
            Map<String,String> map = new HashMap<String,String>();
            map.put("return",invokeResult);
            return RpcResult.success(map);
        } catch (Throwable e) {
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }
    }

    @Override
    public RpcResult getContractMethodArgsTypes(int chainId, String contractAddress, String methodName) {
        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return RpcResult.paramError("[contractAddress] is inValid");
        }
        String[] argsTypes=null;
        try{
            argsTypes= contractService.getContractMethodArgsTypes(chainId, contractAddress, methodName);
        }catch (JsonRpcClientException e){
            Log.error(e);
            return RpcResult.paramError(e.getData().toString());
        }catch (Throwable e){
            return RpcResult.failed(RpcErrorCode.GET_CONTRACT_METHODARGS_EEROR);
        }
        Map<String,String[]> map = new HashMap<String,String[]>();
        map.put("argsTypes",argsTypes);
        return RpcResult.success(map);
    }

}
