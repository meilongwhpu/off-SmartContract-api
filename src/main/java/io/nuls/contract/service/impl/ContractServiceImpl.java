package io.nuls.contract.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import io.nuls.contract.model.ContractInfo;
import io.nuls.contract.model.ProgramMethodArg;
import io.nuls.contract.service.ContractService;
import io.nuls.core.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private HttpClient httpClient;

    @Override
    public String[] getContractConstructorArgsTypes(int chainId, String contractCode)  throws JsonRpcClientException,Throwable {
        String[] types=null;
        Map result= httpClient.getRpcHttpClient().invoke("getContractConstructor",new Object[]{chainId,contractCode}, Map.class);
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
        return types;
    }

    @Override
    public Map getContractConstructor(int chainId, String contractCode) throws JsonRpcClientException, Throwable {
        Map<String,Object> map=new HashMap<String,Object>();
        Map result= httpClient.getRpcHttpClient().invoke("getContractConstructor",new Object[]{chainId,contractCode}, Map.class);
        if(result!=null){
            Map constructor = (Map)result.get("constructor");
            boolean isNrc20= (boolean) result.get("isNrc20");
            List<Map> args = (List<Map>) constructor.get("args");
            int size = args.size();
            ProgramMethodArg[] argTypes = new ProgramMethodArg[size];
            int i = 0;
            for(Map arg : args) {
                ProgramMethodArg methodArg=new ProgramMethodArg();
                methodArg.setName(arg.get("name").toString());
                methodArg.setType(arg.get("type").toString());
                methodArg.setRequired((Boolean) arg.get("required"));
                argTypes[i++] = methodArg;
            }
            map.put("methodArgs",argTypes);
            map.put("isNrc20",isNrc20);
        }
        return map;
    }

    @Override
    public boolean validateContractCreate(int chainId, String sender, long gasLimit, long price, String contractCode, Object[] args)  throws JsonRpcClientException,Throwable{
        boolean isSuccess=false;
        Map result= httpClient.getRpcHttpClient().invoke("validateContractCreate",new Object[]{chainId,sender,gasLimit,price,contractCode,args}, Map.class);
        if(result!=null){
            boolean  successStr=(boolean) result.get("success");
            if(successStr){
                isSuccess=true;
            }else{
                throw  new Throwable(result.get("msg").toString());
            }
        }
        return isSuccess;
    }

    @Override
    public int imputedContractCreateGas(int chainId, String sender, String contractCode, Object[] args)  throws JsonRpcClientException,Throwable{
        int gasLimit=0;
        try{
            Map result=null;
            result= httpClient.getRpcHttpClient().invoke("imputedContractCreateGas",new Object[]{chainId,sender,contractCode,args}, Map.class);
            if(result!=null){
                gasLimit=(int)result.get("gasLimit");
            }
        }catch (Throwable e){
            Log.error("call api-moudle: imputedContractCreateGas error",e);
        }
        return gasLimit;
    }

    @Override
    public String[] getContractMethodArgsTypes(int chainId, String contractAddress, String methodname) throws JsonRpcClientException,Throwable {
        String[] argsTypes=null;
        List result= httpClient.getRpcHttpClient().invoke("getContractMethodArgsTypes",new Object[]{chainId,contractAddress,methodname}, List.class);
        if(result!=null){
            argsTypes =new String[result.size()];
            result.toArray(argsTypes);
        }
        return argsTypes;
    }

    @Override
    public boolean validateContractCall(int chainId, String sender, BigInteger value, long gasLimit, long price, String contractAddress, String methodName, String methodDesc, Object[] args)  throws JsonRpcClientException,Throwable{
        boolean isSuccess=false;
        Map result= httpClient.getRpcHttpClient().invoke("validateContractCall",new Object[]{chainId,sender,value,gasLimit,price,contractAddress,methodName,methodDesc,args}, Map.class);
        if(result!=null){
            boolean  successStr=(boolean) result.get("success");
            if(successStr){
                isSuccess=true;
            }else{
                throw  new Throwable(result.get("msg").toString());
            }
        }
        return isSuccess;
    }

    @Override
    public int imputedContractCallGas(int chainId, String sender, BigInteger value, String contractAddress, String methodName, String methodDesc, Object[] args)  throws JsonRpcClientException,Throwable{
        int gasLimit=0;
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("imputedContractCallGas",new Object[]{chainId,sender,value,contractAddress,methodName,methodDesc,args}, Map.class);
            if(result!=null){
                if(result.containsKey("gasLimit")){
                    gasLimit=(int) result.get("gasLimit");
                }else {
                    throw  new Throwable(result.get("msg").toString());
                }
            }
        }catch (Throwable e){
            Log.error("call api-moudle: imputedContractCreateGas error",e);
            throw  new Throwable(e.getMessage());
        }
        return gasLimit;
    }

    @Override
    public boolean validateContractDelete(int chainId, String sender, String contractAddress) throws JsonRpcClientException,Throwable {
        boolean isSuccess=false;
        Map result= httpClient.getRpcHttpClient().invoke("validateContractDelete",new Object[]{chainId,sender,contractAddress}, Map.class);
        if(result!=null){
            boolean  successStr=(boolean) result.get("success");
            if(successStr){
                isSuccess=true;
            }else{
                throw  new Throwable(result.get("msg").toString());
            }
        }
        return isSuccess;
    }

    @Override
    public String invokeView(int chainId, Object contractAddress, Object methodName, Object methodDesc, Object args) throws JsonRpcClientException, Throwable {
        String invokeResult=null;
        Map result = httpClient.getRpcHttpClient().invoke("invokeView",new Object[]{chainId,contractAddress,methodName,methodDesc,args},Map.class);
        if(result!=null){
            if(result.containsKey("result")){
                invokeResult=(String) result.get("result");
            }else {
                throw  new Throwable(result.get("msg").toString());
            }
        }
        return invokeResult;
    }

    @Override
    public ContractInfo getContract(int chainId, String contractAddress) throws JsonRpcClientException, Throwable {
        ContractInfo contractInfo = httpClient.getRpcHttpClient().invoke("getContract",new Object[]{chainId,contractAddress},ContractInfo.class);
        return contractInfo;
    }


}
