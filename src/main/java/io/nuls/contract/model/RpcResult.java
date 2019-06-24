package io.nuls.contract.model;

import java.util.Map;

public class RpcResult{
    private String jsonrpc = "2.0";

    private long id;

    private Map result;
    private RpcResultError error;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map getResult() {
        return result;
    }

    public RpcResult setResult(Map result) {
        this.result = result;
        return this;
    }

    public RpcResultError getError() {
        return error;
    }

    public RpcResult setError(RpcResultError error) {
        this.error = error;
        return this;
    }

    public static  RpcResult success(Map t) {
        RpcResult rpcResult = new RpcResult();
        rpcResult.setResult(t);
        return rpcResult;
    }

    public static RpcResult failed(RpcErrorCode errorCode) {
        RpcResult rpcResult = new RpcResult();
        RpcResultError error = new RpcResultError(errorCode);
        rpcResult.setError(error);
        return rpcResult;
    }

    public static RpcResult failed(Result result) {
        RpcResult rpcResult = new RpcResult();
        RpcResultError error = new RpcResultError(result.getErrorCode().getCode(), result.getMsg(), null);
        rpcResult.setError(error);
        return rpcResult;
    }

    public static RpcResult dataNotFound() {
        RpcResult rpcResult = new RpcResult();
        RpcResultError error = new RpcResultError(RpcErrorCode.DATA_NOT_EXISTS.getCode(), RpcErrorCode.DATA_NOT_EXISTS.getMessage(), null);
        rpcResult.setError(error);
        return rpcResult;
    }

    public static RpcResult paramError() {
        RpcResult rpcResult = new RpcResult();
        RpcResultError error = new RpcResultError(RpcErrorCode.PARAMS_ERROR.getCode(), RpcErrorCode.PARAMS_ERROR.getMessage(), null);
        rpcResult.setError(error);
        return rpcResult;
    }

    public static RpcResult paramError(String data) {
        RpcResult rpcResult = new RpcResult();
        RpcResultError error = new RpcResultError(RpcErrorCode.PARAMS_ERROR.getCode(), RpcErrorCode.PARAMS_ERROR.getMessage(), data);
        rpcResult.setError(error);
        return rpcResult;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"jsonrpc\":")
                .append('\"').append(jsonrpc).append('\"');
        sb.append(",\"id\":")
                .append(id);
        sb.append(",\"result\":")
                .append('\"').append(result.toString()).append('\"');
        sb.append(",\"error\":")
                .append(error);
        sb.append('}');
        return sb.toString();
    }
}
