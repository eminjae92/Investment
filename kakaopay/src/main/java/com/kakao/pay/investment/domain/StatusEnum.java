package com.kakao.pay.investment.domain;

public enum StatusEnum {

    ING("모집중"),
    END("모집완료");

    private String name;

    public String getName() {
        return name;
    }

    StatusEnum(String name){
        this.name = name;
    }



}
