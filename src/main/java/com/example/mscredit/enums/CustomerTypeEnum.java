package com.example.mscredit.enums;


/**
 * Type of customers.
 *
 * @author Alisson Arteaga / Christian Dionisio
 * @version 1.0
 */
public enum CustomerTypeEnum {
  BUSINESS("BUSINESS"),
  PERSONNEL("PERSONNEL");
  private final String value;

  CustomerTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
