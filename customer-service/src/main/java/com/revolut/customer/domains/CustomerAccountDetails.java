package com.revolut.customer.domains;

public class CustomerAccountDetails {

    private final CustomerDetails customerDetails;
    private final int accountNumber;

    public CustomerAccountDetails(CustomerDetails customerDetails, int accountNumber) {
        this.customerDetails = customerDetails;
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    @Override
    public boolean equals(Object o) {
        return accountNumber == ((CustomerAccountDetails) o).accountNumber;
    }

    @Override
    public int hashCode() {
        return accountNumber;
    }
}
