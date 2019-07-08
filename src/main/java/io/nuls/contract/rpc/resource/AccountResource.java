package io.nuls.contract.rpc.resource;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.nuls.contract.account.model.bo.AccountInfo;

import java.util.Map;

@JsonRpcService("/account")
public interface AccountResource {

    public Map test(@JsonRpcParam(value = "id") long id);

    public AccountInfo test2(@JsonRpcParam(value = "id") long id);

}
