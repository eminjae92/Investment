package com.kakao.pay.investment.domain;

import lombok.*;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInvestmentDto {

    private int productId;
    private String title;
    private int totalInvestingAmount;
    private int personalInvestAmount;
    private String investmentDate;

}
