package io.nuls.offlinecontract.test;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import io.nuls.contract.account.model.po.AccountKeyStoreDto;
import io.nuls.contract.model.RpcResult;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.parse.JSONUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class RpcAccountClientTest {
    private static JsonRpcHttpClient memberClient;
    private static final long test_memberId = 1L;

    private int chainId=2;

    private String password="nuls123456";

    @Before
    public void setUp() throws Throwable {
        memberClient = new JsonRpcHttpClient(new URL("http://127.0.0.1/account"));
    }

    @Test
    public void testRPC()throws Throwable {
        String result = memberClient.invoke("test", new Object[] {test_memberId}, String.class);
        System.out.println(result);
    }

    @Test
    public void createContractForTest()throws Throwable {
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

    @Test
    public  void createAccount() throws Throwable{
        RpcResult result = memberClient.invoke("createAccount", new Object[] {chainId,password}, RpcResult.class);
        System.out.println(result);
    }

    @Test
    public  void importAccountByKeystore() throws Throwable{
        boolean isCreate=false;
        AccountKeyStoreDto keyStoreDto = new AccountKeyStoreDto();
        if(isCreate){
            RpcResult address = memberClient.invoke("createAccount", new Object[] {chainId,password}, RpcResult.class);

            RpcResult accountR = memberClient.invoke("getAccount", new Object[] {chainId,address.getResult()}, RpcResult.class);
            System.out.println(accountR.getResult());
            Map map=(Map) accountR.getResult();
            // Account account= (Account) accountR.getResult();

            keyStoreDto.setAddress(map.get("address").toString());
            keyStoreDto.setPubKey(map.get("pubkeyHex").toString());
            keyStoreDto.setEncryptedPrivateKey(map.get("encryptedPrikeyHex").toString());
        }else {
            keyStoreDto.setAddress("tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG");
            keyStoreDto.setPubKey("03958b790c331954ed367d37bac901de5c2f06ac8368b37d7bd6cd5ae143c1d7e3");
            keyStoreDto.setEncryptedPrivateKey("709c90bb90d2aea2fbfb16361630ecea8dfb5619151bcf11d04b085e92f50aa78063f3d6b4331e44c71b8255922b9047");
        }

        //生成keystore HEX编码
        String keyStoreHex = HexUtil.encode(JSONUtils.obj2json(keyStoreDto).getBytes());
        RpcResult result = memberClient.invoke("importAccountByKeystore", new Object[] {chainId,password,keyStoreHex,true}, RpcResult.class);
        System.out.println(result);
    }
}
