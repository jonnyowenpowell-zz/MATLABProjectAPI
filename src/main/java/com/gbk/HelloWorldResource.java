package com.gbk;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Author: jonny.
 */

@Path("hello")
public class HelloWorldResource {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHelloWorld() {
        return "Hello buddy!";
    }

}
