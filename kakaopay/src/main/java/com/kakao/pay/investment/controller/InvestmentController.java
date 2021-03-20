package com.kakao.pay.investment.controller;

import com.kakao.pay.investment.domain.InvestmentDto;
import com.kakao.pay.investment.domain.PersonalInvestmentDto;
import com.kakao.pay.investment.service.InvestmentService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 투자 서비스 Controller
 */
@RestController
@RequestMapping("/api/investment")
public class InvestmentController {

    private InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService){
        this.investmentService = investmentService;
    }

    /**
     * 전체 투자 상품 조회
     *
     * @return
     */
    @ApiOperation(value = "전체 투자 상품 조회", notes = "상품 모집기간 내의 상품만 응답")
    @GetMapping(value = "/product/list")
    public Flux<List<InvestmentDto>> findAllInvestmentProduct(){
        return Flux.just(investmentService.findTotalInvestmentProductList());
    }

    /**
     * 투자하기
     *
     * @param xUserId 사용자 식별값
     * @param productId 상품 ID
     * @param investmentAmount 투자금액
     * @return
     */
    @ApiOperation(value = "투자하기", notes = "투자하기")
    @Parameters({
            @Parameter(name = "xUserId", required = true,  description = "사용자식별값"),
            @Parameter(name = "productId", required = true, description = "상품 ID"),
            @Parameter(name = "investmentAmount", required = true,  description = "투자 금액"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Sold Out - 총 투자모집 금액 초과"),
    })
    @GetMapping(value = "/invest/{productId}/{investmentAmount}")
    public Mono<String> invest(@RequestHeader(value="X-USER-ID") String xUserId, @PathVariable int productId, @PathVariable int investmentAmount){
        return Mono.justOrEmpty(investmentService.invest(xUserId, productId, investmentAmount));
    }


    /**
     * 나의 투자상품 조회
     *
     * @param xUserId 사용자 식별값
     * @return
     */
    @ApiOperation(value = "나의 투자상품 조회", notes = "사용자가 투자한 모든 상품을 반환")
    @Parameter(name = "xUserId", required = true,  description = "사용자식별값")
    @GetMapping(value = "/personal/product")
    public Flux<List<PersonalInvestmentDto>> findPersonalInvestment(@RequestHeader(value="X-USER-ID") String xUserId){
        return Flux.just(investmentService.findPersonalInvestmentProductList(xUserId));
    }

}
