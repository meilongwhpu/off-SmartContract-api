package io.nuls.contract.service.impl;

import io.nuls.contract.model.BalanceInfo;
import io.nuls.contract.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private HttpClient httpClient;

    @Override
    public BalanceInfo getAccountBalance(int chainId, int assetId, String address) {
        BalanceInfo balanceInfo=null;
        try{
            balanceInfo= httpClient.getRpcHttpClient().invoke("getAccountBalance",new Object[]{chainId,assetId,address},BalanceInfo.class);
        }catch (Throwable e){
            logger.error("call api-moudle: getAccountBalance error",e);
        }
          return balanceInfo;
    }
}
