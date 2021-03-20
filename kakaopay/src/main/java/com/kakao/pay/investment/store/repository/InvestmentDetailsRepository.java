package com.kakao.pay.investment.store.repository;

import com.kakao.pay.investment.store.jpo.InvestmentDetailsJpo;
import com.kakao.pay.investment.store.jpo.InvestmentDetailsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface InvestmentDetailsRepository extends JpaRepository<InvestmentDetailsJpo, Integer> {

    List<InvestmentDetailsJpo> findAllByUserId(String userId);

    List<InvestmentDetailsJpo> findAllByUserIdAndProductId(String userId, int ProductId);
}
