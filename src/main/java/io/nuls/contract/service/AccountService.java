package io.nuls.contract.service;

import io.nuls.contract.model.BalanceInfo;

public interface AccountService {

    public BalanceInfo getAccountBalance(int chainId, int assetId, String address);

}
