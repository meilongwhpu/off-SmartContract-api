package io.nuls.contract.rpc.resource.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import io.nuls.base.RPCUtil;
import io.nuls.base.basic.AddressTool;
import io.nuls.contract.account.model.bo.Account;
import io.nuls.contract.account.model.bo.AccountInfo;
import io.nuls.contract.account.model.po.AccountKeyStoreDto;
import io.nuls.contract.model.RpcErrorCode;
import io.nuls.contract.model.RpcResult;
import io.nuls.contract.model.RpcResultError;
import io.nuls.contract.rpc.resource.AccountResource;
import io.nuls.contract.service.AccountService;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AutoJsonRpcServiceImpl
public class AccountResourceImpl implements AccountResource {

    @Autowired
    private AccountService accountService;

    @Override
    public RpcResult createAccount(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "password")String password) {
        RpcResult result = new RpcResult(true);
        try{
            Account account=accountService.createAccount(chainId,password);
            result.setResult(account.getAddress().toString());
        }catch (Exception e){
            RpcResultError error = new RpcResultError();
            error.setMessage(e.getMessage());
            result.setError(error);
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public RpcResult getAccount(int chainId, String address) {
        RpcResult result = new RpcResult(true);
        if (address == null) {
            return RpcResult.paramError("[address] is not null");
        }
        if (!AddressTool.validAddress(chainId, address)) {
            return RpcResult.paramError("[address] is inValid");
        }
        Account account=accountService.getAccount(chainId,address);
        if(account!=null){
            AccountInfo accountInfo =new AccountInfo(account);
            result.setResult(accountInfo);
        }else {
            RpcResultError error = new RpcResultError();
            error.setMessage("account is not exist");
            result.setError(error);
            result.setSuccess(false);
        }
        return result;
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
            result.setResult(account.getAddress().toString());
        } catch (NulsException e){
            return RpcResult.failed(RpcErrorCode.IMPORT_ACCOUNT_KEYSTORE_ERROR);
        }catch (IOException e) {
            return RpcResult.failed(RpcErrorCode.ACCOUNTKEYSTORE_FILE_DAMAGED);
        }
        return result;
    }
}
