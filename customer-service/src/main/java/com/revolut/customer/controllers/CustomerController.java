package com.revolut.customer.controllers;

import com.google.common.collect.Maps;
import com.revolut.customer.domains.CustomerDetails;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/customer")
public class CustomerController {

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response addCustomer(CustomerDetails customerDetails) {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("accountNumber", 1233456);
        return Response.ok(map).build();
    }
}
