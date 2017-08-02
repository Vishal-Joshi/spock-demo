package com.ebi.bank.service;

import com.ebi.bank.domain.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class BankAccountService {
    private final IdGenerator generator;
    private final Map<String, BankAccount> bankAccounts = new HashMap<>();

    public BankAccountService(IdGenerator generator) {
        this.generator = generator;
    }

    /**
     * Creates a bank account with specified id
     * @param id the id of the bank account to be created
     * @throws IllegalArgumentException if there is already a bank account with the specified id
     */
    public void createBankAccount(String id) {
        if (bankAccounts.keySet().contains(id)) {
            throw new IllegalArgumentException("There's already a bank account with the id: " + id);
        }
        bankAccounts.put(id, new BankAccount(id));
    }

    /**
     * Generates a bank account with default id type
     */
    public void generateBankAccount() {
        String id = generator.generate();

        bankAccounts.put(id, new BankAccount(id));
    }

    /**
     * Generates a bank account with specified id type
     * @param type the id type of the generated bank account
     */
    public void generateBankAccount(IdGenerator.Type type) {
        String id = generator.generate(type);

        bankAccounts.put(id, new BankAccount(id));
    }

    /**
     * @param id the id of the bank account to retrieve
     * @return the bank account with the specified id
     * @throws IllegalArgumentException if there is no bank account with the specified id
     */
    public BankAccount getBankAccount(String id) {
        BankAccount bankAccount = bankAccounts.get(id);

        if (bankAccount == null) {
            throw new IllegalArgumentException("There's no bank account with id: " + id);
        }

        return bankAccount;
    }
}
