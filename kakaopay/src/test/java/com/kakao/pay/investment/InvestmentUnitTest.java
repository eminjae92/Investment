package com.kakao.pay.investment;

import com.kakao.pay.investment.config.exception.NoProductException;
import com.kakao.pay.investment.config.exception.SoldOutException;
import com.kakao.pay.investment.domain.InvestmentDetails;
import com.kakao.pay.investment.domain.InvestmentProduct;
import com.kakao.pay.investment.domain.PersonalInvestmentDto;
import com.kakao.pay.investment.service.InvestmentService;
import com.kakao.pay.investment.store.InvestmentStore;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvestmentUnitTest {


    @Autowired
    private InvestmentStore investmentStore;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    EntityManager em;


    /*
     * 총 투자모집금액 달성시 투자는 마감되고 상품은 Sold Out 예외 처리 테스트
     * */
    @Test
    public void testEndProductAndSoldOutException() {

        investmentService.invest("et8659", 2, 200000);


        InvestmentProduct investmentProduct = investmentStore.selectInvestmentProductById(2);

        Assertions.assertThat(investmentProduct.getInvestmentStatus()).isEqualTo("END");

        boolean exceptionCatched = false;

        try {
            investmentService.invest("et8659", 2, 100000);
        } catch (SoldOutException e) {
            exceptionCatched = true;
        }

        Assert.assertTrue(exceptionCatched);


    }

    /*
     * 투자 후 투자상품의 누적 투자모집 금액, 투자자 수 증가 테스트
     * */
    @Test
    public void testAfterInvestIncreasing() {
        investmentService.invest("et8659", 4, 10000);

        InvestmentProduct investmentProduct = investmentStore.selectInvestmentProductById(4);

        Assertions.assertThat(investmentProduct.getInvesterCnt()).isEqualTo(1);

        Assertions.assertThat(investmentProduct.getCurrentInvestingAmount()).isEqualTo(10000);

    }

    /*
     * 투자 상품 없을 시 예외 처리 테스트
     * */
    @Test
    public void testNoProductException() {
        boolean exceptionCatched = false;

        try {
            investmentService.invest("et8659", 400, 300000);
        } catch (NoProductException e) {
            exceptionCatched = true;
        }

        Assert.assertTrue(exceptionCatched);
    }


        /*
         * 투자 상품 저장 및 조회  단위테스트
         * */
        @Test
        public void testSaveAndSelectInvestmentProduct () {

            InvestmentProduct investmentProduct = InvestmentProduct.builder()
                    .title("상가 포트폴리오 TEST")
                    .totalInvestingAmount(30000)
                    .currentInvestingAmount(0)
                    .investerCnt(0)
                    .startedAt(Timestamp.valueOf("2021-03-11 00:00:00"))
                    .finishedAt(Timestamp.valueOf("2021-11-11 00:00:00"))
                    .investmentStatus("ING").build();
            investmentStore.saveInvestmentProduct(investmentProduct);

            List<InvestmentProduct> investmentProductList
                    = investmentStore.selectTotalInvestmentProductList(new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()));

            Assertions.assertThat(investmentProductList.get(investmentProductList.size() - 1))
                    .isEqualToIgnoringGivenFields(investmentProduct, "productId");

        }

        /*
         * 투자 내역 저장 및 조회 단위테스트
         * */
        @Test
        public void testSaveAndSelectInvest () {

            InvestmentDetails investmentDetails = InvestmentDetails.builder()
                                                    .userId("et8659").productId(1)
                                                        .investmentAmount(100).build();
            investmentStore.saveInvestmentDetails(investmentDetails);

            List<PersonalInvestmentDto> personalInvestmentDtoList = investmentService.findPersonalInvestmentProductList("et8659");

            Assert.assertTrue(personalInvestmentDtoList.size() > 0);

        }

        /*
         * 투자상품 테이블 조회해서 현재 투자모집 금액 조회 단위 테스트
         * */
        @Test
        public void testCurrentInvestmentDetails () {

            investmentService.invest("et8659", 1, 1000);

            InvestmentProduct investmentProduct = investmentStore.selectInvestmentProductById(1);

            Assertions.assertThat(investmentProduct.getCurrentInvestingAmount()).isEqualTo(1000);

        }


    }
