package com.kakao.pay.investment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentDto {

    private int productId; //상품ID
    private String title;   //상품제목
    private int totalInvestingAmount;   //총모집금액
    private int currentInvestingAmount; //현재모집금액
    private int investerCnt;  //투자자수
    private String investmentStatus; //투자모집상태
    private int investRange;  //상품모집기간

    public InvestmentDto(InvestmentProduct investmentProduct){

        BeanUtils.copyProperties(investmentProduct, this);

        this.investRange = calculateDayDifference(investmentProduct.getFinishedAt(), investmentProduct.getStartedAt());

        if(investmentProduct.getInvestmentStatus().equals(StatusEnum.ING.name())) this.investmentStatus = StatusEnum.ING.getName();
        else if(investmentProduct.getInvestmentStatus().equals(StatusEnum.END.name())) this.investmentStatus = StatusEnum.END.getName();

    }
    private int calculateDayDifference(Timestamp from, Timestamp to){

        Date fromDate = new Date();
        fromDate.setTime(from.getTime());
        String formattedFromDate = new SimpleDateFormat("yyyy-MM-dd").format(fromDate);
        Calendar getFromDate = Calendar.getInstance();
        getFromDate.setTime(fromDate);

        Date toDate = new Date();
        toDate.setTime(to.getTime());
        String formattedToDate = new SimpleDateFormat("yyyy-MM-dd").format(toDate);
        Calendar getToDate = Calendar.getInstance();
        getToDate.setTime(toDate); //특정 일자

        long diffSec = (getFromDate.getTimeInMillis() - getToDate.getTimeInMillis() ) / 1000;
        int diffDays = (int) (diffSec / (24*60*60)); //일자수 차이

        return diffDays;
    }



}
