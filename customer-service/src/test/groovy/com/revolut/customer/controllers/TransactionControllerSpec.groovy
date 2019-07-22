package com.revolut.customer.controllers

import com.revolut.customer.Main
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.security.auth.login.Configuration
import javax.ws.rs.client.WebTarget

import static com.revolut.customer.helpers.HttpClientHelper.*
import static com.revolut.customer.helpers.TargetHelper.createTarget
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE
import static javax.ws.rs.core.Response.Status.FORBIDDEN
import static javax.ws.rs.core.Response.Status.NOT_FOUND
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

    def "GET /credit should add money into customer's account"() {
        given: "For a customer"
        def firstName = "bat"
        def lastName = "man"
        def username = "batman"
        def accountNumber = createAccount(firstName, lastName, username, target).readEntity(Integer)
        def amount = "294"

        when: "when crediting a certain amount of money"
        def response = creditMoneyInAccount(accountNumber, amount, target)

        then: "should reflect in bank account"
        response.status == OK.statusCode

        def accountDetails = getAccountDetails(accountNumber, target).readEntity(Map)
        accountDetails.totalBalance == 294
    }

    def "GET /credit should add money into customer's account balance if already in balance"() {
        given: "For a customer"
        def firstName = "super"
        def lastName = "man"
        def username = "superman"
        def accountNumber = createAccount(firstName, lastName, username, target).readEntity(Integer)
        def amount1 = "294"
        def amount2 = "1000"

        def response1 = creditMoneyInAccount(accountNumber, amount1, target)

        when: "when crediting a certain amount of money"
        def response2 = creditMoneyInAccount(accountNumber, amount2, target)

        then: "should reflect total sum in bank account"
        response1.status == OK.statusCode
        response2.status == OK.statusCode

        def accountDetails = getAccountDetails(accountNumber, target).readEntity(Map)
        accountDetails.totalBalance == 1294
    }

    def "GET /debit should debit money from customer's account balance"() {
        given: "For a customer with 1000"
        def firstName = "wonder"
        def lastName = "woman"
        def username = "wonderwoman"
        def accountNumber = createAccount(firstName, lastName, username, target).readEntity(Integer)
        def amountToCredit = "1000"
        def amountToDebit = "500"

        def response1 = creditMoneyInAccount(accountNumber, amountToCredit, target)

        when: "when debiting 500"
        def response2 = target.path("/debit/${accountNumber}/${amountToDebit}")
                .request(APPLICATION_JSON_TYPE)
                .get()

        then: "should reflect total balance as 500"
        response1.status == OK.statusCode
        response2.status == OK.statusCode

        def accountDetails = getAccountDetails(accountNumber, target).readEntity(Map)
        accountDetails.totalBalance == 500
    }

    def "GET /debit should fail with 403 if user don't have enough money to debit"() {
        given: "For a customer with 1000"
        def firstName = "aqua"
        def lastName = "man"
        def username = "aquaman"
        def accountNumber = createAccount(firstName, lastName, username, target).readEntity(Integer)
        def amountToCredit = "1000"
        def amountToDebit = "5000"

        creditMoneyInAccount(accountNumber, amountToCredit, target)

        when: "when debiting 5000"
        def response2 = target.path("/debit/${accountNumber}/${amountToDebit}")
                .request(APPLICATION_JSON_TYPE)
                .get()

        then: "should fail with forbidden"
        response2.status == FORBIDDEN.statusCode
    }

    def "GET /credit should fail with 404 if accountNumber is invalid"() {
        given: "For invalid account number"
        when: "crediting money"
        def response2 = target.path("/credit/12312333/200")
                .request(APPLICATION_JSON_TYPE)
                .get()

        then: "should fail with not found"
        response2.status == NOT_FOUND.statusCode
    }

    def "GET /debit should fail with 404 if accountNumber is invalid"() {
        given: "For invalid account number"
        when: "debiting money"
        def response2 = target.path("/debit/12312333/200")
                .request(APPLICATION_JSON_TYPE)
                .get()

        then: "should fail with not found"
        response2.status == NOT_FOUND.statusCode
    }

}
