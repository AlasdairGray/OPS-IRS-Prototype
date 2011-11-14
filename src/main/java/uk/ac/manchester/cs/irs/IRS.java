package uk.ac.manchester.cs.irs;

import java.net.URI;
import java.util.List;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.beans.Match;

public interface IRS {

    /**
     * Retrieve matches with the given URI as the subject of the mapping 
     * triple for a specified context profile. 
     * 
     * If the limit or profile are null then default values will be used.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings, may be null
     * @param limit maximum number of matches to return, may be null
     * @return a list of matches with the given URI as the subject
     */
    public List<Match> getMappingsWithSubject(
            URI termURI,
            URI profile,
            Integer limit)
            throws IRSException;

    /**
     * Retrieve matches with the given URI as the target of the mapping 
     * triple for a specified context profile. 
     * 
     * If the limit or profile are null then default values will be used.
     * 
     * @param termURI URI specifying the term to find mappings for
     * @param profile URI specifying the context for the mappings, may be null
     * @param limit maximum number of mappings to return, may be null
     * @return a list of matches with the given URI as the target 
     */
    public List<Match> getMappingsWithTarget(
            URI termURI,
            URI profile,
            Integer limit)
            throws IRSException;

    /**
     * Retrieve matches with the given URI as either the subject or target of 
     * the mapping triple for a specified context profile. 
     * 
     * If the limit or profile are null then default values will be used.
     * 
     * @param termURI URI specifying the term to find matches for
     * @param profile URI specifying the context for the mappings, may be null
     * @param limit maximum number of mappings to return, may be null
     * @return a list of matches with the given URI as the subject or target
     */
    public List<Match> getMappingsWithURI(
            URI termURI,
            URI profile,
            Integer limit)
            throws IRSException;
    
    /**
     * Retrieve the evidence for a specified mapping.
     * 
     * @param mappingId mapping identifier
     * @return details of the identified mapping
     */
    public Mapping getMappingDetails(int mappingId)
            throws IRSException;
    
}
