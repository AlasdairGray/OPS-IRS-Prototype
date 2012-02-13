/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.manchester.cs.irs.datastore;

import java.util.Collection;
import java.util.List;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.LinksetBean;
import uk.ac.manchester.cs.irs.beans.LinksetMetadata;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.beans.Match;

/**
 *
 * @author agray
 */
public interface DBAccess {

    /**
     * Retrieve the selected mapping from the database
     *
     * @param mappingId identifier of the mapping
     * @return mapping details
     */
    Mapping getMappingDetails(int mappingId) throws IRSException;

    /**
     * Retrieve all matches that have the supplied URI as either a source or
     * a target of a mapping.
     *
     * @param uri URI to search for
     * @return List of matches containing the URI
     * @throws IRSException problem retrieving data from database
     */
    List<Match> getMappingsWithURI(String uri, int limit) throws IRSException;

    /**
     * Retrieve the metadata about the selected linkset from the database
     * 
     * @param linksetId identifier of the linkset
     * @return metadata stored about the linkset
     * @throws IRSException 
     */
    public LinksetBean getLinksetDetails(int linksetId) throws IRSException;

    /**
     * Insert the provided linkset metadata into the database and return the
     * newly created identifier for the linkset.
     * 
     * @param linksetMetadata
     * @return newly created identifier for the linkset
     */
    public int insertLinksetMetadata(LinksetMetadata linksetMetadata) throws IRSException;

    /**
     * Insert the provided mapping into the database.
     *
     * @param link mapping relating URIs from two datasets
     * @exception
     */
    void insertLink(Mapping link) throws IRSException;

    /**
     * Insert the provided collection of mapping links into the database.
     *
     * @param collection of mapping links relating URIs from two datasets
     * @exception
     */
    void insertLinkCollection(Collection<Mapping> linkCollection) throws IRSException;
    
    /**
     * Retrieve the total number of linksets available on the server
     * @return 
     */
    public int getNumberLinksets() throws IRSException;
    
    /**
     * Retrieve the total number of mappings available on the server
     */
    public int getNumberMappings() throws IRSException;
    
}
