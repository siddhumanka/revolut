package com.revolut.customer.repositories;

import com.revolut.customer.domains.CustomerBankAccountDetails;

import java.util.Optional;

public interface Repository {

    boolean addCustomerAccountDetails(CustomerBankAccountDetails customerBankAccountDetails);

    Optional<CustomerBankAccountDetails> getCustomerDetailsByAccountNumber(int accountNumber);
}
