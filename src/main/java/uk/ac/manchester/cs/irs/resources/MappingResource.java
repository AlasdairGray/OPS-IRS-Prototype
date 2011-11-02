package uk.ac.manchester.cs.irs.resources;

import java.net.URISyntaxException;
import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class MappingResource {

    @Context 
    private UriInfo uriInfo;
            
    /**
     * Retrieve all the mappings for the URI specified in the path.
     * If no profile URI is specified then the configuration file value is assumed.
     * If the isSubject parameter is omitted, then it is assumed to be true.
     * If the isTarget parameter is omitted, then it is assumed to be false.
     * If no limit is specified then the configuration file value is used.
     * 
     * @param uri URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings
     * @param isSubject specifies if the URI should appear in the subject position of a mapping triple
     * @param isTarget specifies if the URI should appear in the target position of a mapping triple
     * @param limit maximum number of mappings to return
     * @return 
     */
    @GET
    @Path("/getMappings")
    //Order of declaration gives precidence
    @Produces(MediaType.TEXT_HTML)
    public Response getMappings(
            @QueryParam("uri") URI uri,
            @QueryParam("profile") URI profileUri,
            @QueryParam("issubject") Boolean isSubject, 
            @QueryParam("istarget") Boolean isTarget,
            @QueryParam("limit") Integer limit) 
            throws URISyntaxException {
        Response response;
        if (uri == null) {
            response = Response.status(400).build();
        } else {
            if (isSubject == null) {
                isSubject = true;
            }
            if (isTarget == null) {
                isTarget = true;
            }
            if (limit == null) {
                limit = 10;
            }
            if (profileUri == null) {
                //Assume general profile is used
                profileUri = new URI("http://irs.openphacts.eu/default");
            }
        
            StringBuilder output = new StringBuilder();
            output.append("Term URI: ").append(uri.toASCIIString()).append("<br/>");
            output.append("Profile URI: ").append(profileUri.toASCIIString()).append("<br/>");
            output.append("isSubject: ").append(isSubject).append("<br/>");
            output.append("isTarget: ").append(isTarget).append("<br/>");
            output.append("Limit: ").append(limit);
            response = Response.ok(output.toString(), MediaType.TEXT_HTML).build();
        }
        return response;
    }
    
    /**
     * Retrieve the evidence for a specified mapping.
     * 
     * @param mappingURI URI that identifies a mapping
     * @param profile URI of a profile that was used to supply the mapping
     * @return a representation of the information to support a mapping
     */
    @GET
    @Path("/mapping/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response getMappingDetails(
            @PathParam("id") Integer mappingId) 
    {
        Response response;
        if (mappingId == null) {
            response = Response.status(400).build(); 
        } else {
            StringBuilder output = new StringBuilder();
            output.append("Mapping ID: ").append(mappingId).append("<br/>");
            response = Response.ok(output.toString(), MediaType.TEXT_HTML).build();
        }
        return response;
    }

}
