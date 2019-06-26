package io.nuls.contract.service;

import java.util.Map;

public interface TransactionService {

    public boolean broadcastTx(int chainId, String txHex);

}
