package com.revolut.customer.controllers;

import com.revolut.customer.services.TransactionService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GET
    @Path("credit/{accountNumber}/{amount}")
    @Produces(APPLICATION_JSON)
    public Response creditAmount(@PathParam("accountNumber") int accountNumber, @PathParam("amount") int amount) {
        transactionService.creditAmountInAccount(amount, accountNumber);
        return Response.ok().build();
    }

}
