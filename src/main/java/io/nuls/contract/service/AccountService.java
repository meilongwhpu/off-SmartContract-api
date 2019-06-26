package io.nuls.contract.service;

import io.nuls.base.signture.P2PHKSignature;
import io.nuls.contract.account.model.bo.Account;
import io.nuls.contract.model.BalanceInfo;
import io.nuls.core.exception.NulsException;

public interface AccountService {

    public Account createAccount(int chainId,String password);

    public Account getAccount(int chainId, String address);

    public BalanceInfo getAccountBalance(int chainId, int assetId, String address);

    /**
     * 数据摘要签名
     * sign digest data
     *
     * @param digest   data digest.
     * @param chainId
     * @param address  address of account.
     * @param password password of account.
     * @return the signData byte[].
     * @throws NulsException nulsException
     */
    P2PHKSignature signDigest(byte[] digest, int chainId, String address, String password) throws NulsException;

    boolean validationPassword(int chainId,String address, String password);

}
