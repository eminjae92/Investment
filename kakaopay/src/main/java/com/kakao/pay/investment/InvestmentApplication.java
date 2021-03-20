package com.kakao.pay.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class InvestmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestmentApplication.class, args);
    }

}
