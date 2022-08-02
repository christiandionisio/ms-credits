package com.example.mscredit.controller;

import com.example.mscredit.dto.ResponseTemplateDTO;
import com.example.mscredit.error.PersonalCustomerAlreadyHaveCreditException;
import com.example.mscredit.model.Credit;
import com.example.mscredit.service.ICreditService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credits")
public class CreditController {

    @Autowired
    private ICreditService service;

    private static final Logger logger = LogManager.getLogger(CreditController.class);

    @GetMapping
    public Flux<Credit> findAll() {
        logger.debug("Debugging log");
        logger.info("Info log");
        logger.warn("Hey, This is a warning!");
        logger.error("Oops! We have an Error. OK");
        logger.fatal("Damn! Fatal error. Please fix me.");
        return service.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> create(@RequestBody Credit credit) {
        return service.create(credit)
                .flatMap(c -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)))
                .onErrorResume(e -> {
                    if (e instanceof PersonalCustomerAlreadyHaveCreditException) {
                        logger.error(e.getMessage());
                        return Mono.just(new ResponseEntity<>(new ResponseTemplateDTO(null,
                                e.getMessage()), HttpStatus.FORBIDDEN));
                    }
                    logger.error(e.getMessage());
                    return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public Mono<Credit> update(@RequestBody Credit credit) {
        return service.update(credit);
    }

    @DeleteMapping("/{creditId}")
    public Mono<Void> delete(@PathVariable String creditId) {
        return service.delete(creditId);
    }


    @GetMapping("/{id}")
    public Mono<Credit> read(@PathVariable String id){
        Mono<Credit> credit = service.findById(id);
        return credit;
    }

    @GetMapping("/findByCustomerId/{customerId}")
    public Mono<ResponseEntity<Credit>> findByCustomerId(@PathVariable String customerId){
        return service.findByCustomerId(customerId)
                .flatMap(c -> Mono.just(new ResponseEntity<>(c, HttpStatus.OK)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
