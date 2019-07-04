package io.nuls.contract.constant;

import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.constant.ErrorCode;

public interface AccountErrorCode extends CommonCodeConstanst {

    ErrorCode PASSWORD_IS_WRONG = ErrorCode.init("ac_0000");
    ErrorCode ACCOUNT_NOT_EXIST = ErrorCode.init("ac_0001");
    ErrorCode ACCOUNT_IS_ALREADY_ENCRYPTED = ErrorCode.init("ac_0002");
    ErrorCode ACCOUNT_EXIST = ErrorCode.init("ac_0003");
    ErrorCode ADDRESS_ERROR = ErrorCode.init("ac_0004");
    ErrorCode PRIVATE_KEY_WRONG = ErrorCode.init("ac_0005");
    ErrorCode ACCOUNT_IS_ALREADY_ENCRYPTED_AND_LOCKED = ErrorCode.init("ac_0006");



}