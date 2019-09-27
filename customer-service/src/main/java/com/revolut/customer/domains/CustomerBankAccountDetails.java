package com.revolut.customer.domains;

import com.revolut.customer.domains.requests.CustomerDetailsRequest;
import com.revolut.customer.domains.responses.Subscription;

public class CustomerBankAccountDetails {

    private final CustomerDetailsRequest customerPersonalDetails;
    private final int accountNumber;
    private int totalBalance;
    private Subscription subscription;

    public CustomerBankAccountDetails(CustomerDetailsRequest customerPersonalDetails, int accountNumber) {
        this.customerPersonalDetails = customerPersonalDetails;
        this.accountNumber = accountNumber;
        this.totalBalance = 0;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
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

    public Subscription getSubscription() {
        return subscription;
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
