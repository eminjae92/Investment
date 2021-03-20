package com.kakao.pay.investment.domain;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentDetails {

    private int sequenceNumber; //투자내역ID

    private String userId; //사용자ID

    private int productId; //상품ID

    private int investmentAmount; //투자금액

    private Timestamp investmentDate; //투자일시


    @Builder
    public InvestmentDetails(String userId, int productId, int investmentAmount){
        this.userId = userId;
        this.productId = productId;
        this.investmentAmount = investmentAmount;
        this.investmentDate = new Timestamp(System.currentTimeMillis());
    }

}
