package com.revolut.customer.domains.requests;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequest {

    @JsonProperty(value = "payerAccountNumber", access = JsonProperty.Access.READ_WRITE)
    private int payerAccountNumber;

    @JsonProperty(value = "payeeAccountNumber", access = JsonProperty.Access.READ_WRITE)
    private int payeeAccountNumber;

}
