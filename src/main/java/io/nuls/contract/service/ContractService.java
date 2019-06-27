package io.nuls.contract.service;

import com.googlecode.jsonrpc4j.JsonRpcClientException;

import java.math.BigInteger;
import java.util.Map;

public interface ContractService {

    public String[] getContractConstructor(int chainId,String contractCode) throws JsonRpcClientException,Throwable;

    public boolean validateContractCreate(int chainId,String sender,long gasLimit,long price,String contractCode,Object[] args) throws JsonRpcClientException,Throwable;

    public long imputedContractCreateGas(int chainId,String sender,String contractCode,Object[] args) throws JsonRpcClientException,Throwable;

    public String[] getContractMethodArgsTypes(int chainId,String contractAddress,String methodname) throws JsonRpcClientException,Throwable;

    public boolean validateContractCall(int chainId, String sender, BigInteger value,long gasLimit,long price,String contractAddress,String methodName,String methodDesc,Object[] args) throws JsonRpcClientException,Throwable;

    public Map imputedContractCallGas(int chainId ,String sender,BigInteger  value,String contractAddress,String methodName,String methodDesc,Object[] args) throws JsonRpcClientException,Throwable;

    public boolean validateContractDelete(int chainId ,String sender,String contractAddress) throws JsonRpcClientException,Throwable;
}
