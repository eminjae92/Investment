package com.kakao.pay.investment.config.exception;

import lombok.Getter;

@Getter
public class SoldOutException extends RuntimeException{

    private String errorMessage;

    public SoldOutException(){
        this.errorMessage = "Sold-Out";
    }
}
