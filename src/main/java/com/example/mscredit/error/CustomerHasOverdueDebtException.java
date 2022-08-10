package com.example.mscredit.error;

public class CustomerHasOverdueDebtException extends Exception {
    public CustomerHasOverdueDebtException() {
        super("Customer has overdue debt");
    }
}
