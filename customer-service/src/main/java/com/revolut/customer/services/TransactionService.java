package com.revolut.customer.services;


import com.revolut.customer.domains.CustomerBankAccountDetails;
import com.revolut.customer.repositories.Repository;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

public class TransactionService {
    private final Repository repository;

    public TransactionService(Repository repository) {
        this.repository = repository;
    }

    public void creditAmountInAccount(int amount, int accountNumber) {
        if (repository.getCustomerDetailsByAccountNumber(accountNumber).isPresent()) {
            CustomerBankAccountDetails details = repository.getCustomerDetailsByAccountNumber(accountNumber).get();
            int totalBalance = details.getTotalBalance();
            details.setTotalBalance(totalBalance + amount);
        } else
            throw new NotFoundException("Customer with account number " + accountNumber + " not found.");
    }

    public void debitAmountFromAccount(int amount, int accountNumber) {
        if (repository.getCustomerDetailsByAccountNumber(accountNumber).isPresent()) {
            CustomerBankAccountDetails details = repository.getCustomerDetailsByAccountNumber(accountNumber).get();
            int totalBalance = details.getTotalBalance();
            if ((totalBalance - amount) < 0) {
                throw new ForbiddenException("Not enough balance please credit at least " + (amount - totalBalance) + " amount of money.");
            } else details.setTotalBalance(totalBalance - amount);
        } else
            throw new NotFoundException("Customer with account number " + accountNumber + " not found.");
    }
}
