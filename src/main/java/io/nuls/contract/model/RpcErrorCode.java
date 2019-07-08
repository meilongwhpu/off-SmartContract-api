package io.nuls.contract.model;

import io.nuls.core.constant.ErrorCode;

public interface RpcErrorCode {


    ErrorCode  DB_TABLE_CREATE_ERROR= ErrorCode.init("db_0001");
    ErrorCode  DB_SAVE_BATCH_ERROR= ErrorCode.init("db_0002");
    ErrorCode  DB_UPDATE_ERROR= ErrorCode.init("db_0003");
    ErrorCode  DB_QUERY_ERROR= ErrorCode.init("db_0004");
    ErrorCode DB_DELETE_ERROR = ErrorCode.init("db_0005");

    ErrorCode ADDRESS_ERROR= ErrorCode.init("ac_0001");
    ErrorCode PASSWORD_IS_WRONG= ErrorCode.init("ac_0002");
    ErrorCode PRIVATE_KEY_WRONG= ErrorCode.init("ac_0003");
    ErrorCode VALIADE_PW_ERROR= ErrorCode.init("ac_0004");
    ErrorCode ACCOUNT_EXIST= ErrorCode.init("ac_0005");
    ErrorCode ACCOUNT_NOT_EXIST= ErrorCode.init("ac_0006");
    ErrorCode ACCOUNT_IS_ALREADY_ENCRYPTED_AND_LOCKED= ErrorCode.init("ac_0007");
    ErrorCode ACCOUNT_IS_ALREADY_ENCRYPTED= ErrorCode.init("ac_0008");
    ErrorCode INSUFFICIENT_BALANCE= ErrorCode.init("ac_0009");


    ErrorCode GET_CONSTRUSTOR_PARAMETER = ErrorCode.init("fn_0001");
    ErrorCode BROADCAST_TX_ERROR= ErrorCode.init("fn_0002");
    ErrorCode CONTRACT_TX_CREATE_ERROR= ErrorCode.init("fn_0003");
    ErrorCode CONTRACT_VALIDATION_FAILED= ErrorCode.init("fn_0004");
    ErrorCode GET_CONTRACT_INFO_FAILED= ErrorCode.init("fn_0005");
    ErrorCode VALIADE_CONTRACT_CALL_ERROR= ErrorCode.init("fn_0006");
    ErrorCode VALIADE_CONTRACT_DELETE_ERROR= ErrorCode.init("fn_0007");

    ErrorCode PARAMETER_ERROR= ErrorCode.init("sys_0001");
    ErrorCode NULL_PARAMETER = ErrorCode.init("sys_0002");
    ErrorCode DESERIALIZE_ERROR= ErrorCode.init("sys_0003");
    ErrorCode SIGNATURE_ERROR= ErrorCode.init("sys_0004");
    ErrorCode IO_ERROR= ErrorCode.init("sys_0005");
    ErrorCode PARSE_JSON_FAILD= ErrorCode.init("err_0006");
    ErrorCode FILE_OPERATION_FAILD= ErrorCode.init("sys_0007");
    ErrorCode DATA_PARSE_ERROR = ErrorCode.init("sys_0008");

    //api module exception
    ErrorCode NULS_SERVICE_ERROR = ErrorCode.init("srv_0001");

}
