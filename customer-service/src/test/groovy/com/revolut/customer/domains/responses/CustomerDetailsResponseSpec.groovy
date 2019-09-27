package com.revolut.customer.domains.responses

import com.revolut.customer.helpers.builders.SubscriptionRequestBuilder
import spock.lang.Specification

class CustomerDetailsResponseSpec extends Specification {

    def "build() should create the valid object or default null or 0"() {
        given:
        def firstName = "firstName"
        def lastName = "lastName"
        def totalBalance = 19
        def subscription = SubscriptionRequestBuilder.build()

        when:
        def response = new CustomerDetailsResponse.CustomerDetailsResponseBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withTotalBalance(totalBalance)
                .withSubscription(subscription).build()

        then:
        response.totalBalance == totalBalance
        response.lastName == lastName
        response.firstName == firstName
        response.username == null
        response.subscription == subscription
    }
}
