package io.nuls.contract.rpc.resource.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import io.nuls.contract.rpc.resource.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class ContractServiceImpl  implements ContractService {
    private final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Override
    public String test(@JsonRpcParam(value = "id") long id){
        logger.info("input : "+id);
        return "test"+id;
    }
}
