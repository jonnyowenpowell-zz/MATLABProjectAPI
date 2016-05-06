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
    @Consumes(MediaType.TEXT_PLAIN)
    public String sayHello(String name) {
        if (name != null) {
            return "Hello " + name;
        } else {
            return "Hello buddy";
        }
    }

}
