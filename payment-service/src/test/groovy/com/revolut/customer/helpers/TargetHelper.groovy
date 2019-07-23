package com.revolut.customer.helpers

import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.testing.junit.DropwizardAppRule

import javax.ws.rs.client.WebTarget

import static java.util.UUID.randomUUID

class TargetHelper {

    static WebTarget createTarget(DropwizardAppRule rule) {
        return createTarget(rule, new JerseyClientConfiguration())
    }

    static WebTarget createTarget(DropwizardAppRule rule, JerseyClientConfiguration clientConfig, Class<?>... providerClasses) {
        def builder = new JerseyClientBuilder(rule.getEnvironment()).using(clientConfig)
        providerClasses.each { builder.withProvider(it) }

        def client = builder.build(randomUUID().toString())
        return client.target("http://localhost:${rule.localPort}")
    }

}
