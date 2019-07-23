package com.revolut.customer.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerServiceFactory {

    @JsonProperty
    private String baseUrl;

    @JsonProperty
    private String debitPath;

    @JsonProperty
    private String creditPath;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getDebitPath() {
        return debitPath;
    }

    public String getCreditPath() {
        return creditPath;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setDebitPath(String debitPath) {
        this.debitPath = debitPath;
    }

    public void setCreditPath(String creditPath) {
        this.creditPath = creditPath;
    }
}
