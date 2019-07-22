package com.revolut.customer.services;


import com.revolut.customer.storages.AccountStorage;

public class TransactionService {
    private final AccountStorage storage;

    public TransactionService(AccountStorage storage) {
        this.storage = storage;
    }

    public void creditAmountInAccount(int amount, int accountNumber) {
        storage.getCustomerDetailsByAccountNumber(accountNumber).ifPresent(accountDetails -> {
            accountDetails.setTotalBalance(accountDetails.getTotalBalance() + amount);
        });
    }
}
