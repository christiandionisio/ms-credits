package com.example.mscredit.repo;

import com.example.mscredit.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


/**
 * Repository layer of Credit product.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
public interface CreditRepo extends ReactiveMongoRepository<Credit, String> {
  Mono<Credit> findByCustomerId(String customerId);
  Flux<Credit> findCreditByCustomerIdAndPaymentDateBefore(String customerId, LocalDateTime paymentDate);
}
