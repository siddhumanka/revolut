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

    @JsonProperty(value = "subscription", access = JsonProperty.Access.READ_WRITE)
    //private Map<Integer, Subscription> subscriptionMap;
    private Subscription subscription;

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

    public Subscription getSubscription() {
        return subscription;
    }

    public static class CustomerDetailsResponseBuilder {
        private CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();

        public CustomerDetailsResponseBuilder withFirstName(String firstName) {
            this.customerDetailsResponse.firstName = firstName;
            return this;
        }

        public CustomerDetailsResponseBuilder withLastName(String lastName) {
            this.customerDetailsResponse.lastName = lastName;
            return this;
        }

        public CustomerDetailsResponseBuilder withUserName(String username) {
            this.customerDetailsResponse.username = username;
            return this;
        }

        public CustomerDetailsResponseBuilder withTotalBalance(int totalBalance) {
            this.customerDetailsResponse.totalBalance = totalBalance;
            return this;
        }

        public CustomerDetailsResponseBuilder withSubscription(Subscription subscription) {
            this.customerDetailsResponse.subscription = subscription;
            return this;
        }

        public CustomerDetailsResponse build() {
            return this.customerDetailsResponse;
        }
    }
}
