package com.kakao.pay.investment.store.jpo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class InvestmentDetailsPK implements Serializable {


    private String userId; //사용자ID

    private int productId; //상품ID
}
