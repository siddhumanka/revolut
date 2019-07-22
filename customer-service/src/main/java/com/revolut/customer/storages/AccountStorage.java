package com.revolut.customer.storages;

import com.revolut.customer.domains.CustomerBankAccountDetails;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AccountStorage {
    private final Set<CustomerBankAccountDetails> accounts = new HashSet<>();

    public Set<CustomerBankAccountDetails> getAccounts() {
        return accounts;
    }

    public Set<CustomerBankAccountDetails> getStorage() {
        return this.getAccounts();
    }

    public boolean addCustomerAccountDetails(CustomerBankAccountDetails customerBankAccountDetails) {
        return accounts.add(customerBankAccountDetails);
    }

    public Optional<CustomerBankAccountDetails> getCustomerDetailsByAccountNumber(int accountNumber) {
        return accounts.stream().filter(details -> details.getAccountNumber() == accountNumber).findFirst();
    }
}
