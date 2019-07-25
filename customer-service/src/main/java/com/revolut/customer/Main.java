package com.revolut.customer;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.revolut.customer.controllers.CustomerController;
import com.revolut.customer.controllers.HelloWorldController;
import com.revolut.customer.controllers.TransactionController;
import com.revolut.customer.domains.CustomerBankAccountDetails;
import com.revolut.customer.services.CustomerService;
import com.revolut.customer.services.TransactionService;
import com.revolut.customer.repositories.AccountStorageRepository;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

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
        Set<CustomerBankAccountDetails> inMemoryDb = new HashSet<>();
        AccountStorageRepository storage = new AccountStorageRepository(inMemoryDb);
        CustomerService customerService = new CustomerService(storage);
        TransactionService transactionService = new TransactionService(storage);

        environment.jersey().register(customerService);
        environment.jersey().register(transactionService);
        environment.jersey().register(new CustomerController(customerService));
        environment.jersey().register(new TransactionController(transactionService));
        environment.jersey().register(new HelloWorldController());
    }
}
