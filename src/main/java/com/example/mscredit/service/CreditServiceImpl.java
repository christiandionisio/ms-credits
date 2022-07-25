package com.example.mscredit.service;

import com.example.mscredit.model.Credit;
import com.example.mscredit.repo.CreditRepo;
import com.example.mscredit.util.CreditBusinessRulesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements ICreditService {

    @Autowired
    private CreditRepo repo;


    @Override
    public Flux<Credit> findAll() {
        return repo.findAll();
    }

    @Override
    public Mono<Credit> create(Credit credit) {
        return CreditBusinessRulesUtil.findCustomerById(credit.getCustomerId())
                .flatMap(customer -> repo.save(credit));
    }

    @Override
    public Mono<Credit> update(Credit credit) {
        return repo.save(credit);
    }

    @Override
    public Mono<Void> delete(String creditId) {
        return repo.deleteById(creditId);
    }
}
