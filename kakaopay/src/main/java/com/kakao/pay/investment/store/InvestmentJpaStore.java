package com.kakao.pay.investment.store;

import com.kakao.pay.investment.config.exception.NoProductException;
import com.kakao.pay.investment.domain.InvestmentDetails;
import com.kakao.pay.investment.domain.InvestmentDto;
import com.kakao.pay.investment.domain.InvestmentProduct;
import com.kakao.pay.investment.domain.PersonalInvestmentDto;
import com.kakao.pay.investment.store.jpo.InvestmentDetailsJpo;
import com.kakao.pay.investment.store.jpo.InvestmentProuductJpo;
import com.kakao.pay.investment.store.repository.InvestmentDetailsRepository;
import com.kakao.pay.investment.store.repository.InvestmentProductRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class InvestmentJpaStore implements InvestmentStore{

    private InvestmentDetailsRepository investmentDetailsRepository;
    private InvestmentProductRepository investmentProductRepository;

    public InvestmentJpaStore(InvestmentDetailsRepository investmentDetailsRepository, InvestmentProductRepository investmentProductRepository) {
        this.investmentDetailsRepository = investmentDetailsRepository;
        this.investmentProductRepository = investmentProductRepository;
    }



    @Override
    public List<InvestmentProduct> selectTotalInvestmentProductList(Timestamp from, Timestamp to) {
        return investmentProductRepository.findByStartedAtLessThanEqualAndFinishedAtGreaterThanEqual(from, to)
                .stream().map(InvestmentProuductJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void saveInvestmentProduct(InvestmentProduct investmentProduct) {
        InvestmentProuductJpo investmentProuductJpo = new InvestmentProuductJpo(investmentProduct);
        investmentProductRepository.save(investmentProuductJpo);
    }

    @Override
    public void saveInvestmentDetails(InvestmentDetails investmentDetails) {
        InvestmentDetailsJpo investmentDetailsJpo = new InvestmentDetailsJpo(investmentDetails);
        investmentDetailsRepository.save(investmentDetailsJpo);
    }

    @Override
    public InvestmentProduct selectInvestmentProductById(int productId) {

        return investmentProductRepository.findByProductId(productId).isPresent() ? investmentProductRepository.findByProductId(productId).get().toDomain() : null;
    }

    @Override
    public List<PersonalInvestmentDto> selectPersonalInvestmentProductList(String xUserId) {

        return investmentDetailsRepository.findAllByUserId(xUserId).stream().map(InvestmentDetailsJpo::toDto).collect(Collectors.toList());
    }


}
