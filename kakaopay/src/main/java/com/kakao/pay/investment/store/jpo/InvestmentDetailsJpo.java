package com.kakao.pay.investment.store.jpo;

import com.kakao.pay.investment.domain.InvestmentDetails;
import com.kakao.pay.investment.domain.PersonalInvestmentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "INVESTMENT_DETAILS")
@NoArgsConstructor
@Getter
@Setter
public class InvestmentDetailsJpo {

    @Id
    @Column(name = "SEQUENCE_NUMBER")
    @GenericGenerator(name = "SEQUENCE_NUMBER", strategy = "com.kakao.pay.investment.store.jpo.DetailsIdGenerator")
    @GeneratedValue(generator = "SEQUENCE_NUMBER")
    private int sequenceNumber; //투자내역ID

    @Column(name = "USER_ID")
    private String userId; //사용자ID

    @Column(name = "PRODUCT_ID")
    private int productId; //상품ID

    @Column(name = "INVESTMENT_AMOUNT")
    private int investmentAmount; //투자금액

    @Column(name = "INVESTMENT_DATE")
    private Timestamp investmentDate; //투자일시

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false)
    private InvestmentProuductJpo investmentProuductJpo;

    public InvestmentDetailsJpo(InvestmentDetails investmentDetails){
        BeanUtils.copyProperties(investmentDetails, this);
    }

    public InvestmentDetails toDomain() {
        InvestmentDetails investmentDetails = new InvestmentDetails();
        BeanUtils.copyProperties(this, investmentDetails);
        return investmentDetails;
    }
    public PersonalInvestmentDto toDto() {
        PersonalInvestmentDto personalInvestmentDto = PersonalInvestmentDto.builder()
                                                        .productId(this.productId)
                                                        .title(this.investmentProuductJpo != null ? this.investmentProuductJpo.getTitle() : null)
                                                        .totalInvestingAmount(this.investmentProuductJpo != null ? this.investmentProuductJpo.getTotalInvestingAmount() : 0)
                                                        .personalInvestAmount(this.investmentAmount)
                                                        .investmentDate(String.valueOf(this.investmentDate)).build();

        return personalInvestmentDto;
    }
}
