package com.crsri.mes.common.response;

/**
 * 〈一句话功能简述〉<br>
 * 〈响应的状态码〉
 *
 * @author zcj
 * @date 2018/8/8 22:11
 * @since 1.0.0
 */
public enum ResponseCode {
    /**
     * 成功返回
     */
    SUCCESS(0, "成功"),

    /**
     * 错误返回
     */
    FAIL(1, "失败");

    private final int code;

    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}