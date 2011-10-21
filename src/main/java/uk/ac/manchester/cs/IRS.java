package uk.ac.manchester.cs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
@Produces("text/plain")
interface IRS {

    @GET
    @Path("/hello")
    public String hello(@QueryParam("name") String name);
    
}
