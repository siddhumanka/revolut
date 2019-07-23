package com.revolut.customer.services;

import com.revolut.customer.clients.CustomerServiceClient;
import com.revolut.customer.domains.requests.PaymentRequest;

public class PaymentService {

    private final CustomerServiceClient customerServiceClient;

    public PaymentService(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    public void payAmount(int amount, PaymentRequest request) {
        customerServiceClient.debitFromAccount(amount, request.getPayerAccountNumber());
        customerServiceClient.creditInAccount(amount, request.getPayeeAccountNumber());
    }
}
