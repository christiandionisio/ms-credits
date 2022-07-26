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
                .flatMap(customer -> (customer.getCustomerType().equalsIgnoreCase(CustomerTypeEnum.PERSONNEL.getValue()))
                        ? repo.findByCustomerId(customer.getCustomerId())
                            .flatMap(creditDB -> Mono.<Credit>error(new PersonalCustomerAlreadyHaveCreditException()))
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
}
