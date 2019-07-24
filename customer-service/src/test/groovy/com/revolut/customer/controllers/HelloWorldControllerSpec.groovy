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
import static javax.ws.rs.core.Response.Status.OK

class HelloWorldControllerSpec extends Specification {


    @Shared
    @ClassRule
    DropwizardAppRule rule = new DropwizardAppRule<Configuration>(Main, resourceFilePath("test-config.yml"))

    @Shared
    WebTarget target


    def setupSpec() {
        target = createTarget(rule)
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

