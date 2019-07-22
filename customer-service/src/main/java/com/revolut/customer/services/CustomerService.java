package com.revolut.customer.services;

import com.revolut.customer.domains.CustomerBankAccountDetails;
import com.revolut.customer.domains.requests.CustomerDetailsRequest;
import com.revolut.customer.domains.responses.CustomerDetailsResponse;
import com.revolut.customer.storages.AccountStorage;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

public class CustomerService {
    private final AccountStorage storage;

    public CustomerService(AccountStorage storage) {
        this.storage = storage;
    }

    public int createAccount(CustomerDetailsRequest customerDetailsRequest) {

        CustomerBankAccountDetails accountDetails = new CustomerBankAccountDetails(customerDetailsRequest, customerDetailsRequest.getUsername().hashCode());
        if (storage.addCustomerAccountDetails(accountDetails))
            return accountDetails.getAccountNumber();
        throw new BadRequestException();
    }

    public CustomerDetailsResponse getAccountDetails(int accountNumber) throws Throwable {
        return storage.getCustomerDetailsByAccountNumber(accountNumber).map(customerBankAccountDetails ->
                new CustomerDetailsResponse()
                        .withFirstName(customerBankAccountDetails.getCustomerPersonalDetails().getFirstName())
                        .withLastName(customerBankAccountDetails.getCustomerPersonalDetails().getLastName())
                        .withUserName(customerBankAccountDetails.getCustomerPersonalDetails().getUsername())
                        .withTotalBalance(customerBankAccountDetails.getTotalBalance())
        ).orElseThrow(() -> {
            throw new NotFoundException("Customer with account number " + accountNumber + "not found.");
        });
    }
}
