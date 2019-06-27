package io.nuls.contract.account.model.bo;

import io.nuls.core.crypto.HexUtil;

import java.io.Serializable;

public class AccountInfo  implements Serializable {
    /**
     * 账户地址
     */
    private String address;

    /**
     * 公钥Hex.encode(byte[])
     */
    private String pubkeyHex;

    /**
     * 已加密私钥Hex.encode(byte[])
     */
    private String encryptedPrikeyHex;

    public AccountInfo(Account account) {
        this.address = account.getAddress().getBase58();
        this.pubkeyHex = HexUtil.encode(account.getPubKey());
        if (account.getEncryptedPriKey() != null) {
            this.encryptedPrikeyHex = HexUtil.encode(account.getEncryptedPriKey());
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPubkeyHex() {
        return pubkeyHex;
    }

    public void setPubkeyHex(String pubkeyHex) {
        this.pubkeyHex = pubkeyHex;
    }

    public String getEncryptedPrikeyHex() {
        return encryptedPrikeyHex;
    }

    public void setEncryptedPrikeyHex(String encryptedPrikeyHex) {
        this.encryptedPrikeyHex = encryptedPrikeyHex;
    }
}
