package io.nuls.contract.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ContractService {

    public Map getContractConstructor(int chainId,String contractCode);

    public Map validateContractCreate(int chainId,String sender,long gasLimit,long price,String contractCode,Object[] args);

    public Map imputedContractCreateGas(int chainId,String sender,String contractCode,Object[] args);

    public List<String> getContractMethodArgsTypes(int chainId,String contractAddress,String methodname);

    public Map validateContractCall(int chainId, String sender, BigInteger value,long gasLimit,long price,String contractAddress,String methodName,String methodDesc,Object[] args);

    public Map imputedContractCallGas(int chainId ,String sender,BigInteger  value,String contractAddress,String methodName,String methodDesc,Object[] args);

    public Map validateContractDelete(int chainId ,String sender,String contractAddress);
}
