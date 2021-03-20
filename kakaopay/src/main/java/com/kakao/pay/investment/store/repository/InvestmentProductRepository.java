package com.kakao.pay.investment.store.repository;

import com.kakao.pay.investment.store.jpo.InvestmentProuductJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface InvestmentProductRepository extends JpaRepository<InvestmentProuductJpo, String> {

    List<InvestmentProuductJpo> findByStartedAtLessThanEqualAndFinishedAtGreaterThanEqual(Timestamp startedAt, Timestamp finishedAt);
    Optional<InvestmentProuductJpo> findByProductId(int ProductId);
}
