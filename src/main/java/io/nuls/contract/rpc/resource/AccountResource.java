package io.nuls.contract.rpc.resource;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.nuls.contract.model.RpcResult;

@JsonRpcService("/account")
public interface AccountResource {

    public RpcResult createAccount(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "password")String password);

    public RpcResult getAccount(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "address")String address);

    public RpcResult importAccountByKeystore(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "password")String password,@JsonRpcParam(value = "keyStore")String keyStore,@JsonRpcParam(value = "overwrite")boolean overwrite);
}
