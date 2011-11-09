package uk.ac.manchester.cs.irs;

import java.net.URI;
import java.util.List;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.datastore.MySQLAccess;

public class IRSImpl implements IRS {

    private MySQLAccess dbAccess;
    
    public IRSImpl() 
            throws IRSException {
        dbAccess = new MySQLAccess();
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
