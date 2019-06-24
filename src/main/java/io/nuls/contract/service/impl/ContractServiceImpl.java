package io.nuls.contract.service.impl;

import io.nuls.contract.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {

    private final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    private HttpClient httpClient;

    @Override
    public Map getContractConstructor(int chainId, String contractCode) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("getContractConstructor",new Object[]{chainId,contractCode}, Map.class);
        }catch (Throwable e){
            logger.error("call api-moudle: getContractConstructor error",e);
        }
        return result;
    }

    @Override
    public Map validateContractCreate(int chainId, String sender, long gasLimit, long price, String contractCode, Object[] args) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("validateContractCreate",new Object[]{chainId,sender,gasLimit,price,contractCode}, Map.class);
        }catch (Throwable e){
            logger.error("call api-moudle: validateContractCreate error",e);
        }
        return result;
    }

    @Override
    public Map imputedContractCreateGas(int chainId, String sender, String contractCode, Object[] args) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("imputedContractCreateGas",new Object[]{chainId,sender,contractCode,args}, Map.class);
        }catch (Throwable e){
            logger.error("call api-moudle: imputedContractCreateGas error",e);
        }
        return result;
    }

    @Override
    public List<String> getContractMethodArgsTypes(int chainId, String contractAddress, String methodname) {
        List<String> result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("getContractMethodArgsTypes",new Object[]{chainId,contractAddress,methodname}, List.class);
        }catch (Throwable e){
            logger.error("call api-moudle: getContractMethodArgsTypes error",e);
        }
        return result;
    }

    @Override
    public Map validateContractCall(int chainId, String sender, BigInteger value, long gasLimit, long price, String contractAddress, String methodName, String methodDesc, Object[] args) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("imputedContractCreateGas",new Object[]{chainId,sender,value,gasLimit,price,contractAddress,methodName,methodDesc,args}, Map.class);
        }catch (Throwable e){
            logger.error("call api-moudle: imputedContractCreateGas error",e);
        }
        return result;
    }

    @Override
    public Map imputedContractCallGas(int chainId, String sender, BigInteger value, String contractAddress, String methodName, String methodDesc, Object[] args) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("imputedContractCallGas",new Object[]{chainId,sender,value,contractAddress,methodName,methodDesc,args}, Map.class);
        }catch (Throwable e){
            logger.error("call api-moudle: imputedContractCallGas error",e);
        }
        return result;
    }

    @Override
    public Map validateContractDelete(int chainId, String sender, String contractAddress) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("validateContractDelete",new Object[]{chainId,sender,contractAddress}, Map.class);
        }catch (Throwable e){
            logger.error("call api-moudle: validateContractDelete error",e);
        }
        return result;
    }
}
