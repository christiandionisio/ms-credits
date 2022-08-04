package com.example.mscredit.controller;

import com.example.mscredit.dto.CreditDto;
import com.example.mscredit.dto.ResponseTemplateDto;
import com.example.mscredit.error.PersonalCustomerAlreadyHaveCreditException;
import com.example.mscredit.model.Credit;
import com.example.mscredit.service.CreditService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller layer of Credits.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
@RestController
@RequestMapping("/credits")
public class CreditController {

  @Autowired
  private CreditService service;

  private static final Logger logger = LogManager.getLogger(CreditController.class);

  private ModelMapper modelMapper = new ModelMapper();

  /**
   * Get list of Credits.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  @GetMapping
  public Flux<Credit> findAll() {
    logger.debug("Debugging log");
    logger.info("Info log");
    logger.warn("Hey, This is a warning!");
    logger.error("Oops! We have an Error. OK");
    logger.fatal("Damn! Fatal error. Please fix me.");
    return service.findAll();
  }

  /**
   * Save a credit product.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  @PostMapping
  public Mono<ResponseEntity<Object>> create(@RequestBody CreditDto creditDto) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    return service.create(modelMapper.map(creditDto, Credit.class))
            .flatMap(c -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)))
            .onErrorResume(e -> {
              if (e instanceof PersonalCustomerAlreadyHaveCreditException) {
                logger.error(e.getMessage());
                return Mono.just(new ResponseEntity<>(new ResponseTemplateDto(null,
                        e.getMessage()), HttpStatus.FORBIDDEN));
              }
              logger.error(e.getMessage());
              return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
            })
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PutMapping
  public Mono<Credit> update(@RequestBody CreditDto creditDto) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    return service.update(modelMapper.map(creditDto, Credit.class));
  }

  @DeleteMapping("/{creditId}")
  public Mono<Void> delete(@PathVariable String creditId) {
    return service.delete(creditId);
  }


  @GetMapping("/{id}")
  public Mono<Credit> read(@PathVariable String id) {
    return service.findById(id);
  }

  /**
   * Find Customer by Customer Id.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  @GetMapping("/findByCustomerId/{customerId}")
  public Mono<ResponseEntity<Credit>> findByCustomerId(@PathVariable String customerId) {
    return service.findByCustomerId(customerId)
            .flatMap(c -> Mono.just(new ResponseEntity<>(c, HttpStatus.OK)))
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
