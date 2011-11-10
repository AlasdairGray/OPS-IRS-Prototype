package uk.ac.manchester.cs.irs;

import java.net.URI;
import java.util.List;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.datastore.MySQLAccess;

public class IRSImpl implements IRS {

    private MySQLAccess dbAccess;
    
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
    public List<Mapping> getMappingsWithSubject(URI termURI, URI profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public List<Mapping> getMappingsWithTarget(URI termURI, URI profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public List<Mapping> getMappingsWithURI(URI termURI, URI profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public Mapping getMappingDetails(int mappingId) 
            throws IRSException {
        return dbAccess.getMappingDetails(mappingId);
    }

}
