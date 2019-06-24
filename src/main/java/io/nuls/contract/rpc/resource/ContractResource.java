package io.nuls.contract.rpc.resource;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

import java.math.BigInteger;

@JsonRpcService("/contract")
public interface ContractResource {
    public String test(@JsonRpcParam(value = "id") long id);

    public String createContract(int chainId,int assetChainId,int assetId,String sender,String contractCode,Object[] args,long gasLimit,long price);

    public String callContract(int chainId, int assetChainId, int assetId, String sender, String contractCode,BigInteger value,String methodName, String methodDesc,Object[] args, long gasLimit, long price);

    public String deleteContract(int chainId, int assetChainId, int assetId, String sender, String contractCode);

}
