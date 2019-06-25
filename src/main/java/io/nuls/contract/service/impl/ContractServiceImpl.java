package io.nuls.contract.service.impl;

import io.nuls.contract.service.ContractService;
import io.nuls.core.log.Log;
import io.nuls.core.model.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private HttpClient httpClient;

    @Override
    public String[] getContractConstructor(int chainId, String contractCode) {
        String[] types=null;
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("getContractConstructor",new Object[]{chainId,contractCode}, Map.class);
            if(result!=null){
                Map constructor = (Map)result.get("constructor");
                List<Map> args = (List<Map>) constructor.get("args");
                int size = args.size();
                String[] argTypes = new String[size];
                int i = 0;
                for(Map arg : args) {
                    argTypes[i++] = arg.get("type").toString();
                }
                types=argTypes;
            }
        }catch (Throwable e){
            Log.error("call api-moudle: getContractConstructor error",e);
        }
        return types;
    }

    @Override
    public boolean validateContractCreate(int chainId, String sender, long gasLimit, long price, String contractCode, Object[] args) {
        boolean isSuccess=false;
        try{
            Map result=null;
            result= httpClient.getRpcHttpClient().invoke("validateContractCreate",new Object[]{chainId,sender,gasLimit,price,contractCode}, Map.class);
            String  successStr=(String) result.get("success");
            if("true".equals(successStr)){
                isSuccess=true;
            }
        }catch (Throwable e){
            Log.error("call api-moudle: validateContractCreate error",e);
        }
        return isSuccess;
    }

    @Override
    public long imputedContractCreateGas(int chainId, String sender, String contractCode, Object[] args) {
        long gasLimit=0;
        try{
            Map result=null;
            result= httpClient.getRpcHttpClient().invoke("imputedContractCreateGas",new Object[]{chainId,sender,contractCode,args}, Map.class);
            gasLimit=(long)result.get("gasLimit");
        }catch (Throwable e){
            Log.error("call api-moudle: imputedContractCreateGas error",e);
        }
        return gasLimit;
    }

    @Override
    public List<String> getContractMethodArgsTypes(int chainId, String contractAddress, String methodname) {
        List<String> result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("getContractMethodArgsTypes",new Object[]{chainId,contractAddress,methodname}, List.class);
        }catch (Throwable e){
            Log.error("call api-moudle: getContractMethodArgsTypes error",e);
        }
        return result;
    }

    @Override
    public Map validateContractCall(int chainId, String sender, BigInteger value, long gasLimit, long price, String contractAddress, String methodName, String methodDesc, Object[] args) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("imputedContractCreateGas",new Object[]{chainId,sender,value,gasLimit,price,contractAddress,methodName,methodDesc,args}, Map.class);
        }catch (Throwable e){
            Log.error("call api-moudle: imputedContractCreateGas error",e);
        }
        return result;
    }

    @Override
    public Map imputedContractCallGas(int chainId, String sender, BigInteger value, String contractAddress, String methodName, String methodDesc, Object[] args) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("imputedContractCallGas",new Object[]{chainId,sender,value,contractAddress,methodName,methodDesc,args}, Map.class);
        }catch (Throwable e){
            Log.error("call api-moudle: imputedContractCallGas error",e);
        }
        return result;
    }

    @Override
    public Map validateContractDelete(int chainId, String sender, String contractAddress) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("validateContractDelete",new Object[]{chainId,sender,contractAddress}, Map.class);
        }catch (Throwable e){
            Log.error("call api-moudle: validateContractDelete error",e);
        }
        return result;
    }
}
