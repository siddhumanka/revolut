package com.revolut.customer.repositories;

import com.revolut.customer.domains.CustomerBankAccountDetails;

import java.util.Optional;
import java.util.Set;

public class AccountStorageRepository implements Repository {
    private final Set<CustomerBankAccountDetails> accounts;

    public AccountStorageRepository(Set<CustomerBankAccountDetails> accounts) {
        this.accounts = accounts;
    }

    public Set<CustomerBankAccountDetails> getAccounts() {
        return accounts;
    }

    @Override
    public boolean addCustomerAccountDetails(CustomerBankAccountDetails customerBankAccountDetails) {
        return accounts.add(customerBankAccountDetails);
    }

    @Override
    public Optional<CustomerBankAccountDetails> getCustomerDetailsByAccountNumber(int accountNumber) {
        return accounts.stream().filter(details -> details.getAccountNumber() == accountNumber).findFirst();
    }
}
