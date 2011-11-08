package uk.ac.manchester.cs.irs;

import java.net.URI;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.datastore.MySQLAccess;

public class IRSImpl implements IRS {

    private MySQLAccess dbAccess;
    
    public IRSImpl() 
            throws IRSException {
//        System.out.println("About to initialise database access");
//        dbAccess = new MySQLAccess();
//        System.out.println("Database access initialised");
    }
    
    @Override
    public String getMappingsWithSubject(URI termURI) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithSubject(URI termURI, URI profile) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithSubject(URI termURI, URI profile, int limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithSubject(URI termURI, int limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithTarget(URI termURI) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithTarget(URI termURI, URI profile) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithTarget(URI termURI, URI profile, int limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithTarget(URI termURI, int limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public Mapping getMappingDetails(int mappingId) 
            throws IRSException {
        //        return dbAccess.getMappingDetails(mappingId);
        Mapping mapping = new Mapping();
        mapping.setId(mappingId);
        return mapping;
    }

}
