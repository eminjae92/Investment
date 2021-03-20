package com.kakao.pay.investment.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * swagger config
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"KakaoPay Homework API",
				"API Documentation for KakaoPay Homework",
				"1.0.0",
				null, null, null, null )
				;
	}

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
				.genericModelSubstitutes(Optional.class, Flux.class, Mono.class);
    }
}