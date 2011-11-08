package uk.ac.manchester.cs.irs;

import java.net.URI;
import uk.ac.manchester.cs.irs.beans.Mapping;

public interface IRS {
    
    /**
     * Retrieve all mappings with the given URI as the subject of the mapping triple. 
     * @param termURI
     * @return 
     */
    public String getMappingsWithSubject(URI termURI)
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
            URI termURI,
            URI profile)
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
    public String getMappingsWithSubject(
            URI termURI,
            URI profile,
            int limit)
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
            URI termURI,
            int limit)
            throws IRSException;

    /**
     * Retrieve all mappings with the given URI as the target of the mapping 
     * triple. 
     * @param termURI
     * @return 
     */
    public String getMappingsWithTarget(URI termURI)
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
            URI termURI,
            URI profile)
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
    public String getMappingsWithTarget(
            URI termURI,
            URI profile,
            int limit)
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
            URI termURI,
            int limit)
            throws IRSException;
    
    /**
     * Retrieve the evidence for a specified mapping.
     * 
     * @param mappingId int that identifies a mapping
     * @return details of the identified mapping
     */
    public Mapping getMappingDetails(int mappingId)
            throws IRSException;
    
}
