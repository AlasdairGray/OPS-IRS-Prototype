package uk.ac.manchester.cs;

import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
@Produces("text/plain")
interface IRS {

    /**
     * Retrieve all mappings with the given URI as the subject of the mapping triple. 
     * @param termURI
     * @return 
     */
    @GET
    @Path("/getMappingsWithSubject")
    public String getMappingsWithSubject(@QueryParam("uri") URI termURI);

    /**
     * Retrieve all mappings with the given URI as the subject of the mapping triple for a specified
     * context profile.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings
     * @return 
     */
    @GET
    @Path("/getMappingsWithSubject")
    public String getMappingsWithSubject(
            @QueryParam("uri") URI termURI,
            @QueryParam("profile") URI profile);

    /**
     * Retrieve all mappings with the given URI as the subject of the mapping triple for a specified
     * context profile. Limit the number of returned mappings.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings
     * @param limit maximum number of mappings to return
     * @return 
     */
    @GET
    @Path("/getMappingsWithSubject")
    public String getMappingsWithSubject(
            @QueryParam("uri") URI termURI,
            @QueryParam("profile") URI profile,
            @QueryParam("limit") int limit);

    /**
     * Retrieve all mappings with the given URI as the subject of the mapping triple. 
     * Limit the number of returned mappings.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param limit maximum number of mappings to return
     * @return 
     */
    @GET
    @Path("/getMappingsWithSubject")
    public String getMappingsWithSubject(
            @QueryParam("uri") URI termURI,
            @QueryParam("limit") int limit);

    /**
     * Retrieve all mappings with the given URI as the target of the mapping 
     * triple. 
     * @param termURI
     * @return 
     */
    @GET
    @Path("/getMappingsWithTarget")
    public String getMappingsWithTarget(@QueryParam("uri") URI termURI);

    /**
     * Retrieve all mappings with the given URI as the target of the mapping 
     * triple for a specified context profile.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings
     * @return 
     */
    @GET
    @Path("/getMappingsWithTarget")
    public String getMappingsWithTarget(
            @QueryParam("uri") URI termURI,
            @QueryParam("profile") URI profile);

    /**
     * Retrieve all mappings with the given URI as the target of the mapping 
     * triple for a specified context profile. 
     * Limit the number of returned mappings.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings
     * @param limit maximum number of mappings to return
     * @return 
     */
    @GET
    @Path("/getMappingsWithTarget")
    public String getMappingsWithTarget(
            @QueryParam("uri") URI termURI,
            @QueryParam("profile") URI profile,
            @QueryParam("limit") int limit);

    /**
     * Retrieve all mappings with the given URI as the target of the mapping 
     * triple. 
     * Limit the number of returned mappings.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param limit maximum number of mappings to return
     * @return 
     */
    @GET
    @Path("/getMappingsWithTarget")
    public String getMappingsWithTarget(
            @QueryParam("uri") URI termURI,
            @QueryParam("limit") int limit);
    
    /**
     * Retrieve the evidence for a specified mapping.
     * 
     * @param mappingURI URI that identifies a mapping
     * @return 
     */
    @GET
    @Path("/getMappingDetails")
    public String getMappingDetails(@QueryParam("mapping-uri") URI mappingURI);
    
}
