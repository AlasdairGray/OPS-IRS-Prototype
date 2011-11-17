package uk.ac.manchester.cs.irs;

import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.beans.Match;
import uk.ac.manchester.cs.irs.datastore.MySQLAccess;

public class IRSImpl implements IRS {

    private MySQLAccess dbAccess;
    private int DEFAULT_LIMIT = 10;
    
    public IRSImpl() 
            throws IRSException {
        dbAccess = instantiateDBAccess();
    }

    /**
     * Using a method approach to instantiate the database access class so that
     * it can be easily overridden with a mock object for unit testing.
     * @return handle for accessing data store
     * @throws IRSException If there is a problem creating a connection to the database.
     */
    protected MySQLAccess instantiateDBAccess() throws IRSException {
        return new MySQLAccess();
    }

    @Override
    public List<Match> getMappingsWithSubject(URI termURI, URI profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public List<Match> getMappingsWithTarget(URI termURI, URI profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public List<Match> getMappingsWithURI(URI termURI, URI profile, Integer limit) 
            throws IRSException {
        if (termURI == null) {
            String msg = "No term URI provided.";
            Logger.getLogger(IRSImpl.class.getName()).log(Level.SEVERE, msg);
            throw new IRSException(msg);
        }
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }
        List<Match> mappings = dbAccess.getMappingsWithURI(termURI, limit);
        return mappings;
    }

    @Override
    public Mapping getMappingDetails(int mappingId) 
            throws IRSException {
        return dbAccess.getMappingDetails(mappingId);
    }

}
