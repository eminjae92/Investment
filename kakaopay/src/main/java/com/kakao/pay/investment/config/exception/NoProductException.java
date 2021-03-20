package com.kakao.pay.investment.config.exception;

import lombok.Getter;

@Getter
public class NoProductException extends RuntimeException {

    private String errorMessage;

    public NoProductException(){
        this.errorMessage = "존재하지 않는 투자상품입니다.";
    }

}
