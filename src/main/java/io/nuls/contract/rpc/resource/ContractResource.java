package io.nuls.contract.rpc.resource;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.nuls.contract.model.RpcResult;

import java.math.BigInteger;

@JsonRpcService("/contract")
public interface ContractResource {
    public String test(@JsonRpcParam(value = "id") long id);

    public String createContractForTest(@JsonRpcParam(value = "id") int id,@JsonRpcParam(value = "contractCode") String contractCode);

    public RpcResult createContract(int chainId, int assetChainId, int assetId, String sender,String password, String contractCode, Object[] args, long gasLimit, long price,String remark);

    public String callContract(int chainId, int assetChainId, int assetId, String sender, String contractCode,BigInteger value,String methodName, String methodDesc,Object[] args, long gasLimit, long price);

    public String deleteContract(int chainId, int assetChainId, int assetId, String sender, String contractCode);

}
