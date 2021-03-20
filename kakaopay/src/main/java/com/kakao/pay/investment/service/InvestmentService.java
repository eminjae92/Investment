package com.kakao.pay.investment.service;

import com.kakao.pay.investment.domain.InvestmentDto;
import com.kakao.pay.investment.domain.PersonalInvestmentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface InvestmentService {

    List<InvestmentDto> findTotalInvestmentProductList();
    String invest(String xUserId, int productId, int investmentAmount);
    List<PersonalInvestmentDto> findPersonalInvestmentProductList(String xUserId);
}
