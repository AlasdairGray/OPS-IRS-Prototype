package uk.ac.manchester.cs.irs.resources;

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
import org.springframework.util.Assert;
import uk.ac.manchester.cs.irs.IRS;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.IRSImpl;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.beans.Match;

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
     * Retrieve all the matches for the URI specified in the path.
     * If no profile URI is specified then the configuration file value is assumed.
     * If the isSubject parameter is omitted, then it is assumed to be true.
     * If the isTarget parameter is omitted, then it is assumed to be true.
     * If no limit is specified then the configuration file value is used.
     * 
     * @param uri URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings
     * @param isSubject specifies if the URI should appear in the subject position of a mapping triple
     * @param isTarget specifies if the URI should appear in the target position of a mapping triple
     * @param limit maximum number of matches to return
     * @return 
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Path("/getMappings")
    public List<Match> getMappings(
            @QueryParam("uri") URI uri,
            @QueryParam("profile") URI profileUri,
            @QueryParam("issubject") Boolean isSubject, 
            @QueryParam("istarget") Boolean isTarget,
            @QueryParam("limit") Integer limit) 
            throws IRSException {
        Assert.notNull(irs);
        Assert.notNull(uri);
        Response response = null;
        List<Match> matches = null;
        if (isSubject == null) {
            isSubject = true;
        }
        if (isTarget == null) {
            isTarget = true;
        }
        if (isSubject && isTarget) {
            matches = irs.getMappingsWithURI(uri, profileUri, limit);
        } else if (isSubject) {
            matches = irs.getMappingsWithSubject(uri, profileUri, limit);
        } else if (isTarget) {
            matches = irs.getMappingsWithTarget(uri, profileUri, limit);
        } else {
            final String msg = "Illegal state. Cannot set both subject and target to false.";
            Logger.getLogger(MappingResource.class.getName()).log(Level.SEVERE, msg);
            throw new IRSException(msg);
        }
        return matches;
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
    public Mapping getMappingDetails(
            @PathParam("id") Integer mappingId) 
            throws IRSException 
    {
        Assert.notNull(irs);
        Assert.notNull(mappingId);
        Mapping mapping = irs.getMappingDetails(mappingId);
        return mapping;
    }

}
