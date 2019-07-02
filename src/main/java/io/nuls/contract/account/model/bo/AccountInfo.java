package io.nuls.contract.account.model.bo;

import java.math.BigInteger;

public class AccountInfo {
    private String address;

    private BigInteger balance;

    private BigInteger totalBalance;

    public AccountInfo() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public BigInteger getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigInteger totalBalance) {
        this.totalBalance = totalBalance;
    }

}
