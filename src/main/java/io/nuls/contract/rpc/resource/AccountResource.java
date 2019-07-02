package io.nuls.contract.rpc.resource;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.nuls.contract.model.RpcResult;

@JsonRpcService("/account")
public interface AccountResource {

    public String test(@JsonRpcParam(value = "id") long id);

    public RpcResult createAccount(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "password")String password);

    public RpcResult getAccount(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "assetId")int assetId,@JsonRpcParam(value = "address")String address);

    public RpcResult exportAccountKeyStore(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "address")String address, @JsonRpcParam(value = "password")String password,@JsonRpcParam(value = "filePath")String filePath);

    public RpcResult importAccountByKeystore(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "password")String password,@JsonRpcParam(value = "keyStore")String keyStore,@JsonRpcParam(value = "overwrite")boolean overwrite);

    public RpcResult importAccountByPriKey(@JsonRpcParam(value = "chainId")int chainId,@JsonRpcParam(value = "priKey")String priKey, @JsonRpcParam(value = "password")String password, @JsonRpcParam(value = "overwrite")boolean overwrite);

    public RpcResult exportPriKeyByAddress(@JsonRpcParam(value = "chainId")int chainId,@JsonRpcParam(value = "address")String address,@JsonRpcParam(value = "password")String password);

}
