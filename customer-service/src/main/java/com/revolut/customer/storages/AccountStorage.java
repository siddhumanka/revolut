package com.revolut.customer.storages;

import com.revolut.customer.domains.CustomerAccountDetails;

import java.util.HashSet;
import java.util.Set;

public class AccountStorage {
    private final Set<CustomerAccountDetails> accounts = new HashSet<>();

    public Set<CustomerAccountDetails> getAccounts() {
        return accounts;
    }

    public Set<CustomerAccountDetails> getStorage() {
        return this.getAccounts();
    }

    public boolean addCustomerAccountDetails(CustomerAccountDetails customerAccountDetails) {
        return accounts.add(customerAccountDetails);
    }
}
