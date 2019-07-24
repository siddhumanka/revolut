package com.revolut.customer.clients;

import com.revolut.customer.configs.CustomerServiceFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

public class CustomerServiceClient {

    private final Client client;
    private final CustomerServiceFactory customerServiceFactory;

    public CustomerServiceClient(Client client, CustomerServiceFactory customerServiceFactory) {
        this.client = client;
        this.customerServiceFactory = customerServiceFactory;
    }

    public Response debitFromAccount(int amount, int payerAccountNumber) {
        String debitPath = customerServiceFactory.getBaseUrl() + customerServiceFactory.getDebitPath();
        return client.target(debitPath + "/" + payerAccountNumber + "/" + amount)
                .request()
                .get();
    }

    public void creditInAccount(int amount, int payeeAccountNumber) {
        String debitPath = customerServiceFactory.getBaseUrl() + customerServiceFactory.getCreditPath();
        client.target(debitPath + "/" + payeeAccountNumber + "/" + amount)
                .request()
                .get();
    }
}
