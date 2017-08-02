package com.ebi.bank.domain;

public class BankAccount {
    private final String id;

    public BankAccount(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
