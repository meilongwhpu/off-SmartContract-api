package io.nuls.contract.rpc.resource;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/contract")
public interface ContractService {
    public String test(@JsonRpcParam(value = "id") long id);
}
