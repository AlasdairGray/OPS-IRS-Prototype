package uk.ac.manchester.cs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@Produces("text/plain")
interface IRS {

    @GET
    @Path("/helloWorld")
    public String helloWorld();
    
}
