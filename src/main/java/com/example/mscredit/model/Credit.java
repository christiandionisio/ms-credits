package com.example.mscredit.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Credit document.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
@Document(collection = "credits")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credit {
  @Id
  private String creditId;
  private BigDecimal creditBalance;
  private LocalDateTime paymentDate;
  private Integer timeLimit;
  private String initialDate;
  private BigDecimal monthlyFee;
  private String creditType;
  private String customerId;
  private Integer interestRate;
}
