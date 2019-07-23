package com.revolut.customer.controllers

import com.revolut.customer.Main
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.security.auth.login.Configuration
import javax.ws.rs.client.WebTarget

import static com.revolut.customer.helpers.TargetHelper.createTarget
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath
import static javax.ws.rs.client.Entity.entity
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE
import static javax.ws.rs.core.Response.Status.NO_CONTENT

class PaymentControllerSpec extends Specification {

    @Shared
    @ClassRule
    DropwizardAppRule rule = new DropwizardAppRule<Configuration>(Main, resourceFilePath("test-config.yml"))

    @Shared
    WebTarget target

    def setup() {
        target = createTarget(rule)
    }

    def "POST /pay should transfer money into given bank account"() {
        given: "A payer and a payee account number"
        def payerAccountNumber = 123
        def payeeAccountNumber = 124
        def amount = 500

        when: "paying the money"
        def response = target.path("/pay/${amount}")
                .request(APPLICATION_JSON_TYPE)
                .post(entity([payer: "${payerAccountNumber}", payee: "${payeeAccountNumber}"], APPLICATION_JSON_TYPE))


        then: "should return 204"
        response.status == NO_CONTENT.statusCode

    }
}
