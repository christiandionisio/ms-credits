package com.example.mscredit.service;

import com.example.mscredit.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {
    Flux<Credit> findAll();
    Mono<Credit> create(Credit credit);
    Mono<Credit> update(Credit credit);
    Mono<Void> delete(String creditId);

    Mono<Credit> findById(String id);
}
