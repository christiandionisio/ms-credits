package com.example.mscredit.error;

public class PersonalCustomerAlreadyHaveCreditException extends Exception {

    public PersonalCustomerAlreadyHaveCreditException() {
        super("Personal customer can only have 1 credit");
    }
}
