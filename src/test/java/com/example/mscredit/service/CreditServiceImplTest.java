package com.example.mscredit.service;

import com.example.mscredit.dto.CustomerDto;
import com.example.mscredit.error.CustomerHasOverdueDebtException;
import com.example.mscredit.model.Credit;
import com.example.mscredit.provider.CreditProvider;
import com.example.mscredit.repo.CreditRepo;
import com.example.mscredit.util.CreditBusinessRulesUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

    @Test
    void createCreditForPersonalCustomerTest() {
        try (MockedStatic<CreditBusinessRulesUtil> mockedStatic = Mockito.mockStatic(CreditBusinessRulesUtil.class)) {
            mockedStatic.when(() -> CreditBusinessRulesUtil.findCustomerById(Mockito.anyString()))
                    .thenReturn(Mono.just(getCustomerPersonal()));

            Mockito.when(repo.findByCustomerId(Mockito.anyString()))
                    .thenReturn(Mono.empty());

            Mockito.when(repo.save(Mockito.any(Credit.class)))
                    .thenReturn(Mono.just(CreditProvider.getCredit()));

            Mockito.when(repo.findCreditByCustomerIdAndPaymentDateBefore(Mockito.anyString(),
                            Mockito.any(LocalDateTime.class)))
                    .thenReturn(Flux.empty());

            StepVerifier.create(service.create(CreditProvider.getCredit()))
                    .expectNext(CreditProvider.getCredit())
                    .verifyComplete();

        }
    }


    @Test
    void createCreditForBusinessCustomerTest() {
        try (MockedStatic<CreditBusinessRulesUtil> mockedStatic = Mockito.mockStatic(CreditBusinessRulesUtil.class)) {
            mockedStatic.when(() -> CreditBusinessRulesUtil.findCustomerById(Mockito.anyString()))
                    .thenReturn(Mono.just(getCustomerBusiness()));

            Mockito.when(repo.findByCustomerId(Mockito.anyString()))
                    .thenReturn(Mono.empty());

            Mockito.when(repo.save(Mockito.any(Credit.class)))
                    .thenReturn(Mono.just(CreditProvider.getCredit()));

            Mockito.when(repo.findCreditByCustomerIdAndPaymentDateBefore(Mockito.anyString(),
                            Mockito.any(LocalDateTime.class)))
                    .thenReturn(Flux.empty());

            StepVerifier.create(service.create(CreditProvider.getCredit()))
                    .expectNext(CreditProvider.getCredit())
                    .verifyComplete();

        }
    }

    @Test
    void createCreditWithCustomerHasOverdueDebtExceptionTest() {
        try (MockedStatic<CreditBusinessRulesUtil> mockedStatic = Mockito.mockStatic(CreditBusinessRulesUtil.class)) {
            mockedStatic.when(() -> CreditBusinessRulesUtil.findCustomerById(Mockito.anyString()))
                    .thenReturn(Mono.just(getCustomerBusiness()));

            Mockito.when(repo.findByCustomerId(Mockito.anyString()))
                    .thenReturn(Mono.empty());

            Mockito.when(repo.save(Mockito.any(Credit.class)))
                    .thenReturn(Mono.just(CreditProvider.getCredit()));

            List<Credit> creditList = new ArrayList<>();
            creditList.add(CreditProvider.getCredit());
            Mockito.when(repo.findCreditByCustomerIdAndPaymentDateBefore(Mockito.anyString(),
                            Mockito.any(LocalDateTime.class)))
                    .thenReturn(Flux.fromIterable(creditList));

            StepVerifier.create(service.create(CreditProvider.getCredit()))
                    .expectError(CustomerHasOverdueDebtException.class)
                    .verify();

        }
    }

    private CustomerDto getCustomerBusiness() {
        CustomerDto customer = new CustomerDto();
        customer.setCustomerId("1");
        customer.setCustomerType("BUSINESS");
        return customer;
    }

    private CustomerDto getCustomerPersonal() {
        CustomerDto customer = new CustomerDto();
        customer.setCustomerId("1");
        customer.setCustomerType("PERSONNEL");
        return customer;
    }

}