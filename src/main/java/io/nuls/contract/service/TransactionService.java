package io.nuls.contract.service;

import com.googlecode.jsonrpc4j.JsonRpcClientException;

public interface TransactionService {

    public boolean broadcastTx(int chainId, String txHex) throws JsonRpcClientException,Throwable;

}
