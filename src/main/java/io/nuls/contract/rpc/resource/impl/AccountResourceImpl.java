package io.nuls.contract.rpc.resource.impl;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import io.nuls.base.RPCUtil;
import io.nuls.base.basic.AddressTool;
import io.nuls.contract.account.model.bo.Account;
import io.nuls.contract.account.model.bo.AccountInfo;
import io.nuls.contract.account.model.po.AccountKeyStoreDto;
import io.nuls.contract.model.BalanceInfo;
import io.nuls.contract.model.RpcErrorCode;
import io.nuls.contract.model.RpcResult;
import io.nuls.contract.model.RpcResultError;
import io.nuls.contract.rpc.resource.AccountResource;
import io.nuls.contract.service.AccountKeyStoreService;
import io.nuls.contract.service.AccountService;
import io.nuls.core.exception.NulsException;
import io.nuls.core.exception.NulsRuntimeException;
import io.nuls.core.log.Log;
import io.nuls.core.parse.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AutoJsonRpcServiceImpl
public class AccountResourceImpl implements AccountResource {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountKeyStoreService accountKeyStoreService;

    @Override
    public String test(@JsonRpcParam(value = "id") long id) {
        try{
            BalanceInfo info= accountService.getAccountBalance(2,1,"tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD");
            System.out.println(info.toString());
            Log.info("input : "+id);
            return "test"+id;
        }catch (JsonRpcClientException e){
            Log.info("---1----"+e.getData().toString());
            return e.getData()+"--"+e.getMessage();
        }catch (Throwable e){
            Log.info("---2----"+e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public RpcResult createAccount(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "password")String password) {
        Map<String,String> map = new HashMap<String,String>();
        RpcResult result = new RpcResult(true);
        try{
            Account account=accountService.createAccount(chainId,password);
            map.put("address",account.getAddress().toString());
            result.setResult(map);
        }catch (Exception e){
            Log.error(e);
            RpcResultError error = new RpcResultError();
            error.setMessage(e.getMessage());
            result.setError(error);
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public RpcResult getAccount(int chainId, int assetId,String address) {
        RpcResult result = new RpcResult(true);
        if (address == null) {
            return RpcResult.paramError("[address] is not null");
        }
        if (!AddressTool.validAddress(chainId, address)) {
            return RpcResult.paramError("[address] is inValid");
        }
        Account account=accountService.getAccount(chainId,address);
        if(account!=null){
            try {
                AccountInfo accountInfo= new AccountInfo();
                accountInfo.setAddress(account.getAddress().toString());
                BalanceInfo balanceInfo=accountService.getAccountBalance(chainId, assetId,address);
                accountInfo.setBalance(balanceInfo.getBalance());
                accountInfo.setTotalBalance(balanceInfo.getTotalBalance());
                result.setResult(accountInfo);
            } catch (Throwable e) {
                Log.error(e);
                return RpcResult.paramError(e.getMessage());
            }
        }else {
            RpcResultError error = new RpcResultError();
            error.setMessage("account is not exist");
            result.setError(error);
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public RpcResult exportAccountKeyStore(int chainId, String address, String password, String filePath) {
        if (address == null ) {
            return RpcResult.paramError("[address] is not null");
        }
        if (password == null ) {
            return RpcResult.paramError("[password] is not null");
        }
        if (filePath == null ) {
            return RpcResult.paramError("[filePath] is not null");
        }
        String backupFileName = accountKeyStoreService.backupAccountToKeyStore(filePath,chainId, address, password);
        Map<String,String> map = new HashMap<String,String>();
        map.put("path",backupFileName);
        return RpcResult.success(map);
    }

    @Override
    public RpcResult importAccountByKeystore(int chainId, String password, String keyStore, boolean overwrite) {
        RpcResult result = new RpcResult(true);
        if (password == null ) {
            return RpcResult.paramError("[password] is not null");
        }
        if (keyStore == null) {
            return RpcResult.paramError("[keyStore] is not null");
        }

        try {
            AccountKeyStoreDto accountKeyStoreDto = JSONUtils.json2pojo(new String(RPCUtil.decode(keyStore)), AccountKeyStoreDto.class);
            Account account=  accountService.importAccountByKeyStore(accountKeyStoreDto.toAccountKeyStore(), chainId, password, overwrite);
            Map<String,String> map = new HashMap<String,String>();
            map.put("address",account.getAddress().toString());
            result.setResult(map);
        } catch (NulsRuntimeException e){
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }catch (NulsException e){
            Log.error(e);
            return RpcResult.failed(RpcErrorCode.IMPORT_ACCOUNT_KEYSTORE_ERROR);
        }catch (IOException e) {
            Log.error(e);
            return RpcResult.failed(RpcErrorCode.ACCOUNTKEYSTORE_FILE_DAMAGED);
        }
        return result;
    }

    @Override
    public RpcResult importAccountByPriKey(int chainId, String priKey, String password, boolean overwrite) {
        if (priKey == null ) {
            return RpcResult.paramError("[priKey] is not null");
        }
        if (password == null) {
            return RpcResult.paramError("[password] is not null");
        }

        try {
           Account account= accountService.importAccountByPrikey(chainId, priKey, password, overwrite);
            Map<String,String> map = new HashMap<String,String>();
            map.put("address",account.getAddress().toString());
            return RpcResult.success(map);
        } catch (NulsException e) {
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }
    }

    @Override
    public RpcResult exportPriKeyByAddress(int chainId, String address, String password) {
        if (password == null ) {
            return RpcResult.paramError("[password] is not null");
        }
        if (address == null) {
            return RpcResult.paramError("[address] is not null");
        }
        try {
            String unencryptedPrivateKey= accountService.getPrivateKey(chainId,address,password);
            Map<String,String> map = new HashMap<String,String>();
            map.put("privateKey",unencryptedPrivateKey);
            RpcResult.success(map);
        } catch (NulsException e) {
            Log.error(e);
            return RpcResult.paramError(e.getMessage());
        }
        return null;
    }
}
