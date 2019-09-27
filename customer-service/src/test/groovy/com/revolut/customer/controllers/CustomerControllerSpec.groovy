package com.revolut.customer.controllers

import com.revolut.customer.Main
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.security.auth.login.Configuration
import javax.ws.rs.client.WebTarget

import static com.revolut.customer.helpers.HttpClientHelper.createAccount
import static com.revolut.customer.helpers.HttpClientHelper.getAccountDetails
import static com.revolut.customer.helpers.TargetHelper.createTarget
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath
import static javax.ws.rs.client.Entity.entity
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE
import static javax.ws.rs.core.Response.Status.NOT_FOUND
import static javax.ws.rs.core.Response.Status.OK

class CustomerControllerSpec extends Specification {

    @Shared
    @ClassRule
    DropwizardAppRule rule = new DropwizardAppRule<Configuration>(Main, resourceFilePath("test-config.yml"))

    @Shared
    WebTarget target

    def setup() {
        target = createTarget(rule)
    }


    def "POST /account should add a new customer to bank"() {
        given: "A customer details"

        def firstName = "Bat"
        def lastName = "man"
        def username = "batman"

        when: "Posting customer details"
        def response = createAccount(firstName, lastName, username, target)

        then: "should return a unique account number"
        def responseJson = response.readEntity(Integer)

        response.status == OK.statusCode
        responseJson != null
    }

    def "POST /account should return different account number for different customer"() {
        given: "Two customer with details"

        def firstName1 = "wonder"
        def lastName1 = "woman"
        def username1 = "wonderwoman"

        def firstName2 = "super"
        def lastName2 = "man"
        def username2 = "superman"

        when: "Posting 2 customer details"
        def response1 = createAccount(firstName1, lastName1, username1, target)
        def response2 = createAccount(firstName2, lastName2, username2, target)

        then: "account number for both should be different"
        def responseJson1 = response1.readEntity(Integer)
        def responseJson2 = response2.readEntity(Integer)

        response1.status == OK.statusCode
        response2.status == OK.statusCode
        responseJson1 != responseJson2
    }

    def "GET /account/{accountNumber} should return all customer details"() {
        given: "A customer with details and account number"

        def firstName = "aqua"
        def lastName = "man"
        def username = "aquaman"

        def response1 = createAccount(firstName, lastName, username, target)
        def accountNumber = response1.readEntity(Integer)

        when: "Requesting customer details"
        def response2 = getAccountDetails(accountNumber, target)

        then: "should return valid customer details"
        def responseJson2 = response2.readEntity(Map)

        response2.status == OK.statusCode
        responseJson2.firstName == firstName
        responseJson2.lastName == lastName
        responseJson2.username == username
        responseJson2.containsKey("subscription")
        responseJson2.subscription == null
    }

    def "GET /account/{accountNumber} should return 404 if customer not found"() {
        given: "A non existing customer with account number"

        when: "requesting details for the same"
        def response2 = getAccountDetails("32424235535", target)

        then: "not found should be returned"
        response2.status == NOT_FOUND.statusCode
    }


    def "POST /account/subscribe should subscribe a customer to an subscription account number"() {
        given: "An existing customer"
        def firstName = "super"
        def lastName = "boy"
        def username = "superboy"

        def response1 = createAccount(firstName, lastName, username, target)
        def accountNumber = response1.readEntity(Integer)

        println accountNumber
        def subscriptionFirstName = "subscription"
        def subscriptionLastName = "account"
        def subscriptionUsername = "subscriptionaccount"

        def subscriptionResponse1 = createAccount(subscriptionFirstName, subscriptionLastName, subscriptionUsername, target)
        def subscriptionAccountNumber = subscriptionResponse1.readEntity(Integer)

        when: "requesting subscription on an account"
        def response2 = target.path("/account/subscribe")
                .request(APPLICATION_JSON_TYPE)
                .post(entity([customerAccountNumber: accountNumber, subscriptionAccountNumber: subscriptionAccountNumber, amount: "200", time: "21:02"], APPLICATION_JSON_TYPE))

        then: "should return 200"
        response2.status == 200

        def response3 = getAccountDetails(accountNumber, target)
        def responseJson3 = response3.readEntity(Map)

        def subscriptionMap = responseJson3.subscription

        subscriptionMap != null
        subscriptionMap.accountNumber == subscriptionAccountNumber
    }
}
