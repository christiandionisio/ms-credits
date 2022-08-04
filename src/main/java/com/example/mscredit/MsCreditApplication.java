package com.example.mscredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * CreditCard application.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
@EnableEurekaClient
@SpringBootApplication
public class MsCreditApplication {

  public static void main(String[] args) {
    SpringApplication.run(MsCreditApplication.class, args);
  }

}
