package com.revolut.customer;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.revolut.customer.controllers.CustomerController;
import com.revolut.customer.controllers.HelloWorldController;
import com.revolut.customer.services.CustomerService;
import com.revolut.customer.storages.AccountStorage;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application<Configuration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.getObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        super.initialize(bootstrap);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        LOGGER.info("Registering REST resources");
        AccountStorage storage = new AccountStorage();
        CustomerService customerService = new CustomerService(storage);
        environment.jersey().register(customerService);
        environment.jersey().register(new CustomerController(customerService));
        environment.jersey().register(new HelloWorldController());
    }
}
