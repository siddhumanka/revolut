package com.revolut.customer.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PaymentServiceConfiguration extends Configuration {
    @Valid
    @NotNull
    private CustomerServiceFactory customerServiceFactory = new CustomerServiceFactory();

    @JsonProperty("customerService")
    public CustomerServiceFactory getCustomerServiceFactory() {
        return customerServiceFactory;
    }

}