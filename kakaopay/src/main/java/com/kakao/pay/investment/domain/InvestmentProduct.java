package com.kakao.pay.investment.domain;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentProduct {

    private int productId; //상품ID

    private String title; //상품명

    private int totalInvestingAmount; //총 투자 모집금액

    private int currentInvestingAmount; //누적 투자 모집금액

    private int investerCnt; //투자자 수

    private Timestamp startedAt; //투자시작일시

    private Timestamp finishedAt; //투자종료일시

    private String investmentStatus; //투자모집상태

}
