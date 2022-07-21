package com.example.mscredit.controller;

import com.example.mscredit.model.Credit;
import com.example.mscredit.service.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credits")
public class CreditController {

    @Autowired
    private ICreditService service;

    @GetMapping
    public Flux<Credit> findAll() {
        return service.findAll();
    }

    @PostMapping
    public Mono<Credit> create(Credit credit) {
        return service.create(credit);
    }

    @PutMapping
    public Mono<Credit> update(Credit credit) {
        return service.update(credit);
    }

    @DeleteMapping("/{creditId}")
    public Mono<Void> delete(@PathVariable String creditId) {
        return service.delete(creditId);
    }


}
