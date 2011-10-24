package uk.ac.manchester.cs.irs;

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
    public String getMappingsWithSubject(@QueryParam("uri") URI termURI)
            throws IRSException;

    /**
     * Retrieve all mappings with the given URI as the subject of the mapping triple for a specified
     * context profile.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings
     * @return 
     */
    public String getMappingsWithSubject(
            @QueryParam("uri") URI termURI,
            @QueryParam("profile") URI profile)
            throws IRSException;

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
            @QueryParam("limit") Integer limit)
            throws IRSException;

    /**
     * Retrieve all mappings with the given URI as the subject of the mapping triple. 
     * Limit the number of returned mappings.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param limit maximum number of mappings to return
     * @return 
     */
    public String getMappingsWithSubject(
            @QueryParam("uri") URI termURI,
            @QueryParam("limit") Integer limit)
            throws IRSException;

    /**
     * Retrieve all mappings with the given URI as the target of the mapping 
     * triple. 
     * @param termURI
     * @return 
     */
    public String getMappingsWithTarget(@QueryParam("uri") URI termURI)
            throws IRSException;

    /**
     * Retrieve all mappings with the given URI as the target of the mapping 
     * triple for a specified context profile.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings
     * @return 
     */
    public String getMappingsWithTarget(
            @QueryParam("uri") URI termURI,
            @QueryParam("profile") URI profile)
            throws IRSException;

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
            @QueryParam("limit") Integer limit)
            throws IRSException;

    /**
     * Retrieve all mappings with the given URI as the target of the mapping 
     * triple. 
     * Limit the number of returned mappings.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param limit maximum number of mappings to return
     * @return 
     */
    public String getMappingsWithTarget(
            @QueryParam("uri") URI termURI,
            @QueryParam("limit") Integer limit)
            throws IRSException;
    
    /**
     * Retrieve the evidence for a specified mapping.
     * 
     * @param mappingURI URI that identifies a mapping
     * @return 
     */
    @GET
    @Path("/getMappingDetails")
    public String getMappingDetails(@QueryParam("mapping-uri") URI mappingURI)
            throws IRSException;
    
}
