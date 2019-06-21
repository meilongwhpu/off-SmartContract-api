package io.nuls.contract.test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nuls.contract.model.RpcResult;

import java.lang.reflect.Type;

public class Test {

    public static void main(String[] args) {
       try{
           ObjectMapper mapper=new ObjectMapper();
           String json="{\"jsonrpc\":\"2.0\",\"id\":2103470749,\"result\":{\"address\":\"tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD\",\"alias\":null,\"type\":1,\"txCount\":27,\"totalOut\":70800000,\"totalIn\":1000000060219650,\"consensusLock\":0,\"timeLock\":0,\"balance\":999999989419650,\"totalBalance\":999999989419650,\"totalReward\":1000000060219650,\"tokens\":[\"tNULSeBaNCHAhqG84z2kdeHx6AuFH6Zk6TmDDG,POCMTEST\"]}}\n";
           JsonNode node= mapper.readTree(json);
           JsonParser parser =mapper.treeAsTokens(node);
            RpcResult result =mapper.treeToValue(node, RpcResult.class);
            Type type=Type.class.cast(RpcResult.class);
            JavaType javaType=mapper.getTypeFactory().constructType(type);
           RpcResult result2=mapper.readValue(parser, javaType);
           System.out.println(result2.toString());
       }catch (Exception e){

       }

    }
}
