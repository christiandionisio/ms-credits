package com.example.mscredit.service;

import com.example.mscredit.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Service layer of Credit product.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
public interface CreditService {
  Flux<Credit> findAll();

  Mono<Credit> create(Credit credit);

  Mono<Credit> update(Credit credit);

  Mono<Void> delete(String creditId);

  Mono<Credit> findById(String id);

  Mono<Credit> findByCustomerId(String customerId);
}
