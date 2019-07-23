package com.revolut.customer.controllers;


import com.revolut.customer.domains.requests.PaymentRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/pay")
public class PaymentController {

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("/{amount}")
    public Response pay(@PathParam("amount") int amount, PaymentRequest paymentRequest) {
        return Response.noContent().build();
    }

}
