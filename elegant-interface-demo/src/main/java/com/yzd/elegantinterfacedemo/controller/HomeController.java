package com.yzd.elegantinterfacedemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yaozh
 * @Description:
 */
@Api(tags = {"01-演示相关接口"})
@RestController
@RequestMapping("/home")
public class HomeController {
    @ApiOperation(value = "接口说明：getTime")
    @GetMapping("/getTime")
    public String getTime() {
        return "Get time:" + System.currentTimeMillis();
    }

    @GetMapping("/getBoolean")
    public Boolean getBoolean() {
        return true;
    }

    @GetMapping("/getList")
    public List<String> getList() {
        List<String> objectList = new ArrayList<>();
        objectList.add("1");
        objectList.add("2");
        objectList.add("3");
        return objectList;
    }

    @GetMapping("/noResult")
    public void noResult() {
        return;
    }

    @GetMapping("/throwError")
    public void throwError() {
        throw new RuntimeException("Throw error");
    }

    @GetMapping("/throwBusinessException")
    public void throwBusinessException() {
        throw new BusinessException(ResultCode.PARAM_IS_INVALID, "Throw business exception");
    }

//    @GetMapping("/getSuccess")
//    public ResultObjectModel getSuccess() {
//        return ResultObjectModel.success("无法直接返回字符串，只能这样间接性返回！");
//    }
}
