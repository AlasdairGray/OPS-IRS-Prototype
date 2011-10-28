package uk.ac.manchester.cs.irs.resources;

import java.net.URISyntaxException;
import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mappings")
public class MappingResource {

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
    //Order of declaration gives precidence
    @Produces(MediaType.TEXT_HTML)
    public Response getMappings(
            @QueryParam("uri") URI uri,
            @QueryParam("profile") URI profileUri,
            @QueryParam("issubject") Boolean isSubject, 
            @QueryParam("istarget") Boolean isTarget,
            @QueryParam("limit") Integer limit) 
            throws URISyntaxException {
        if (isSubject == null) {
            isSubject = true;
        }
        if (isTarget == null) {
            isTarget = false;
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
        return Response.ok(output.toString(), MediaType.TEXT_HTML).build();
    }
    
    /**
     * Retrieve the evidence for a specified mapping.
     * 
     * @param mappingURI URI that identifies a mapping
     * @param profile URI of a profile that was used to supply the mapping
     * @return a representation of the information to support a mapping
     */
    @GET
    @Path("/getMappingDetails")
    @Produces(MediaType.TEXT_HTML)
    public Response getMappingDetails(
            @QueryParam("mapping-uri") URI mappingURI,
            @QueryParam("profile") URI profileUri) 
            throws URISyntaxException {
        if (profileUri == null) {
            //Assume general profile is used
            profileUri = new URI("http://irs.openphacts.eu/default");
        }
        StringBuilder output = new StringBuilder();
        output.append("Mapping URI: ").append(mappingURI.toASCIIString()).append("<br/>");
        output.append("Profile URI: ").append(profileUri.toASCIIString());
        return Response.ok(output.toString(), MediaType.TEXT_HTML).build();        
    }

}
