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
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE
import static javax.ws.rs.core.Response.Status.OK

class TransactionControllerSpec extends Specification {

    @Shared
    @ClassRule
    DropwizardAppRule rule = new DropwizardAppRule<Configuration>(Main, resourceFilePath("test-config.yml"))

    @Shared
    WebTarget target

    def setup() {
        target = createTarget(rule)
    }

    def "GET /credit should add money into customer account"() {
        given: "For a customer"
        def firstName = "bat"
        def lastName = "man"
        def username = "batman"
        def accountNumber = createAccount(firstName, lastName, username, target).readEntity(Integer)
        def amount = "294"

        when: "when crediting a certain amount of money"
        def response = target.path("/credit/${accountNumber}/${amount}")
                .request(APPLICATION_JSON_TYPE)
                .get()

        then: "should reflect in bank account"
        response.status == OK.statusCode

        def accountDetails = getAccountDetails(accountNumber, target).readEntity(Map)
        accountDetails.totalBalance == 294
    }

    def "GET /credit should add money into customer account balance if already in balance"() {
        given: "For a customer"
        def firstName = "super"
        def lastName = "man"
        def username = "superman"
        def accountNumber = createAccount(firstName, lastName, username, target).readEntity(Integer)
        def amount1 = "294"
        def amount2 = "1000"

        def response1 = target.path("/credit/${accountNumber}/${amount1}")
                .request(APPLICATION_JSON_TYPE)
                .get()

        when: "when crediting a certain amount of money"
        def response2 = target.path("/credit/${accountNumber}/${amount2}")
                .request(APPLICATION_JSON_TYPE)
                .get()

        then: "should reflect total sum in bank account"
        response1.status == OK.statusCode
        response2.status == OK.statusCode

        def accountDetails = getAccountDetails(accountNumber, target).readEntity(Map)
        accountDetails.totalBalance == 1294
    }
}
