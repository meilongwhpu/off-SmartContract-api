package io.nuls.contract.test;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import io.nuls.contract.model.RpcResult;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;

public class RpcContractClientTest {
    private static JsonRpcHttpClient memberClient;
    private int chainId=2;
    private  int assetId=1;

    private String sender="tNULSeBaMnYpvcJj5grAfxyi4pfKcwSreGotak";

    private String contractAddress_nrc = "tNULSeBaNCHAhqG84z2kdeHx6AuFH6Zk6TmDDG";

    private String contractAddress="";
    private String password="NULS123456";

    private long gasLimit=100000;

    private long price=3;
    private  String remark="test";
    @Before
    public void setUp() throws Throwable {
        memberClient = new JsonRpcHttpClient(new URL("http://127.0.0.1/contract"));
    }

    @Test
    public void createContract()throws Throwable {
        //int chainId,int assetId,String sender, String password,
        // String contractCode,Object[] args,long gasLimit, long price, String remark
        String contractCode=getContractCode("D:\\BlockChain-nuls\\Pocm-contract-beta\\pocmContract-version-beta1\\target\\pocmContract-test2.jar");
        Object[] args = new Object[]{contractAddress_nrc,12000, 2, 0.5, 2, false, "tNULSeBaMtEPLXxUgyfnBt9bpb5Xv84dyJV98p", null, null};
        RpcResult result=memberClient.invoke("createContract",new Object[]{chainId,assetId,sender,password,contractCode,args,gasLimit,price,remark},RpcResult.class);
        System.out.println(result);
    }

    @Test
    public void callContract()throws Throwable {
    //int chainId, int assetId, String sender,String password,String contractAddress,Object[] args,
        // long gasLimit, long price, String remark
        Object[] args=new Object[]{};
        RpcResult result=memberClient.invoke("callContract",new Object[]{chainId,assetId,sender,password,contractAddress,args,gasLimit,price,remark},RpcResult.class);
        System.out.println(result);
    }

    @Test
    public void deleteContract()throws Throwable {
        //int chainId,int assetId,String sender,String password, String contractAddress,String remark
        RpcResult result=memberClient.invoke("deleteContract",new Object[]{chainId,assetId,sender,password,contractAddress,remark},RpcResult.class);
        System.out.println(result);
    }

    private String getContractCode(String sourcePath){
        String hexEncode="";
        try {
            InputStream jarFile = new FileInputStream(sourcePath);
            byte[] contractCode= IOUtils.toByteArray(jarFile);
            hexEncode= Hex.encodeHexString(contractCode);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return hexEncode;
    }

}
