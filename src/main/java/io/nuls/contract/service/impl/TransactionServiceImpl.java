package io.nuls.contract.service.impl;

import io.nuls.contract.service.TransactionService;
import io.nuls.core.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private HttpClient httpClient;

    @Override
    public boolean broadcastTx(int chainId, String txHex) {
        boolean isSuccess=false;
        try{
            Map result=null;
            result= httpClient.getRpcHttpClient().invoke("broadcastTx",new Object[]{chainId,txHex}, Map.class);
            String  successStr=(String) result.get("success");
            if("true".equals(successStr)){
                isSuccess=true;
            }
        }catch (Throwable e){
            Log.error("call api-moudle: broadcastTx error",e);
        }
        return isSuccess;
    }


}
