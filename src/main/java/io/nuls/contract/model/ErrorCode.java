package io.nuls.contract.model;

import io.nuls.contract.utils.I18nUtils;

public class ErrorCode {
    /**
     * 消息内容的国际化编码
     * Internationalized encoding of message content.
     */
    private String msg;

    /**
     * 返回码，用于标记唯一的结果
     * The return code is used to mark the unique result.
     */
    private String code;

    public ErrorCode() {

    }

    protected ErrorCode(String code) {
        this.code = code;
        this.msg = code;
        if (null == code) {
            throw new RuntimeException("the errorcode code cann't be null!");
        }
    }

    /**
     * 根据系统语言设置，返回国际化编码对应的字符串
     * According to the system language Settings, return the string corresponding to the internationalization encoding.
     *
     * @return String
     */
    public String getMsg() {
        return I18nUtils.get(msg);
    }

    public String getCode() {
        return code;
    }

    public static final ErrorCode init(String code) {
        return new ErrorCode(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof ErrorCode)) {
            return false;
        }
        return code.equals(((ErrorCode) obj).getCode());
    }
}
