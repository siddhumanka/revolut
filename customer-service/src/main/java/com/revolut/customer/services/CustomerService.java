package com.revolut.customer.services;

import com.revolut.customer.domains.CustomerAccountDetails;
import com.revolut.customer.domains.CustomerDetails;
import com.revolut.customer.storages.AccountStorage;

public class CustomerService {
    private final AccountStorage storage;

    public CustomerService(AccountStorage storage) {
        this.storage = storage;
    }

    public int createAccount(CustomerDetails customerDetails) {

        CustomerAccountDetails accountDetails = new CustomerAccountDetails(customerDetails, customerDetails.getUsername().hashCode());
        if (storage.addCustomerAccountDetails(accountDetails))
            return accountDetails.getAccountNumber();
        throw new RuntimeException();
    }

}
