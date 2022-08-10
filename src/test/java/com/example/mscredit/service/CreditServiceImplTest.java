package com.example.mscredit.service;

import com.example.mscredit.model.Credit;
import com.example.mscredit.provider.CreditProvider;
import com.example.mscredit.repo.CreditRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CreditServiceImplTest {

    @MockBean
    private CreditRepo repo;

    @Autowired
    private CreditServiceImpl service;

    @Test
    void findCreditByCustomerIdAndPaymentDateBeforeTest() {

        List<Credit> creditList = new ArrayList<>();
        creditList.add(CreditProvider.getCredit());

        Mockito.when(repo.findCreditByCustomerIdAndPaymentDateBefore(Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(Flux.fromIterable(creditList));

        StepVerifier.create(service.findCreditByCustomerIdAndPaymentDateBefore("someCustomerId",
                        "10/08/2022"))
                .expectNextCount(1)
                .verifyComplete();
    }

}