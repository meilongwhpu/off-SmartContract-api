package io.nuls.contract.test;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.net.URL;

public class RpcClientTest {
    private static JsonRpcHttpClient memberClient;
    private static final long test_memberId = 1L;

    public RpcClientTest() throws Throwable {
        memberClient = new JsonRpcHttpClient(new URL("http://127.0.0.1/contract"));
    }

    public static void main(String[] args) {
        try {
            RpcClientTest test = new RpcClientTest();
            test.testRPC();
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void testRPC()throws Throwable {
        String result = memberClient.invoke("test", new Object[] {test_memberId}, String.class);
        System.out.println(memberClient.getHeaders().get("id"));
        System.out.println(result);
    }

}
