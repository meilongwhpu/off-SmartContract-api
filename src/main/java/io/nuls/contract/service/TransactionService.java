package io.nuls.contract.service;

import java.util.Map;

public interface TransactionService {

    public Map broadcastTx(int chainId, String txHex);

}
