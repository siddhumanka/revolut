package com.revolut

import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.security.auth.login.Configuration
import javax.ws.rs.client.WebTarget

import static java.util.UUID.randomUUID
import static javax.ws.rs.core.Response.Status.OK

class HelloWorldSpec extends Specification {

    @Shared
    @ClassRule
    DropwizardAppRule rule = new DropwizardAppRule<Configuration>(Main)

    @Shared
    WebTarget target


    def setupSpec() {
        def builder = new JerseyClientBuilder(rule.getEnvironment()).using(new JerseyClientConfiguration())
        def client = builder.build(randomUUID().toString())
        target = client.target("http://localhost:${rule.localPort}")
    }

    def "GET /hello-world should return hello world as string"() {
        when: "Calling hello-world endpoint"
        def response = target.path("/hello-world")
                .request()
                .get()

        then: "should return helloWorld"
        def responseString = response.readEntity(String)

        response.status == OK.statusCode
        responseString == "helloWorld"
    }
}
