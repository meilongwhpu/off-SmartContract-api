package io.nuls.contract.helper;

import io.nuls.base.RPCUtil;
import io.nuls.base.data.Transaction;
import io.nuls.contract.model.txdata.CreateContractData;
import io.nuls.core.basic.NulsData;
import io.nuls.core.basic.Result;
import io.nuls.core.basic.VarInt;
import io.nuls.core.exception.NulsException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;

import static io.nuls.core.constant.CommonCodeConstanst.FAILED;

@Component
public class ContractTxHelper {

    public CreateContractData getCreateContractData(byte[] senderBytes, byte[] contractAddressBytes, BigInteger value, long gasLimit, long price, byte[] contractCode, String[][] args) {
        CreateContractData createContractData = new CreateContractData();
        createContractData.setSender(senderBytes);
        createContractData.setContractAddress(contractAddressBytes);
        createContractData.setGasLimit(gasLimit);
        createContractData.setPrice(price);
        createContractData.setCode(contractCode);
        if (args != null) {
            createContractData.setArgsCount((byte) args.length);
            createContractData.setArgs(args);
        }
        return createContractData;
    }

    public int calcSize(NulsData nulsData) {
        if (nulsData == null) {
            return 0;
        }
        int size = nulsData.size();
        // 计算tx.size()时，当coinData和txData为空时，计算了1个长度，若此时nulsData不为空，则要扣减这1个长度
        return VarInt.sizeOf(size) + size - 1;
    }

}
