package com.revolut.customer.helpers.builders

import com.revolut.customer.domains.responses.Subscription

class SubscriptionRequestBuilder {

    public static Subscription build(accountNumber = 987654321, time = "10:21", money = 200) {
        new Subscription(accountNumber: accountNumber, time: time, money: money)
    }
}
