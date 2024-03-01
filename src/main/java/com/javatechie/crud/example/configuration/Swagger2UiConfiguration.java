package com.javatechie.crud.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Optional;

@Configuration
@EnableSwagger2
public class Swagger2UiConfiguration {
	
	@Value("${server.servlet.context-path}")
    public String contectPath;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.javatechie.crud.example.controller"))
				.paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring-boot-crud")
                .description("spring-boot-crud")
                .version("1.0")
                .build();
    }
}
