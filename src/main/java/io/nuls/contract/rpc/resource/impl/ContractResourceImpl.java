package io.nuls.contract.rpc.resource.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import io.nuls.contract.model.BalanceInfo;
import io.nuls.contract.rpc.resource.ContractResource;
import io.nuls.contract.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@AutoJsonRpcServiceImpl
public class ContractResourceImpl implements ContractResource {
    private final Logger logger = LoggerFactory.getLogger(ContractResourceImpl.class);

    @Autowired
    private AccountService accountService;

    @Override
    public String test(@JsonRpcParam(value = "id") long id) {
       BalanceInfo info= accountService.getAccountBalance(2,1,"tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD");
       System.out.println(info.toString());
        logger.info("input : "+id);
        return "test"+id;
    }

    @Override
    public String createContract(int chainId, int assetChainId, int assetId, String sender, String contractCode, Object[] args, long gasLimit, long price) {
        return null;
    }

    @Override
    public String callContract(int chainId, int assetChainId, int assetId, String sender, String contractCode, BigInteger value, String methodName, String methodDesc, Object[] args, long gasLimit, long price) {
        return null;
    }

    @Override
    public String deleteContract(int chainId, int assetChainId, int assetId, String sender, String contractCode) {
        return null;
    }
}
