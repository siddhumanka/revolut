package com.revolut.customer.controllers;

import com.revolut.customer.domains.CustomerDetails;
import com.revolut.customer.services.CustomerService;

import javax.ws.rs.*;
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

    @GET
    @Path("{accountNumber}")
    @Produces(APPLICATION_JSON)
    public Response getCustomerDetails(@PathParam("accountNumber") int accountNumber, CustomerDetails customerDetails) throws Throwable {
        CustomerDetails details = customerService.getAccountDetails(accountNumber);
        return Response.ok(details).build();
    }
}
