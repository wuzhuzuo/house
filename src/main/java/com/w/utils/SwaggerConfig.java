package com.w.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.w.contorller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("经纪人项目 RESULTFUL APIs")
                .description("house项目后台接口")
                .version("1.0")
                .termsOfServiceUrl("http://47.103.197.178:8080")
             .license("Apache 2.0")
               /* .licenseUrl("http://www.apache.org/license/LICENSE-2.0")*/
                .licenseUrl("https://mszfzjd.cn/house")
                .build();
    }
}
