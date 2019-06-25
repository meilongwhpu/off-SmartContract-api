package io.nuls.contract.test;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import io.nuls.contract.model.ProgramMethod;
import io.nuls.contract.model.ProgramMethodArg;
import io.nuls.core.parse.JSONUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class RpcClientTest {
    private static JsonRpcHttpClient memberClient;
    private static final long test_memberId = 1L;

    public RpcClientTest() throws Throwable {
        memberClient = new JsonRpcHttpClient(new URL("http://127.0.0.1/contract"));
    }

    public static void main(String[] args) {
        try {
            RpcClientTest test = new RpcClientTest();
          //  test.testRPC();
            test.testRPC2();
/*
            String json="{\"name\":\"<init>\",\"desc\":\"(String tokenAddress, BigDecimal price, int awardingCycle, BigDecimal minimumDepositNULS, int minimumLocked, boolean openConsensus, String packingAddress, String rewardHalvingCycle, String maximumDepositAddressCount) return void\",\"args\":[{\"type\":\"String\",\"name\":\"tokenAddress\",\"required\":true},{\"type\":\"BigDecimal\",\"name\":\"price\",\"required\":true},{\"type\":\"int\",\"name\":\"awardingCycle\",\"required\":true},{\"type\":\"BigDecimal\",\"name\":\"minimumDepositNULS\",\"required\":true},{\"type\":\"int\",\"name\":\"minimumLocked\",\"required\":true},{\"type\":\"boolean\",\"name\":\"openConsensus\",\"required\":true},{\"type\":\"String\",\"name\":\"packingAddress\",\"required\":false},{\"type\":\"String\",\"name\":\"rewardHalvingCycle\",\"required\":false},{\"type\":\"String\",\"name\":\"maximumDepositAddressCount\",\"required\":false}],\"returnArg\":\"void\",\"view\":false,\"event\":false,\"payable\":false},\"isNrc20\":false}";
            ProgramMethod method=JSONUtils.json2pojo(json, ProgramMethod.class);
            for(ProgramMethodArg arg:method.getArgs()){
                System.out.println(arg.toString());
            }
*/

        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void testRPC()throws Throwable {
        String result = memberClient.invoke("test", new Object[] {test_memberId}, String.class);
        System.out.println(result);
    }

    public void testRPC2()throws Throwable {
        String hexEncode="";
        String sourcePath="D:\\BlockChain-nuls\\Pocm-contract-beta\\pocmContract-version-beta1\\target\\pocmContract-test2.jar";
        try {
            InputStream jarFile = new FileInputStream(sourcePath);
            byte[] contractCode= IOUtils.toByteArray(jarFile);
            hexEncode= Hex.encodeHexString(contractCode);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(hexEncode);
        String result = memberClient.invoke("createContractForTest", new Object[] {2,hexEncode}, String.class);
        System.out.println(result);
    }

}
