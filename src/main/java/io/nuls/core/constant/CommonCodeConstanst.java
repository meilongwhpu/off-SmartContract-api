package io.nuls.core.constant;

/**
 * @Author: zhoulijun
 * @Time: 2019-04-18 17:48
 * @Description: 功能描述
 */
public interface CommonCodeConstanst {

    ErrorCode FAILED = ErrorCode.init("err_0001");
    ErrorCode DATA_PARSE_ERROR = ErrorCode.init("err_0003");
    ErrorCode IO_ERROR = ErrorCode.init("err_0006");
    ErrorCode PARAMETER_ERROR = ErrorCode.init("err_0012");
    ErrorCode PARSE_JSON_FAILD = ErrorCode.init("err_0017");
    ErrorCode FILE_OPERATION_FAILD = ErrorCode.init("err_0018");
    ErrorCode DESERIALIZE_ERROR = ErrorCode.init("err_0020");

    ErrorCode DB_TABLE_CREATE_ERROR = ErrorCode.init("err_2011");
    ErrorCode DB_SAVE_BATCH_ERROR = ErrorCode.init("err_2012");
    ErrorCode DB_SAVE_ERROR = ErrorCode.init("err_2013");
    ErrorCode DB_UPDATE_ERROR = ErrorCode.init("err_2014");
    ErrorCode DB_QUERY_ERROR = ErrorCode.init("err_2015");

    ErrorCode NULS_SERVICE_ERROR = ErrorCode.init("err_2016");

}
