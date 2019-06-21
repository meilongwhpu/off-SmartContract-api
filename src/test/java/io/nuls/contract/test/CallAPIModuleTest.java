package io.nuls.contract.test;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import io.nuls.contract.model.RpcResult;
import io.nuls.contract.utils.JSONUtils;

import java.net.URL;

public class CallAPIModuleTest {
    private static JsonRpcHttpClient memberClient;
    private static final long test_memberId = 1L;

    public CallAPIModuleTest() throws Throwable {
        memberClient = new JsonRpcHttpClient(new URL("http://127.0.0.1:18003"));
    }

    public static void main(String[] args) {
        try {
            CallAPIModuleTest test = new CallAPIModuleTest();
            test.testRPC();
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void testRPC()throws Throwable {
        RpcResult result = memberClient.invoke("getAccount", new Object[] {2,"tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD"}, RpcResult.class);
        System.out.println(result.toString());
    }
}
