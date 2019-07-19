package com.revolut.customer.controllers

import com.revolut.customer.Main
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.security.auth.login.Configuration
import javax.ws.rs.client.WebTarget

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath
import static io.dropwizard.util.Duration.seconds
import static java.util.UUID.randomUUID
import static javax.ws.rs.client.Entity.entity
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE
import static javax.ws.rs.core.Response.Status.OK

class CustomerControllerSpec extends Specification {

    @Shared
    @ClassRule
    DropwizardAppRule rule = new DropwizardAppRule<Configuration>(Main, resourceFilePath("test-config.yml"))

    @Shared
    WebTarget target


    static WebTarget createTarget(DropwizardAppRule rule) {
        return createTarget(rule, new JerseyClientConfiguration())
    }

    static WebTarget createTarget(DropwizardAppRule rule, JerseyClientConfiguration clientConfig, Class<?>... providerClasses) {
        def builder = new JerseyClientBuilder(rule.getEnvironment()).using(clientConfig)
        providerClasses.each { builder.withProvider(it)}

        def client = builder.build(randomUUID().toString())
        return client.target("http://localhost:${rule.localPort}")
    }

    def setupSpec() {
        target = createTarget(rule)
    }


    def "POST /customer should add a customer to bank"() {
        given: "A customer details"

        def firstName = "Bat"
        def lastName = "man"
        def username = "batman"

        when: "Posting customer details"
        def response = target.path("/customer")
                .request(APPLICATION_JSON_TYPE)
                .post(entity("""{
                        "firstName" : "${firstName}", 
                        "lastName": "${lastName}", 
                        "username": "${username}"
                 }""", APPLICATION_JSON_TYPE))

        then: "should return a unique account number"
        def responseJson = response.readEntity(Map)

        response.status == OK.statusCode
        responseJson.accountNumber != null
    }
}
