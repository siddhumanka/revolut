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
    }

    def "GET /account/{accountNumber} should return 404 if customer not found"() {
        given: "A non existing customer with account number"

        when: "requesting details for the same"
        def response2 = getAccountDetails("32424235535", target)

        then: "not found should be returned"
        response2.status == NOT_FOUND.statusCode
    }

}
