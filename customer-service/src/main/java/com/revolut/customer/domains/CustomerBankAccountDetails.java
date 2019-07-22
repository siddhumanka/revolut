package com.revolut.customer.domains;

import com.revolut.customer.domains.requests.CustomerDetailsRequest;

public class CustomerBankAccountDetails {

    private final CustomerDetailsRequest customerPersonalDetails;
    private final int accountNumber;
    private int totalBalance;

    public CustomerBankAccountDetails(CustomerDetailsRequest customerPersonalDetails, int accountNumber) {
        this.customerPersonalDetails = customerPersonalDetails;
        this.accountNumber = accountNumber;
        this.totalBalance = 0;
    }

    public int getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public CustomerDetailsRequest getCustomerPersonalDetails() {
        return customerPersonalDetails;
    }

    @Override
    public boolean equals(Object o) {
        return accountNumber == ((CustomerBankAccountDetails) o).accountNumber;
    }

    @Override
    public int hashCode() {
        return accountNumber;
    }
}
