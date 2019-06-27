package io.nuls.contract.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import io.nuls.contract.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private HttpClient httpClient;

    @Override
    public boolean broadcastTx(int chainId, String txHex)  throws JsonRpcClientException,Throwable {
        boolean isSuccess=false;
        Map result=null;
        result= httpClient.getRpcHttpClient().invoke("broadcastTx",new Object[]{chainId,txHex}, Map.class);
        String  successStr=(String) result.get("success");
        if("true".equals(successStr)){
            isSuccess=true;
        }
        return isSuccess;
    }


}
