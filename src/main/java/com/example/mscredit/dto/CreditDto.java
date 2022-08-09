package com.example.mscredit.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

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