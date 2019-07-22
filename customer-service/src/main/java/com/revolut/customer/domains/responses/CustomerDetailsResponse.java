package com.revolut.customer.domains.responses;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDetailsResponse {

    @JsonProperty(value = "firstName", access = JsonProperty.Access.READ_WRITE)
    private String firstName;

    @JsonProperty(value = "lastName", access = JsonProperty.Access.READ_WRITE)
    private String lastName;

    @JsonProperty(value = "username", access = JsonProperty.Access.READ_WRITE)
    private String username;

    @JsonProperty(value = "totalBalance", access = JsonProperty.Access.READ_WRITE)
    private int totalBalance;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalBalance() {
        return totalBalance;
    }

    public CustomerDetailsResponse withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerDetailsResponse withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerDetailsResponse withUserName(String username) {
        this.username = username;
        return this;
    }

    public CustomerDetailsResponse withTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
        return this;
    }

}
