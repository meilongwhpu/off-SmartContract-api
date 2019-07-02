package io.nuls.contract.service;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import io.nuls.contract.model.ContractInfo;

import java.math.BigInteger;
import java.util.Map;

public interface ContractService {

    public String[] getContractConstructorArgsTypes(int chainId,String contractCode) throws JsonRpcClientException,Throwable;

    public Map getContractConstructor(int chainId,String contractCode) throws JsonRpcClientException,Throwable;

    public boolean validateContractCreate(int chainId,String sender,long gasLimit,long price,String contractCode,Object[] args) throws JsonRpcClientException,Throwable;

    public int imputedContractCreateGas(int chainId,String sender,String contractCode,Object[] args) throws JsonRpcClientException,Throwable;

    public String[] getContractMethodArgsTypes(int chainId,String contractAddress,String methodname) throws JsonRpcClientException,Throwable;

    public boolean validateContractCall(int chainId, String sender, BigInteger value,long gasLimit,long price,String contractAddress,String methodName,String methodDesc,Object[] args) throws JsonRpcClientException,Throwable;

    public int imputedContractCallGas(int chainId ,String sender,BigInteger  value,String contractAddress,String methodName,String methodDesc,Object[] args) throws JsonRpcClientException,Throwable;

    public boolean validateContractDelete(int chainId ,String sender,String contractAddress) throws JsonRpcClientException,Throwable;

    public String invokeView(int chainId, Object contractAddress, Object methodName, Object methodDesc, Object args)throws JsonRpcClientException,Throwable;

    public ContractInfo getContract(int chainId, String contractAddress)throws JsonRpcClientException, Throwable;
}
