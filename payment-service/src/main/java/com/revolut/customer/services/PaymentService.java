package com.revolut.customer.services;

import com.revolut.customer.clients.CustomerServiceClient;
import com.revolut.customer.domains.requests.PaymentRequest;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;

public class PaymentService {

    private final CustomerServiceClient customerServiceClient;

    public PaymentService(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    public void payAmount(int amount, PaymentRequest request) {
        Response debitResponse = customerServiceClient.debitFromAccount(amount, request.getPayerAccountNumber());
        if (debitResponse.getStatus() == 403) {
            throw new ForbiddenException();
        }
        customerServiceClient.creditInAccount(amount, request.getPayeeAccountNumber());
    }
}
