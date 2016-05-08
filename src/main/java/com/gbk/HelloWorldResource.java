package com.gbk;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Author: jonny.
 */

@Path("hello")
public class HelloWorldResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHelloWorld() {
        return "Hello buddy!";
    }

}
