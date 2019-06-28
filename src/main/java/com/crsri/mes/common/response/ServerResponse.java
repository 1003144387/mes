package com.crsri.mes.common.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 〈一句话功能简述〉<br>
 * 〈响应封装〉
 *
 * @author zcj
 * @date 2018/8/8 22:08
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("响应封装")
public class ServerResponse<T> implements Serializable {

    private static final long serialVersionUID = -7138388168274503646L;

    @ApiModelProperty("全局请求响应码 ：0 操作成功 1操作失败")
    private int code;

    @ApiModelProperty("服务端提示信息")
    private String msg;

    @ApiModelProperty("服务端响应数据")
    private T data;

    private ServerResponse(int code) {
        this.code = code;
    }

    private ServerResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createByFail(){
        return new ServerResponse<T>(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getDesc());
    }
    public static <T> ServerResponse<T> createByFail(T data){
        return new ServerResponse<T>(ResponseCode.FAIL.getCode(),data);
    }

    public static <T> ServerResponse<T> createByFailMessage(String msg){
        return new ServerResponse<T>(ResponseCode.FAIL.getCode(),msg);
    }

}
