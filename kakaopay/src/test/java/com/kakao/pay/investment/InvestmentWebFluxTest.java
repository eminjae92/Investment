package com.kakao.pay.investment;


import com.kakao.pay.investment.controller.InvestmentController;
import com.kakao.pay.investment.service.InvestmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = InvestmentController.class)
public class InvestmentWebFluxTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private InvestmentService investmentService;

    @Test
    public void testInvestmentProduct() {
        String url = "/api/investment/product/list";

        this.webTestClient.get().uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();
    }

    @Test
    public void testInvest() {
        String url = "/api/investment/invest/1/1000";

        this.webTestClient.get().uri(url)
                .header("X-USER-ID","et8659")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();
    }

    @Test
    public void testPersonalInvestment() {
        String url = "/api/investment/personal/product";

        this.webTestClient.get().uri(url).header("X-USER-ID","et8659")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();
    }
}
