package uk.ac.manchester.cs.irs;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.beans.Match;
import uk.ac.manchester.cs.irs.beans.ServerStatistics;
import uk.ac.manchester.cs.irs.datastore.DBAccess;
import uk.ac.manchester.cs.irs.datastore.MySQLAccess;

public class IRSImpl implements IRS {

    private DBAccess dbAccess;
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
    protected DBAccess instantiateDBAccess() throws IRSException {
        return new MySQLAccess("jdbc:mysql://localhost:3306/irs", "irs", "irs");
    }

    @Override
    public List<Match> getMappingsWithSubject(String termURI, String profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public List<Match> getMappingsWithTarget(String termURI, String profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public List<Match> getMappingsWithURI(String termURI, String profile, Integer limit) 
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

    @Override
    public ServerStatistics getStatistics() throws IRSException {
        int numberLinksets = dbAccess.getNumberLinksets();
        int numberMappings = dbAccess.getNumberMappings();
        return new ServerStatistics(numberLinksets, numberMappings);
    }

}
