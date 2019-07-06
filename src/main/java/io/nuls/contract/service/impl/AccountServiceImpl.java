package io.nuls.contract.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import io.nuls.base.basic.AddressTool;
import io.nuls.base.data.Address;
import io.nuls.base.signture.P2PHKSignature;
import io.nuls.base.signture.SignatureUtil;
import io.nuls.contract.constant.AccountErrorCode;
import io.nuls.contract.account.model.bo.Account;
import io.nuls.contract.account.model.bo.AccountKeyStore;
import io.nuls.contract.account.model.po.AccountPo;
import io.nuls.contract.account.storage.AccountStorageService;
import io.nuls.contract.account.utils.AccountTool;
import io.nuls.contract.constant.ContractErrorCode;
import io.nuls.contract.model.BalanceInfo;
import io.nuls.contract.service.AccountKeyStoreService;
import io.nuls.contract.service.AccountService;
import io.nuls.core.crypto.AESEncrypt;
import io.nuls.core.crypto.ECKey;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.exception.CryptoException;
import io.nuls.core.exception.NulsException;
import io.nuls.core.exception.NulsRuntimeException;
import io.nuls.core.log.Log;
import io.nuls.core.model.FormatValidUtils;
import io.nuls.core.model.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AccountServiceImpl implements AccountService {

    private Lock locker = new ReentrantLock();

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private AccountStorageService accountStorageService;

    @Autowired
    private AccountKeyStoreService accountKeyStoreService;

    @Override
    public Account createAccount(int chainId, String password) {
        locker.lock();
        try {
            Account account = AccountTool.createAccount(chainId);
            if (StringUtils.isNotBlank(password)) {
                account.encrypt(password);
            }
            AccountPo po = new AccountPo(account);
            boolean result = accountStorageService.saveAccount(po);
            if(result){
                accountKeyStoreService.backupAccountToKeyStore(null, chainId, account.getAddress().getBase58(), password);
            }
            return account;
        } catch (NulsException e) {
            Log.error(e);
            throw new NulsRuntimeException(e.getErrorCode());
        }finally {
            locker.unlock();
        }
    }

    @Override
    public Account getAccount(int chainId, String address) {
        if (!AddressTool.validAddress(chainId, address)) {
            throw new NulsRuntimeException(AccountErrorCode.ADDRESS_ERROR);
        }
        byte[] addressBytes = AddressTool.getAddress(address);
        AccountPo accountPo=accountStorageService.getAccount(addressBytes);
        Account account=null;
        if(accountPo!=null){
            account=accountPo.toAccount();
        }
        return account;
    }

    @Override
    public List<Account> getAccountList(int chainId) {
        List<Account> accountList = new ArrayList<>();
        List<AccountPo> accountPoList=accountStorageService.getAccountList();
        if (null == accountPoList || accountPoList.isEmpty()) {
            return accountList;
        }
        for (AccountPo po : accountPoList) {
            if(chainId==po.getChainId()){
                Account account = po.toAccount();
                accountList.add(account);
            }
        }
        Collections.sort(accountList, (Account o1, Account o2) -> (o2.getCreateTime().compareTo(o1.getCreateTime())));
        return accountList;
    }


    @Override
    public BalanceInfo getAccountBalance(int chainId, int assetId, String address){
        BalanceInfo balanceInfo=null;
        try {
            balanceInfo= httpClient.getRpcHttpClient().invoke("getAccountBalance",new Object[]{chainId,assetId,address},BalanceInfo.class);
        } catch (Throwable e) {
            throw new NulsRuntimeException(AccountErrorCode.NULS_SERVICE_ERROR,e);
        }
        return balanceInfo;
    }

    @Override
    public P2PHKSignature signDigest(byte[] digest, int chainId, String address, String password) {
        if (null == digest || digest.length == 0) {
            throw new NulsRuntimeException(AccountErrorCode.PARAMETER_ERROR);
        }
        byte[] addressBytes = AddressTool.getAddress(address);
        AccountPo accountPo=accountStorageService.getAccount(addressBytes);
        try {
            //根据密码获得ECKey get ECKey from Password
            ECKey  ecKey = accountPo.getEcKey(password);
            byte[] signBytes = SignatureUtil.signDigest(digest, ecKey).serialize();
            return new P2PHKSignature(signBytes, ecKey.getPubKey());
        }  catch (NulsException e) {
            throw new NulsRuntimeException(e);
        }catch (IOException e) {
            Log.error(e.getMessage());
            throw new NulsRuntimeException(AccountErrorCode.IO_ERROR,e);
        }
    }

    @Override
    public boolean validationPassword(int chainId, String address, String password) {
        byte[] addressBytes = AddressTool.getAddress(address);
        AccountPo accountPo=accountStorageService.getAccount(addressBytes);
        boolean result=false;
        if(accountPo!=null){
            result =accountPo.validatePassword(password);
        }
        return result;
    }

    @Override
    public Account importAccountByKeyStore(AccountKeyStore keyStore, int chainId, String password, boolean overwrite){
        if (null == keyStore || StringUtils.isBlank(keyStore.getAddress())) {
            throw new NulsRuntimeException(AccountErrorCode.PARAMETER_ERROR);
        }
        if (!AddressTool.validAddress(chainId, keyStore.getAddress())) {
            throw new NulsRuntimeException(AccountErrorCode.ADDRESS_ERROR);
        }
        if (!FormatValidUtils.validPassword(password)) {
            throw new NulsRuntimeException(AccountErrorCode.PASSWORD_IS_WRONG);
        }
        if (!overwrite) {
            byte[] addressBytes = AddressTool.getAddress(keyStore.getAddress());
            //Query account already exists
            AccountPo accountPo = accountStorageService.getAccount(addressBytes);
            if (null != accountPo) {
                throw new NulsRuntimeException(AccountErrorCode.ACCOUNT_EXIST);
            }
        }

        Account account;
        byte[] priKey;
        //if the private key is not encrypted, it is not empty
        if (null != keyStore.getPrikey() && keyStore.getPrikey().length > 0) {
            if (!ECKey.isValidPrivteHex(HexUtil.encode(keyStore.getPrikey()))) {
                throw new NulsRuntimeException(AccountErrorCode.PARAMETER_ERROR);
            }
            //create account by private key
            priKey = keyStore.getPrikey();
            try {
                account = AccountTool.createAccount(chainId, HexUtil.encode(priKey));
                //如果私钥生成的地址和keystore的地址不相符，说明私钥错误
                //if the address generated by the private key does not match the address of the keystore, the private key error
                if (!account.getAddress().getBase58().equals(keyStore.getAddress())) {
                    throw new NulsRuntimeException(AccountErrorCode.PRIVATE_KEY_WRONG);
                }
            } catch (NulsException e) {
                throw new NulsRuntimeException(e);
            }
        } else if (null == keyStore.getPrikey() && null != keyStore.getEncryptedPrivateKey()) {
            try {
                //create account by private key
                priKey = AESEncrypt.decrypt(HexUtil.decode(keyStore.getEncryptedPrivateKey()), password);
                account = AccountTool.createAccount(chainId, HexUtil.encode(priKey));
            } catch (NulsException e) {
                throw new NulsRuntimeException(e);
            }catch (CryptoException e) {
                throw new NulsRuntimeException(AccountErrorCode.PASSWORD_IS_WRONG);
            }
            //如果私钥生成的地址和keystore的地址不相符，说明私钥错误
            //if the address generated by the private key does not match the address of the keystore, the private key error
            if (!account.getAddress().getBase58().equals(keyStore.getAddress())) {
                throw new NulsRuntimeException(AccountErrorCode.PRIVATE_KEY_WRONG);
            }
        } else {
            throw new NulsRuntimeException(AccountErrorCode.PARAMETER_ERROR);
        }
        try {
            account.encrypt(password);
        } catch (NulsException e) {
            throw new NulsRuntimeException(e);
        }
        accountStorageService.saveAccount(new AccountPo(account));

        //backup account to keystore
        accountKeyStoreService.backupAccountToKeyStore(null, chainId, account.getAddress().getBase58(), password);

        return account;
    }

    @Override
    public Account importAccountByPrikey(int chainId, String prikey, String password, boolean overwrite) {
        //check params
        if (!ECKey.isValidPrivteHex(prikey)) {
            throw new NulsRuntimeException(AccountErrorCode.PRIVATE_KEY_WRONG);
        }
        if (!FormatValidUtils.validPassword(password)) {
            throw new NulsRuntimeException(AccountErrorCode.PASSWORD_IS_WRONG);
        }
        //not allowed to cover
        if (!overwrite) {
            Address address = AccountTool.newAddress(chainId, prikey);
            //Query account already exists
            Account account = this.getAccount(chainId, address.getBase58());
            if (null != account) {
                throw new NulsRuntimeException(AccountErrorCode.ACCOUNT_EXIST);
            }
        }
        //create account by private key
        Account account;
        try {
            account = AccountTool.createAccount(chainId, prikey);
        } catch (NulsException e) {
            throw new NulsRuntimeException(AccountErrorCode.PRIVATE_KEY_WRONG);
        }
        //encrypting account private key
        try {
            account.encrypt(password);
        } catch (NulsException e) {
            throw new NulsRuntimeException(e);
        }

        //save account to storage
        accountStorageService.saveAccount(new AccountPo(account));

        //backup account to keystore
        accountKeyStoreService.backupAccountToKeyStore(null, chainId, account.getAddress().getBase58(), password);

        return account;
    }

    @Override
    public String getPrivateKey(int chainId, String address, String password)  throws JsonRpcClientException{
        //check whether the account exists
        Account account = this.getAccount(chainId, address);
        if (null == account) {
            throw new NulsRuntimeException(AccountErrorCode.ACCOUNT_NOT_EXIST);
        }
        //加过密(有密码) 就验证密码 Already encrypted(Added password), verify password
        if (account.isEncrypted()) {
            try {
                byte[] priKeyBytes = account.getPriKey(password);
                return HexUtil.encode(priKeyBytes);
            } catch (NulsException e) {
                throw new NulsRuntimeException(AccountErrorCode.PASSWORD_IS_WRONG);
            }
        } else {
            return null;
        }
    }

}
