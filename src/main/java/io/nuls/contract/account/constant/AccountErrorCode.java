package io.nuls.contract.account.constant;

import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.constant.ErrorCode;

public interface AccountErrorCode extends CommonCodeConstanst {

    ErrorCode PASSWORD_IS_WRONG = ErrorCode.init("ac" + "_0000");
    ErrorCode ACCOUNT_NOT_EXIST = ErrorCode.init("ac" + "_0001");
    ErrorCode ACCOUNT_IS_ALREADY_ENCRYPTED = ErrorCode.init("ac" + "_0002");
    ErrorCode ACCOUNT_EXIST = ErrorCode.init("ac" + "_0003");
    ErrorCode ADDRESS_ERROR = ErrorCode.init("ac" + "_0004");
    ErrorCode ALIAS_EXIST = ErrorCode.init("ac" + "_0005");
    ErrorCode ALIAS_NOT_EXIST = ErrorCode.init("ac" + "_0006");
    ErrorCode ACCOUNT_ALREADY_SET_ALIAS = ErrorCode.init("ac" + "_0007");
    ErrorCode ACCOUNT_UNENCRYPTED = ErrorCode.init("ac" + "_0008");
    ErrorCode ALIAS_CONFLICT = ErrorCode.init("ac" + "_0009");
    ErrorCode HAVE_ENCRYPTED_ACCOUNT = ErrorCode.init("ac" + "_0010");
    ErrorCode HAVE_UNENCRYPTED_ACCOUNT = ErrorCode.init("ac" + "_0011");
    ErrorCode PRIVATE_KEY_WRONG = ErrorCode.init("ac" + "_0012");
    ErrorCode ALIAS_ROLLBACK_ERROR = ErrorCode.init("ac" + "_0013");
    ErrorCode ACCOUNTKEYSTORE_FILE_NOT_EXIST = ErrorCode.init("ac" + "_0014");
    ErrorCode ACCOUNTKEYSTORE_FILE_DAMAGED = ErrorCode.init("ac" + "_0015");
    ErrorCode ALIAS_FORMAT_WRONG = ErrorCode.init("ac" + "_0016");
    ErrorCode PASSWORD_FORMAT_WRONG = ErrorCode.init("ac" + "_0017");
    ErrorCode DECRYPT_ACCOUNT_ERROR = ErrorCode.init("ac" + "_0018");
    ErrorCode ACCOUNT_IS_ALREADY_ENCRYPTED_AND_LOCKED = ErrorCode.init("ac" + "_0019");
    ErrorCode REMARK_TOO_LONG = ErrorCode.init("ac" + "_0020");
    ErrorCode INPUT_TOO_SMALL = ErrorCode.init("ac" + "_0021");
    ErrorCode MUST_BURN_A_NULS = ErrorCode.init("ac" + "_0022");
    ErrorCode SIGN_COUNT_TOO_LARGE = ErrorCode.init("ac" + "_0023");
    ErrorCode IS_NOT_CURRENT_CHAIN_ADDRESS = ErrorCode.init("ac" + "_0024");
    ErrorCode IS_MULTI_SIGNATURE_ADDRESS = ErrorCode.init("ac" + "_0025");
    ErrorCode IS_NOT_MULTI_SIGNATURE_ADDRESS = ErrorCode.init("ac" + "_0026");
    ErrorCode ASSET_NOT_EXIST = ErrorCode.init("ac" + "_0027");
    ErrorCode INSUFFICIENT_BALANCE = ErrorCode.init("ac" + "_0028");
    ErrorCode INSUFFICIENT_FEE = ErrorCode.init("ac" + "_0029");
    ErrorCode CHAIN_NOT_EXIST = ErrorCode.init("ac" + "_0030");
    ErrorCode COINDATA_IS_INCOMPLETE = ErrorCode.init("ac" + "_0031");
    ErrorCode TX_NOT_EXIST = ErrorCode.init("ac" + "_0032");
    ErrorCode TX_COINDATA_NOT_EXIST = ErrorCode.init("ac" + "_0033");
    ErrorCode TX_DATA_VALIDATION_ERROR = ErrorCode.init("ac" + "_0034");
    ErrorCode TX_TYPE_ERROR = ErrorCode.init("ac" + "_0035");
    ErrorCode TX_NOT_EFFECTIVE = ErrorCode.init("ac" + "_0036");
    ErrorCode TX_SIZE_TOO_LARGE = ErrorCode.init("ac" + "_0037");
    ErrorCode TX_COINFROM_NOT_FOUND = ErrorCode.init("ac" + "_0038");
    ErrorCode TX_COINTO_NOT_FOUND = ErrorCode.init("ac" + "_0039");
    ErrorCode CHAINID_ERROR = ErrorCode.init("ac" + "_0040");
    ErrorCode ASSETID_ERROR = ErrorCode.init("ac" + "_0041");
    ErrorCode SIGN_ADDRESS_NOT_MATCH = ErrorCode.init("ac" + "_0042");
    ErrorCode ADDRESS_ALREADY_SIGNED = ErrorCode.init("ac" + "_0043");
    ErrorCode COINTO_DUPLICATE_COIN = ErrorCode.init("ac" + "_0044");
    ErrorCode ALIAS_SAVE_ERROR = ErrorCode.init("ac" + "_0045");
    ErrorCode AMOUNT_TOO_SMALL = ErrorCode.init("ac" + "_0046");
    ErrorCode ADDRESS_TRANSFER_BAN = ErrorCode.init("ac" + "_0047");
    ErrorCode REMOTE_RESPONSE_DATA_NOT_FOUND = ErrorCode.init("ac" + "_0048");
    ErrorCode FROM_AND_TO_INCONSISTENCY = ErrorCode.init("ac" + "_0049");
}