package com.yzd.elegantinterfacedemo.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * 作用：通过抛出业务逻辑异常直接中断当前请求
 *
 * @Author: yaozh
 * @Description: 业务逻辑异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private ResultCode status;

    private String message;

    private Object data;

    public BusinessException(ResultCode status, String message) {
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BusinessException(ResultCode status, String message, Object data) {
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }

}