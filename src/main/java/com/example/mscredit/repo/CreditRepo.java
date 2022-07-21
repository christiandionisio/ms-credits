package com.example.mscredit.repo;

import com.example.mscredit.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditRepo extends ReactiveMongoRepository<Credit, String> {

}
