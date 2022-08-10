package com.example.mscredit.service;

import com.example.mscredit.enums.CustomerTypeEnum;
import com.example.mscredit.error.PersonalCustomerAlreadyHaveCreditException;
import com.example.mscredit.model.Credit;
import com.example.mscredit.repo.CreditRepo;
import com.example.mscredit.util.CreditBusinessRulesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service layer implementations of Credit product.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */

@Service
public class CreditServiceImpl implements CreditService {

  @Autowired
  private CreditRepo repo;

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


  @Override
  public Flux<Credit> findAll() {
    return repo.findAll();
  }

  @Override
  public Mono<Credit> create(Credit credit) {
    return CreditBusinessRulesUtil.findCustomerById(credit.getCustomerId())
            .flatMap(customer -> (customer.getCustomerType()
                    .equalsIgnoreCase(CustomerTypeEnum.PERSONNEL.getValue()))
                    ? repo.findByCustomerId(customer.getCustomerId())
                    .flatMap(creditDB -> Mono
                            .<Credit>error(new PersonalCustomerAlreadyHaveCreditException()))
                    .switchIfEmpty(repo.save(credit))
                    : repo.save(credit)
            );
  }

  @Override
  public Mono<Credit> update(Credit credit) {
    return repo.save(credit);
  }

  @Override
  public Mono<Void> delete(String creditId) {
    return repo.deleteById(creditId);
  }

  @Override
  public Mono<Credit> findById(String id) {
    return repo.findById(id);
  }

  @Override
  public Mono<Credit> findByCustomerId(String customerId) {
    return repo.findByCustomerId(customerId);
  }

  @Override
  public Flux<Credit> findCreditByCustomerIdAndPaymentDateBefore(String customerId, String paymentDate) {
    LocalDate paymentDateLocalDate = LocalDate.parse(paymentDate, FORMATTER);
    return repo.findCreditByCustomerIdAndPaymentDateBefore(customerId, paymentDateLocalDate.atStartOfDay());
  }
}
