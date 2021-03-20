package com.kakao.pay.investment.store;

import com.kakao.pay.investment.domain.InvestmentDetails;
import com.kakao.pay.investment.domain.InvestmentDto;
import com.kakao.pay.investment.domain.InvestmentProduct;
import com.kakao.pay.investment.domain.PersonalInvestmentDto;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface InvestmentStore {

    List<InvestmentProduct> selectTotalInvestmentProductList(Timestamp from, Timestamp to);

    void saveInvestmentProduct(InvestmentProduct investmentProduct);

    void saveInvestmentDetails(InvestmentDetails investmentDetails);

    InvestmentProduct selectInvestmentProductById(int productId);

    List<PersonalInvestmentDto> selectPersonalInvestmentProductList(String xUserId);

}
