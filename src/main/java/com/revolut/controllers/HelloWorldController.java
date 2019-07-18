package com.revolut.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/hello-world")
public class HelloWorldController {

    @GET
    public Response helloWorld() {
        return Response.ok("helloWorld").build();
    }
}
