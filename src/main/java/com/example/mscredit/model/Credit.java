package com.example.mscredit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "credits")
@Data
@AllArgsConstructor
public class Credit {
    @Id
    private String creditId;
    private BigDecimal creditBalance;
    private String paymentDate;
    private Integer timeLimit;
    private String initialDate;
    private String monthlyFee;
    private String creditType;
    private String customerId;
    private Integer interestRate;
}
