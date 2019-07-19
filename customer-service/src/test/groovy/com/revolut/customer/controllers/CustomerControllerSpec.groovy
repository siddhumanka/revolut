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


    def "POST /customer should add a new customer to bank"() {
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

    def "POST /customer should return different account number for different customer"() {
        given: "A customer details"

        def firstName1 = "Bat"
        def lastName1 = "man"
        def username1 = "batman"

        def firstName2 = "super"
        def lastName2 = "man"
        def username2 = "superman"

        when: "Posting 2 customer details"
        def response1 = target.path("/customer")
                .request(APPLICATION_JSON_TYPE)
                .post(entity("""{
                        "firstName" : "${firstName1}", 
                        "lastName": "${lastName1}", 
                        "username": "${username1}"
                 }""", APPLICATION_JSON_TYPE))

        def response2 = target.path("/customer")
                .request(APPLICATION_JSON_TYPE)
                .post(entity("""{
                        "firstName" : "${firstName2}", 
                        "lastName": "${lastName2}", 
                        "username": "${username2}"
                 }""", APPLICATION_JSON_TYPE))

        then: "account number for both should be different"
        def responseJson1 = response1.readEntity(Map)
        def responseJson2 = response2.readEntity(Map)

        response1.status == OK.statusCode
        response2.status == OK.statusCode
        responseJson1.accountNumber != responseJson2.accountNumber
    }
}
