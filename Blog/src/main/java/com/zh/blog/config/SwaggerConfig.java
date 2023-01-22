package com.zh.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket weApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webAPI")
                .apiInfo(webApiInfo())
                .select()
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("个人博客项目API文档")
                .description("本文档描述博客项目接口")
                .version("1.0")
                .contact(new Contact("nichijoux", "https://github.com/nichijoux", "1647365387@qq.com"))
                .build();
    }
}
