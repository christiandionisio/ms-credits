package com.example.mscredit.controller;

import com.example.mscredit.dto.CreditDto;
import com.example.mscredit.dto.ResponseTemplateDto;
import com.example.mscredit.error.PersonalCustomerAlreadyHaveCreditException;
import com.example.mscredit.model.Credit;
import com.example.mscredit.service.CreditService;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
  private CreditService creditService;

  private static final Logger logger = LogManager.getLogger(CreditController.class);

  private ModelMapper modelMapper = new ModelMapper();

  /**
   * Get list of Credits.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<Credit>>> findAll() {
    return Mono.just(
            ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(creditService.findAll()));
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
    return creditService.create(modelMapper.map(creditDto, Credit.class))
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

  /**
   * Get detail of a credit by Id.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Credit>> read(@PathVariable String id) {
    return creditService.findById(id).map(credit -> ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(credit))
            .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Update Credit By Id.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Credit>> update(@RequestBody CreditDto creditDto,
                                               @PathVariable String id) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    return creditService.findById(id)
            .flatMap(c -> creditService.update(modelMapper.map(creditDto, Credit.class)))
            .map(creditUpdated -> ResponseEntity
                    .created(URI.create("/credits/".concat(creditUpdated.getCreditId())))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(creditUpdated))
            .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Delete Customer By Id.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  @DeleteMapping("/{creditId}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String creditId) {
    return creditService.findById(creditId)
            .flatMap(credit -> creditService.delete(credit.getCustomerId())
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
            .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  /**
   * Find Customer by Customer Id.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  @GetMapping("/findByCustomerId/{customerId}")
  public Mono<ResponseEntity<Credit>> findByCustomerId(@PathVariable String customerId) {
    return creditService.findByCustomerId(customerId)
            .flatMap(c -> Mono.just(new ResponseEntity<>(c, HttpStatus.OK)))
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
