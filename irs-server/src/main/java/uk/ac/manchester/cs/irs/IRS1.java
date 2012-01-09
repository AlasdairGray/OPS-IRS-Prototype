package uk.ac.manchester.cs.irs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Produces("text/plain")
interface IRS1 {

    @GET
    @Path("/hello1")
    public String hello1(@QueryParam("name") String name, @QueryParam("last") String last);
    
}
