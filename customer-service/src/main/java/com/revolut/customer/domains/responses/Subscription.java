package com.revolut.customer.domains.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subscription {
    @JsonProperty(value = "accountNumber", access = JsonProperty.Access.READ_WRITE)
    private int accountNumber;

    @JsonProperty(value = "time", access = JsonProperty.Access.READ_WRITE)
    private String time;

    @JsonProperty(value = "money", access = JsonProperty.Access.READ_WRITE)
    private int money;

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public static class SubscriptionBuilder {
        private Subscription subscription = new Subscription();

        public SubscriptionBuilder withAccountNumber(int accountNumber) {
            this.subscription.accountNumber = accountNumber;
            return this;
        }

        public SubscriptionBuilder withMoney(int money) {
            this.subscription.money = money;
            return this;
        }

        public SubscriptionBuilder withTime(String time) {
            this.subscription.time = time;
            return this;
        }

        public Subscription build() {
            return this.subscription;
        }

    }
}
