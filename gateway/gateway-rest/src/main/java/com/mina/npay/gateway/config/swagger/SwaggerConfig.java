package com.mina.npay.gateway.config.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author daimingdong
 * swagger controller
 */
@EnableSwagger2
@Configuration
@EnableWebMvc
public class SwaggerConfig {

    private Contact contact = new Contact("daimingdong","localhost:8080/swagger-ui.html", "daimingdong@paybyone.com");

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mina.npay.gateway"))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("米纳移动支付平台")
                .description("提供支付业务")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
