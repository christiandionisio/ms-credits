package com.example.mscredit.repo;

import com.example.mscredit.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditRepo extends ReactiveMongoRepository<Credit, String> {
  Mono<Credit> findByCustomerId(String customerId);
}
