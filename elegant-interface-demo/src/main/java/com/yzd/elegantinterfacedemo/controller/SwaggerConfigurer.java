package com.yzd.elegantinterfacedemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description : 创建Swagger配置类
 *
 * @author yaozh
 */
@EnableSwagger2
@Configuration
@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
public class SwaggerConfigurer {

    public static boolean enable;

    @Value("${swagger.enable}")
    public void setEnable(Boolean enable) {
        SwaggerConfigurer.enable = enable;
    }

    @Bean
    public Docket createRestApi() {
        //设置全局响应状态码
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.yzd.elegantinterfacedemo"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        String description = "{\n" +
                "  \"code\": 200,\n" +
                "  \"success\": true,\n" +
                "  \"message\": \"请求成功\",\n" +
                "  \"data\": 接口数据\n" +
                "}";
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("spring-boot-demo");
        builder.description("spring-boot-demo rest full api doc\r\n全局统一返回响应的数据结构：\r\n" + description);
        builder.version("1.0");
        return builder.build();
    }
}