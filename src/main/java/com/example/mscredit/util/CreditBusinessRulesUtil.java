package com.example.mscredit.util;

import com.example.mscredit.dto.CreditCardDto;
import com.example.mscredit.dto.CreditDto;
import com.example.mscredit.dto.CustomerDto;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Layer of communication with other services.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
public class CreditBusinessRulesUtil {

  /**
   * Communication with ms-customer.
   *
   * @author Alisson Arteaga / Christian Dionisio
   * @version 1.0
   */
  public static Mono<CustomerDto> findCustomerById(String id) {
    return WebClient.create().get()
            .uri("http://localhost:9082/customers/" + id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CustomerDto.class);
  }

  public static Flux<CreditCardDto> getCreditCardsWithOverdueDebt(String idCustomer) {
    return WebClient.create().get()
            .uri("http://localhost:9084/credit-cards/creditCardsWithOverdueDebt?" +
                    "customerId=" + idCustomer + "&hasDebt=true")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(CreditCardDto.class);
  }

}
