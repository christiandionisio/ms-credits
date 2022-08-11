package com.example.mscredit.error;

public class CustomerHasOverdueCreditCardDebtException extends Exception {
    public CustomerHasOverdueCreditCardDebtException() {
        super("Customer has overdue credit card debt");
    }
}
