package com.example.mscredit.repo;

import com.example.mscredit.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


/**
 * Repository layer of Credit product.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
public interface CreditRepo extends ReactiveMongoRepository<Credit, String> {
  Mono<Credit> findByCustomerId(String customerId);
}
