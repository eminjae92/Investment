package com.kakao.pay.investment.store.jpo;

import com.kakao.pay.investment.domain.InvestmentProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "INVESTMENT_PRODUCT")
@NoArgsConstructor
@Getter
@Setter
public class InvestmentProuductJpo {

    @Id
    @Column(name = "PRODUCT_ID")
    @GenericGenerator(name = "PRODUCT_ID", strategy = "com.kakao.pay.investment.store.jpo.ProductIdGenerator")
    @GeneratedValue(generator = "PRODUCT_ID")
    private int productId; //상품ID

    @Column(name = "TITLE")
    private String title; //상품명

    @Column(name = "TOTAL_INVESTING_AMOUNT")
    private int totalInvestingAmount; //총 투자 모집금액
    
    @Column(name = "CURRENT_INVESTING_AMOUNT")
    private int currentInvestingAmount; //누적 투자 모집금액

    @Column(name = "INVESTER_CNT")
    private int investerCnt; //투자자 수
    
    @Column(name = "STARTED_AT")
    private Timestamp startedAt; //투자시작일시

    @Column(name = "FINISHED_AT")
    private Timestamp finishedAt; //투자종료일시

    @Column(name = "INVESTMENT_STATUS")
    private String investmentStatus; //투자모집상태

    public InvestmentProuductJpo(InvestmentProduct investmentProduct){
        BeanUtils.copyProperties(investmentProduct, this);
    }

    public InvestmentProduct toDomain() {
        InvestmentProduct investmentProduct = new InvestmentProduct();
        BeanUtils.copyProperties(this, investmentProduct);
        return investmentProduct;
    }

}
