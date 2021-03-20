package com.kakao.pay.investment.service;

import com.kakao.pay.investment.config.exception.NoProductException;
import com.kakao.pay.investment.config.exception.SoldOutException;
import com.kakao.pay.investment.domain.*;
import com.kakao.pay.investment.store.InvestmentStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvestmentLogic implements InvestmentService{

    private InvestmentStore investmentStore;

    public InvestmentLogic(InvestmentStore investmentStore){
        this.investmentStore = investmentStore;
    }

    @Override
    public List<InvestmentDto> findTotalInvestmentProductList() {

        return investmentStore.selectTotalInvestmentProductList(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()))
                .stream().map(investmentProduct -> new InvestmentDto(investmentProduct)).collect(Collectors.toList());
    }

    @Override
    public String invest(String xUserId, int productId, int investmentAmount) {
        //투자상품 테이블 조회해서 현재 투자모집 금액 조회
        log.info("투자상품 테이블 조회");
        InvestmentProduct investmentProduct = investmentStore.selectInvestmentProductById(productId);

        //투자상품이 없을 경우 사용자 예외 처리(존재하지 않는 투자상품입니다.)
        if(investmentProduct == null) {
            log.error("존재하지 않는 투자상품입니다.");
            throw new NoProductException();
        }

        //투자금액 + 현재 투자모집 금액
        int expectedAmount = investmentProduct.getCurrentInvestingAmount() + investmentAmount;
        log.info("투자금액 + 현재 투자모집 금액 = {}", expectedAmount);

        //if 투자금액 + 현재 투자모집 금액 > 총투자모집 금액 Sold Out Exception
        if(expectedAmount > investmentProduct.getTotalInvestingAmount()) {
            log.error("투자금액 + 현재 투자모집 금액 > 총투자모집 금액 => Sold Out");
            throw new SoldOutException();
        } else {
            //투자상품테이블 업데이트 누적투자모집금액 증가, 투자자 수 증가
            investmentProduct.setCurrentInvestingAmount(expectedAmount);
            investmentProduct.setInvesterCnt(investmentProduct.getInvesterCnt()+1);

            //투자상품 마감시 마감처리
            if(expectedAmount == investmentProduct.getTotalInvestingAmount()) {
                log.info("투자상품 마감처리");
                investmentProduct.setInvestmentStatus(StatusEnum.END.name());
            }

            log.info("투자상품 테이블 저장");
            //투자상품 테이블 저장
            investmentStore.saveInvestmentProduct(investmentProduct);

            //투자내역 객체 생성
            InvestmentDetails investmentDetails = InvestmentDetails.builder()
                                                    .userId(xUserId)
                                                    .productId(productId)
                                                    .investmentAmount(investmentAmount).build();
            //투자내역 테이블 저장
            log.info("투자내역 테이블 저장");
            investmentStore.saveInvestmentDetails(investmentDetails);

        }
        return "SUCCESS";

    }

    @Override
    public List<PersonalInvestmentDto> findPersonalInvestmentProductList(String xUserId) {

        return investmentStore.selectPersonalInvestmentProductList(xUserId);
    }
}
