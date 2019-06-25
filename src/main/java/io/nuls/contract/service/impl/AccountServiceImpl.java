package io.nuls.contract.service.impl;

import io.nuls.contract.model.BalanceInfo;
import io.nuls.contract.service.AccountService;
import io.nuls.core.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private HttpClient httpClient;

    @Override
    public BalanceInfo getAccountBalance(int chainId, int assetId, String address) {
        BalanceInfo balanceInfo=null;
        try{
            balanceInfo= httpClient.getRpcHttpClient().invoke("getAccountBalance",new Object[]{chainId,assetId,address},BalanceInfo.class);
        }catch (Throwable e){
            Log.error("call api-moudle: getAccountBalance error",e);
        }
          return balanceInfo;
    }
}
