package com.revolut.customer.services;

import com.revolut.customer.domains.CustomerBankAccountDetails;
import com.revolut.customer.domains.requests.CustomerDetailsRequest;
import com.revolut.customer.domains.responses.CustomerDetailsResponse;
import com.revolut.customer.repositories.Repository;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

public class CustomerService {
    private final Repository repository;

    public CustomerService(Repository repository) {
        this.repository = repository;
    }

    public int createAccount(CustomerDetailsRequest customerDetailsRequest) {

        CustomerBankAccountDetails accountDetails = new CustomerBankAccountDetails(customerDetailsRequest, customerDetailsRequest.getUsername().hashCode());
        if (repository.addCustomerAccountDetails(accountDetails))
            return accountDetails.getAccountNumber();
        throw new BadRequestException();
    }

    public CustomerDetailsResponse getAccountDetails(int accountNumber) throws Throwable {
        return repository.getCustomerDetailsByAccountNumber(accountNumber)
                .map(this::mapAccountDetailsToResponse)
                .orElseThrow(() -> {
                    throw new NotFoundException("Customer with account number " + accountNumber + "not found.");
                });
    }

    private CustomerDetailsResponse mapAccountDetailsToResponse(CustomerBankAccountDetails customerBankAccountDetails) {
        return new CustomerDetailsResponse.CustomerDetailsResponseBuilder()
                .withFirstName(customerBankAccountDetails.getCustomerPersonalDetails().getFirstName())
                .withLastName(customerBankAccountDetails.getCustomerPersonalDetails().getLastName())
                .withUserName(customerBankAccountDetails.getCustomerPersonalDetails().getUsername())
                .withTotalBalance(customerBankAccountDetails.getTotalBalance()).build();
    }
}
