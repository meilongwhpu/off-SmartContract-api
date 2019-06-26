package io.nuls.contract.rpc.resource;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.nuls.contract.model.RpcResult;

import java.math.BigInteger;

@JsonRpcService("/contract")
public interface ContractResource {
    public String test(@JsonRpcParam(value = "id") long id);

    public String createContractForTest(@JsonRpcParam(value = "id") int id,@JsonRpcParam(value = "contractCode") String contractCode);

    public RpcResult createContract(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "assetChainId")int assetChainId, @JsonRpcParam(value = "assetId")int assetId, @JsonRpcParam(value = "sender")String sender,@JsonRpcParam(value = "password")String password, @JsonRpcParam(value = "contractCode")String contractCode, @JsonRpcParam(value = "args")Object[] args, @JsonRpcParam(value = "gasLimit")long gasLimit, @JsonRpcParam(value = "price")long price,@JsonRpcParam(value = "remark")String remark);

    public RpcResult callContract(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "assetChainId")int assetChainId, @JsonRpcParam(value = "assetId")int assetId, @JsonRpcParam(value = "sender")String sender,@JsonRpcParam(value = "password")String password, @JsonRpcParam(value = "contractAddress")String contractAddress,@JsonRpcParam(value = "value")BigInteger value,@JsonRpcParam(value = "methodName")String methodName, @JsonRpcParam(value = "methodDesc")String methodDesc,@JsonRpcParam(value = "args")Object[] args, @JsonRpcParam(value = "gasLimit")long gasLimit, @JsonRpcParam(value = "price")long price,@JsonRpcParam(value = "remark")String remark);

    public RpcResult deleteContract(@JsonRpcParam(value = "chainId")int chainId, @JsonRpcParam(value = "assetChainId")int assetChainId,@JsonRpcParam(value = "assetId")int assetId, @JsonRpcParam(value = "sender")String sender,@JsonRpcParam(value = "password")String password, @JsonRpcParam(value = "contractAddress")String contractAddress,@JsonRpcParam(value = "remark")String remark);

}
