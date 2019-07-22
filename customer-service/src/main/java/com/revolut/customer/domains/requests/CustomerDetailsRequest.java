package com.revolut.customer.domains.requests;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDetailsRequest {

    @JsonProperty(value = "firstName", access = JsonProperty.Access.READ_WRITE)
    private String firstName;

    @JsonProperty(value = "lastName", access = JsonProperty.Access.READ_WRITE)
    private String lastName;

    @JsonProperty(value = "username", access = JsonProperty.Access.READ_WRITE)
    private String username;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
