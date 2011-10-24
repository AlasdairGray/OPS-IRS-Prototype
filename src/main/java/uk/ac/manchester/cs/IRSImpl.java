package uk.ac.manchester.cs;

import java.net.URI;

public class IRSImpl implements IRS {

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
    public String getMappingsWithSubject(URI termURI, URI profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithSubject(URI termURI, Integer limit) 
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
    public String getMappingsWithTarget(URI termURI, URI profile, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingsWithTarget(URI termURI, Integer limit) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

    @Override
    public String getMappingDetails(URI mappingURI) 
            throws IRSException {
        throw new IRSException("Not supported yet.");
    }

}
