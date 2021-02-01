package com.yzd.elegantinterfacedemo.controller;

import lombok.Getter;

/**
 * 状态码可以参考：http/grpc状态
 * 注：目前不推荐这种1001，1002，1003，特别细分状态码
 * 建议还是只分大3大类：未知，内部，参数
 * 目前使用grpc内部状态码
 * //
 * 状态码的设计需要考虑前端的拦截器的使用
 * http code :200 500 403
 * result code:-1,3,13
 * //
 * 看看人家那后端API接口写得，那叫一个优雅
 * https://blog.csdn.net/u013256816/article/details/109127203
 *
 * @Author: yaozh
 * @Description:返回状态码
 */

public enum ResultCode {
    /*成功状态码*/
    SUCCESS(200, "成功"),
    /*未知错误*/
    UNKNOWN_ERROR(-1, "未知错误"),
    /* 参数错误：3*/
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAM_NO_COMPLETE(1004, "参数缺失"),
    /* 内部错误(业务逻辑错误)：13*/
    USER_NOT_LOGGED_IN(2001, "用户未登录，访问的路径需要验证"),
    USER_LOGGED_ERROR(2002, "用户不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003, "账号已被禁用"),
    USER_NOT_EXIST(2004, "用户不存在"),
    USER_HAS_EXISTED(2005, "用户已存在");

    @Getter
    private Integer code;
    @Getter
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
