package io.nuls.contract.model;

public enum RpcErrorCode {

    // 参数不对
    PARAMS_ERROR("1000", "Parameters is wrong!"),

    // 合约未验证
    CONTRACT_NOT_VALIDATION_ERROR("100", "Contract code not certified!"),

    // 合约已验证
    CONTRACT_VALIDATION_ERROR("101", "The contract code has been certified!"),

    // 合约验证失败
    CONTRACT_VALIDATION_FAILED("102", "Contract verification failed."),

    //参数为空
    NULL_PARAMETER("103","contract parameter is null"),

    INSUFFICIENT_BALANCE("200","sender is insufficient balance"),

    CONTRACT_TX_CREATE_ERROR("202","create contract transaction error"),

    SIGNATURE_ERROR("203","transaction data signature error"),

    BROADCAST_TX_ERROR("204","broadcast transaction error"),

    VALIADE_PW_ERROR("205","validation password error"),

    VALIADE_CONTRACT_CALL_ERROR("206","validation contract call error"),

    VALIADE_CONTRACT_DELETE_ERROR("207","validation contract delete error"),

    CONTRACT_TX_CALL_ERROR("208","call contract transaction error"),

    CONTRACT_TX_DELETE_ERROR("209","delete contract transaction error"),

    //数据未找到
    DATA_NOT_EXISTS("404", "Data not found!"),

    //交易解析错误
    TX_PARSE_ERROR("999", "Transaction parse error!"),

    //脚本执行错误
    TX_SHELL_ERROR("755", "Shell execute error!"),

    //系统未知错误
    SYS_UNKNOWN_EXCEPTION("10002", "System unknown error!");

    private String code;

    private String message;

    RpcErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
