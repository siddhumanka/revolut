package com.revolut.customer.controllers;

import com.revolut.customer.domains.CustomerDetails;
import com.revolut.customer.services.CustomerService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/account")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response addCustomer(CustomerDetails customerDetails) {
        int accountNumber = customerService.createAccount(customerDetails);
        return Response.ok(accountNumber).build();
    }
}
