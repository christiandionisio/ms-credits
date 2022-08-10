package com.example.mscredit.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Credit Dto.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
@Data
@Builder
public class CreditDto {
  private String creditId;
  private BigDecimal creditBalance;
  private String paymentDate;
  private Integer timeLimit;
  private String initialDate;
  private BigDecimal monthlyFee;
  private String creditType;
  private String customerId;
  private Integer interestRate;
}
