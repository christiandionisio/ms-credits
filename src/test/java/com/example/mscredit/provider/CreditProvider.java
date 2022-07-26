package com.example.mscredit.provider;

import com.example.mscredit.dto.CreditDto;
import com.example.mscredit.model.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CreditProvider {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static List<Credit> getCreditList(){
    List<Credit> creditList = new ArrayList<>();
    creditList.add(getCredit());
    return creditList;
  }

  public static Credit getCredit() {
    Credit credit = new Credit();
    credit.setCreditId("1");
    credit.setCreditBalance(BigDecimal.valueOf(5000));
    LocalDate localDate = LocalDate.parse("07/08/2022", FORMATTER);
    credit.setPaymentDate(localDate.atStartOfDay());
    credit.setTimeLimit(12);
    credit.setInitialDate("28/07/2022");
    credit.setMonthlyFee(BigDecimal.valueOf(458.33));
    credit.setCreditType("LOAN");
    credit.setCustomerId("1");
    credit.setInterestRate(10);
    return credit;
  }

  public static CreditDto getCreditDto() {
    return CreditDto.builder()
            .creditId("1")
            .creditBalance(BigDecimal.valueOf(5000))
            .paymentDate("07/08/2022")
            .timeLimit(12)
            .initialDate("28/07/2022")
            .monthlyFee(BigDecimal.valueOf(458.33))
            .creditType("LOAN")
            .customerId("1")
            .interestRate(10)
            .build();
  }
}
