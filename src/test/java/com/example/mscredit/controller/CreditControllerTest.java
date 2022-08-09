package com.example.mscredit.controller;

import com.example.mscredit.dto.CreditDto;
import com.example.mscredit.error.PersonalCustomerAlreadyHaveCreditException;
import com.example.mscredit.model.Credit;
import com.example.mscredit.provider.CreditProvider;
import com.example.mscredit.service.CreditService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditControllerTest {
  @MockBean
  CreditService creditService;

  @Autowired
  private WebTestClient webClient;

  @BeforeEach
  void setUp() {
  }

  @Test
  @DisplayName("Get all credits")
  void findAll() {
    Mockito.when(creditService.findAll())
            .thenReturn(Flux.fromIterable(CreditProvider.getCreditList()));

    webClient.get()
            .uri("/credits")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Credit.class)
            .consumeWith(response -> {
              List<Credit> creditList = response.getResponseBody();
              creditList.forEach(c -> {
                System.out.println(c.getCreditBalance());
              });
              Assertions.assertThat(creditList.size() > 0).isTrue();
            });
    //.hasSize(1);
  }

  @Test
  @DisplayName("Create credit")
  void create() {
    Mockito.when(creditService.create(Mockito.any(Credit.class)))
            .thenReturn(Mono.just(CreditProvider.getCredit()));

    webClient.post().uri("/credits")
            .body(Mono.just(CreditProvider.getCreditDto()), CreditDto.class)
            .exchange()
            .expectStatus().isNoContent();
  }

  @Test
  @DisplayName("Create credit with PersonalCustomerAlreadyHaveCreditException")
  void createWithPersonalCustomerAlreadyHaveCreditException() {
    Mockito.when(creditService.create(Mockito.any(Credit.class)))
            .thenReturn(Mono.error(new PersonalCustomerAlreadyHaveCreditException()));

    webClient.post().uri("/credits")
            .body(Mono.just(CreditProvider.getCreditDto()), CreditDto.class)
            .exchange()
            .expectStatus().isForbidden();
  }

  @Test
  @DisplayName("Create credit with GeneralException")
  void createWithGeneralException() {
    Mockito.when(creditService.create(Mockito.any(Credit.class)))
            .thenReturn(Mono.error(new Exception("GeneralException TEST")));

    webClient.post().uri("/credits")
            .body(Mono.just(CreditProvider.getCreditDto()), CreditDto.class)
            .exchange()
            .expectStatus().is5xxServerError();
  }

  @Test
  @DisplayName("Read credit")
  void read() {
    Mockito.when(creditService.findById(Mockito.anyString()))
            .thenReturn(Mono.just(CreditProvider.getCredit()));

    webClient.get().uri("/credits/1")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody(Credit.class)
            .consumeWith(response -> {
              Credit credit = response.getResponseBody();
              Assertions.assertThat(credit.getCreditId()).isEqualTo(CreditProvider.getCredit().getCreditId());
            });
  }

  @Test
  @DisplayName("Update credit")
  void update() {
    Mockito.when(creditService.findById(Mockito.anyString()))
            .thenReturn(Mono.just(CreditProvider.getCredit()));
    Mockito.when(creditService.update(Mockito.any(Credit.class)))
            .thenReturn(Mono.just(CreditProvider.getCredit()));

    webClient.put().uri("/credits/1")
            .body(Mono.just(CreditProvider.getCreditDto()), CreditDto.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Credit.class)
            .isEqualTo(CreditProvider.getCredit());
  }

  @Test
  @DisplayName("Delete credit")
  void delete() {
    Mockito.when(creditService.findById(Mockito.anyString()))
            .thenReturn(Mono.just(CreditProvider.getCredit()));
    Mockito.when(creditService.delete(Mockito.anyString()))
            .thenReturn(Mono.empty());

    webClient.delete().uri("/credits/1")
            .exchange()
            .expectStatus().isNoContent();
  }

  @Test
  @DisplayName("Get Credit by Customer")
  void findByCustomerId() {
    Mockito.when(creditService.findByCustomerId(Mockito.anyString()))
            .thenReturn(Mono.just(CreditProvider.getCredit()));

    webClient.get().uri("/credits/findByCustomerId/1")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk();
  }
}
