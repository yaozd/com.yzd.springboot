package com.yzd.elegantinterfacedemo.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: yaozh
 * @Description:
 */

@ApiModel("ResultObjectModel")
@Setter
@Getter
public class ResultObjectModel<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 返回是否成功
     */
    private Boolean success;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据内容
     */
    private T data;

    public ResultObjectModel() {
    }

    public ResultObjectModel(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultObjectModel success(T data) {
        return new ResultObjectModel(true, ResultCode.SUCCESS.getCode(), "请求成功", data);
    }

    public static <T> ResultObjectModel success() {
        return new ResultObjectModel(true, ResultCode.SUCCESS.getCode(), "请求成功", null);
    }

    /**
     * 返回成功
     *
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultObjectModel success(String message, T data) {
        return new ResultObjectModel(true, ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败
     *
     * @param message
     * @return
     */
    public static ResultObjectModel fail(Integer code, String message) {
        return new ResultObjectModel(false, code, message, null);
    }

    /**
     * 失败
     *
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultObjectModel fail(Integer code, String message, T data) {
        return new ResultObjectModel(false, code, message, data);
    }
}
