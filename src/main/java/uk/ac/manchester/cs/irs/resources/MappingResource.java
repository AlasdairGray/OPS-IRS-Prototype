package uk.ac.manchester.cs.irs.resources;

import java.net.URISyntaxException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import uk.ac.manchester.cs.irs.IRS;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.IRSImpl;
import uk.ac.manchester.cs.irs.beans.Mapping;

@Path("/")
public class MappingResource {
    private static IRS irs = null;

    public MappingResource() {

    }
    
    /**
     * Constructor used to enable mocking for unit testing
     * 
     * @param IRS instance of the IRS to use 
     */
    protected MappingResource(IRS irs) {
        this.irs = irs;
    }

    @Context 
    private UriInfo uriInfo;

    static {
        try {
            irs = new IRSImpl();
        } catch (IRSException ex) {
            String msg = "Cannot initialise IRS service";
            Logger.getLogger(MappingResource.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }
            
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
        if (irs == null) {
            return Response.serverError().build();
        }
        Response response = null;
        if (uri == null) {
            //TODO: Verify this is the correct response code
            response = Response.status(400).build();
        } else {
            if (isSubject == null) {
                isSubject = true;
            }
            if (isTarget == null) {
                isTarget = true;
            }
            List<Mapping> mappings;
            try {
                if (isSubject && isTarget) {
                    mappings = irs.getMappingsWithURI(uri, profileUri, limit);
                } else if (isSubject) {
                    mappings = irs.getMappingsWithSubject(uri, profileUri, limit);
                } else {
                    mappings = irs.getMappingsWithTarget(uri, profileUri, limit);
                }
                response = Response.ok(mappings).build();
            } catch (IRSException ex) {
                String msg = "Problem retrieving mappings with uri " + uri;
                Logger.getLogger(MappingResource.class.getName()).log(Level.SEVERE, msg, ex);
                //TODO: Verify this is the correct response code
                response = Response.status(404).build();
            }
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
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getMappingDetails(
            @PathParam("id") Integer mappingId) 
    {
        if (irs == null) {
            return Response.serverError().build();
        }
        Response response;
        Mapping mapping;
        if (mappingId == null) {
            //TODO: Verify this is the correct response code
            response = Response.status(400).build(); 
        } else {
            try {
                mapping = irs.getMappingDetails(mappingId);
                response = Response.ok(mapping).build();
            } catch (IRSException ex) {
                String msg = "Problem retrieving mapping with id " + mappingId;
                Logger.getLogger(MappingResource.class.getName()).log(Level.SEVERE, msg, ex);
            //TODO: Verify this is the correct response code
                response = Response.status(404).build();
            }
        }
        return response;
    }

}
