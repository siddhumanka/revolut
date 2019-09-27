package com.revolut.customer.domains.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionRequest {

    @JsonProperty(value = "customerAccountNumber", access = JsonProperty.Access.READ_WRITE)
    private Integer customerAccountNumber;

    @JsonProperty(value = "subscriptionAccountNumber", access = JsonProperty.Access.READ_WRITE)
    private Integer subscriptionAccountNumber;

    @JsonProperty(value = "amount", access = JsonProperty.Access.READ_WRITE)
    private int amount;

    @JsonProperty(value = "time", access = JsonProperty.Access.READ_WRITE)
    private String time;

    public void setCustomerAccountNumber(Integer customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    public void setSubscriptionAccountNumber(Integer subscriptionAccountNumber) {
        this.subscriptionAccountNumber = subscriptionAccountNumber;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public Integer getSubscriptionAccountNumber() {
        return subscriptionAccountNumber;
    }

    public int getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }


}
