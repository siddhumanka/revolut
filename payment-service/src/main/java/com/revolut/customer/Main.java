package com.revolut.customer;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.revolut.customer.clients.CustomerServiceClient;
import com.revolut.customer.configs.PaymentServiceConfiguration;
import com.revolut.customer.controllers.HelloWorldController;
import com.revolut.customer.controllers.PaymentController;
import com.revolut.customer.services.PaymentService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

public class Main extends Application<PaymentServiceConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    @Override
    public void initialize(Bootstrap<PaymentServiceConfiguration> bootstrap) {
        bootstrap.getObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        super.initialize(bootstrap);
    }

    @Override
    public void run(PaymentServiceConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Registering REST resources");
        Client client = new JerseyClientBuilder().build();
        CustomerServiceClient customerServiceClient = new CustomerServiceClient(client, configuration.getCustomerServiceFactory());
        PaymentService paymentService = new PaymentService(customerServiceClient);
        environment.jersey().register(new HelloWorldController());
        environment.jersey().register(new PaymentController(paymentService));
    }
}
