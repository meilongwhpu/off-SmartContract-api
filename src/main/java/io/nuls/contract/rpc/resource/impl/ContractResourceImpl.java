package io.nuls.contract.rpc.resource.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import io.nuls.base.RPCUtil;
import io.nuls.base.basic.AddressTool;
import io.nuls.base.basic.TransactionFeeCalculator;
import io.nuls.base.data.*;
import io.nuls.contract.account.utils.AccountTool;
import io.nuls.contract.helper.ContractTxHelper;
import io.nuls.contract.model.BalanceInfo;
import io.nuls.contract.model.RpcErrorCode;
import io.nuls.contract.model.RpcResult;
import io.nuls.contract.model.tx.CreateContractTransaction;
import io.nuls.contract.model.txdata.CreateContractData;
import io.nuls.contract.rpc.resource.ContractResource;
import io.nuls.contract.service.AccountService;
import io.nuls.contract.service.ContractService;
import io.nuls.contract.utils.ContractUtil;
import io.nuls.core.basic.Result;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.log.Log;
import io.nuls.core.model.LongUtils;
import io.nuls.core.model.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static io.nuls.contract.constant.ContractConstant.UNLOCKED_TX;

@Service
@AutoJsonRpcServiceImpl
public class ContractResourceImpl implements ContractResource {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractTxHelper contractTxHelper;

    @Override
    public String test(@JsonRpcParam(value = "id") long id) {
       BalanceInfo info= accountService.getAccountBalance(2,1,"tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD");
       System.out.println(info.toString());
        Log.info("input : "+id);
        return "test"+id;
    }

    @Override
    public String createContractForTest(@JsonRpcParam(value = "id") int id,@JsonRpcParam(value = "contractCode") String contractCode) {
        String[] argTypes=contractService.getContractConstructor(id,contractCode);
        return "success";
    }


    @Override
    public RpcResult createContract(int chainId, int assetChainId, int assetId, String sender,String password, String contractCode, Object[] args, long gasLimit, long price,String remark) {
        if (gasLimit < 0 || price < 0) {
            return RpcResult.paramError("[price/gasLimit] is inValid");
        }
        if (!AddressTool.validAddress(chainId, sender)) {
            return RpcResult.paramError("[address] is inValid");
        }
        if (StringUtils.isBlank(contractCode)) {
            return RpcResult.failed(RpcErrorCode.NULL_PARAMETER);
        }
        byte[] contractCodeBytes = HexUtil.decode(contractCode);

        String[] argTypes=contractService.getContractConstructor(chainId,contractCode);
        if (argTypes==null){
            return RpcResult.paramError("get contract constructor wrong");
        }
        String[][] convertArgs = ContractUtil.twoDimensionalArray(args, argTypes);

        boolean isSuccess=contractService.validateContractCreate(chainId,sender,gasLimit,price,contractCode,args);
        if(isSuccess){
            //账户密码验证，待写


            Address contract = AccountTool.createContractAddress(chainId);
            byte[] contractAddressBytes = contract.getAddressBytes();
            byte[] senderBytes = AddressTool.getAddress(sender);

           long gamLimit=contractService.imputedContractCreateGas(chainId,sender,contractCode,args);

            CreateContractTransaction tx = new CreateContractTransaction();
           try{

                 if (StringUtils.isNotBlank(remark)) {
                     tx.setRemark(remark.getBytes(StandardCharsets.UTF_8));
                 }
                 tx.setTime(System.currentTimeMillis()/ 1000);

                 //组装txData
                 CreateContractData createContractData= contractTxHelper.getCreateContractData(senderBytes,contractAddressBytes,BigInteger.ZERO,gamLimit,price,contractCodeBytes,convertArgs);


                 // 计算CoinData
                 BigInteger imputedValue = BigInteger.valueOf(LongUtils.mul(gamLimit, price));
                 BalanceInfo balanceInfo=accountService.getAccountBalance(chainId,assetId,sender);
                 CoinData coinData = new CoinData();
                 CoinFrom coinFrom = new CoinFrom(senderBytes, chainId, assetId, imputedValue, RPCUtil.decode(balanceInfo.getNonce()), UNLOCKED_TX);
                 coinData.addFrom(coinFrom);
                 BigInteger fee = TransactionFeeCalculator.getNormalUnsignedTxFee(tx.size() + contractTxHelper.calcSize(createContractData) + contractTxHelper.calcSize(coinData));
                 // 总花费
                 BigInteger totalValue = imputedValue.add(fee);
                 if (balanceInfo.getBalance().compareTo(totalValue) < 0) {
                     return RpcResult.failed(RpcErrorCode.INSUFFICIENT_BALANCE);
                 }
                 coinFrom.setAmount(totalValue);
                 tx.setTxDataObj(createContractData);
                 tx.setCoinDataObj(coinData);
                 tx.serializeData();

                 tx.setHash(NulsHash.calcHash(tx.serializeForHash()));

               // 签名、发送交易到交易模块
               Result signAndBroadcastTxResult = contractTxHelper.signAndBroadcastTx(chainId, sender, password, tx);

             }catch (IOException e) {
                 return RpcResult.failed(RpcErrorCode.CONTRACT_TX_CREATE_ERROR);
             }


            return RpcResult.success("create contract sucess");
        }else {
            return RpcResult.failed(RpcErrorCode.CONTRACT_VALIDATION_FAILED);
        }

    }

    @Override
    public String callContract(int chainId, int assetChainId, int assetId, String sender, String contractCode, BigInteger value, String methodName, String methodDesc, Object[] args, long gasLimit, long price) {
        return null;
    }

    @Override
    public String deleteContract(int chainId, int assetChainId, int assetId, String sender, String contractCode) {
        return null;
    }
}
