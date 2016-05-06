package com.gbk;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Author: jonny.
 */

@Path("hello")
public class HelloWorldResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes("text/plain")
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
