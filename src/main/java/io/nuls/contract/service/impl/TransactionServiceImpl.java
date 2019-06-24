package io.nuls.contract.service.impl;

import io.nuls.contract.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class TransactionServiceImpl implements TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private HttpClient httpClient;

    @Override
    public Map broadcastTx(int chainId, String txHex) {
        Map result=null;
        try{
            result= httpClient.getRpcHttpClient().invoke("broadcastTx",new Object[]{chainId,txHex}, Map.class);
        }catch (Throwable e){
            logger.error("call api-moudle: broadcastTx error",e);
        }
        return result;
    }
}
