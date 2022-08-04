package com.example.mscredit.error;


/**
 * Manage Exception related to Business Rules of Credit product.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
public class PersonalCustomerAlreadyHaveCreditException extends Exception {

  public PersonalCustomerAlreadyHaveCreditException() {
    super("Personal customer can only have 1 credit");
  }
}
