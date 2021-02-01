package com.yzd.elegantinterfacedemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Description : rest full 全局统一返回封装
 *
 * @author yaozh
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 需要忽略的地址
     */
    private static String[] ignores = new String[]{
            //过滤swagger相关的请求的接口，不然swagger会提示base-url被拦截
            "/swagger-resources",
            "/v2/api-docs"
    };

    /**
     * 判断哪些需要拦截
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 判断url是否需要拦截
     *
     * @param uri
     * @return
     */
    private boolean ignoring(String uri) {
        log.info(uri);
        for (String string : ignores) {
            if (uri.startsWith(string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        //判断url是否需要拦截
        if (SwaggerConfigurer.enable && this.ignoring(request.getURI().getRawPath())) {
            return body;
        }
        //如果返回的数据是ResultObjectModel、Byte类型则不进行封装
        /**
         if (body instanceof ResultObjectModel || body instanceof Byte || body instanceof String) {
         return body;
         }*/
        //版本2：解决org.springframework.http.converter.StringHttpMessageConverter.addDefaultHeaders
        if (body instanceof Byte || body instanceof String) {
            return JsonUtil.toJSONString(this.getWrapperResponse(body));
        }
        if (body instanceof ResultObjectModel) {
            return body;
        }
        return this.getWrapperResponse(body);
    }

    /**
     * 返回正常的信息
     *
     * @param data
     * @return
     */
    private ResultObjectModel<Object> getWrapperResponse(Object data) {
        return ResultObjectModel.success(data);
    }

    /**
     * 默认异常处理，返回500错误
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultObjectModel<String> defaultExceptionHandler(HttpServletRequest req, Exception e) {
        return getUnknownExceptionResponse(req, e);
    }

    /**
     * 无法找到映射handler的异常处理，返回404错误
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultObjectModel<String> noHandlerFoundExceptionHandler(HttpServletRequest req, Exception e) {
        return getUnknownExceptionResponse(req, e);
    }


    /**
     * 异常信息返回
     *
     * @param request
     * @param ex
     * @return
     */
    private ResultObjectModel<String> getUnknownExceptionResponse(HttpServletRequest request, Exception ex) {
        // 记录错误信息
        log.error("CODE:[{}],uri:[{}],detail exception:", ResultCode.UNKNOWN_ERROR.name(), request.getRequestURI(), ex);
        return ResultObjectModel.fail(ResultCode.UNKNOWN_ERROR.getCode(), ex.getMessage());
    }

    /**
     * 业务层异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultObjectModel<Object> businessExceptionHandler(HttpServletRequest request, Exception e) {
        BusinessException ex = (BusinessException) e;
        // 记录错误信息
        log.error("CODE:[{}],uri:[{}],detail exception:", ex.getStatus().name(), request.getRequestURI(), ex);
        return ResultObjectModel.fail(ex.getStatus().getCode(), ex.getMessage(), ex.getData());
    }
}