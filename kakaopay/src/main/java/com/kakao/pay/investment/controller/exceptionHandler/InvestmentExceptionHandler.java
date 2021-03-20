package com.kakao.pay.investment.controller.exceptionHandler;

import com.kakao.pay.investment.config.exception.NoProductException;
import com.kakao.pay.investment.config.exception.SoldOutException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvestmentExceptionHandler {

    @ExceptionHandler(SoldOutException.class)
    public ResponseEntity<String> soldOutException(SoldOutException e) {
        ResponseEntity responseEntity = ResponseEntity.status(401).body(e.getErrorMessage());

        return responseEntity;
    }


    @ExceptionHandler(NoProductException.class)
    public ResponseEntity<String> noProductException(NoProductException e) {
        ResponseEntity responseEntity = ResponseEntity.status(401).body(e.getErrorMessage());

        return responseEntity;
    }
}
