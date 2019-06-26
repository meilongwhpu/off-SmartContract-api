package io.nuls.contract.rpc.resource;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/account")
public interface AccountResource {

    public String createAccount(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "password")String password);
}
