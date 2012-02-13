package uk.ac.manchester.cs.irs.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.helpers.RDFHandlerBase;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.LinksetMetadata;
import uk.ac.manchester.cs.irs.beans.Mapping;

/**
 *
 */
class LinksetInserter extends RDFHandlerBase {

    private List<String> datasets = new ArrayList<String>(2);
    URI linkPredicate;
    URI subjectTarget;
    URI objectTarget;
    URI subset;
    Literal dateCreated;
    Value creator;
    int linksetId;

    private final DBAccess dbAccess;
    private int count;
    boolean processingHeader = true;
    
	private final int BLOCK_SIZE = 100000;
	private List<Mapping> tempMappings = new ArrayList<Mapping>(BLOCK_SIZE);

    LinksetInserter(DBAccess dbAccess) {
        super();
        this.dbAccess = dbAccess;
        count = 0;
        System.out.println("Block size: " + BLOCK_SIZE);
    }

    public List<String> getDatasets() {
        return datasets;
    }

    @Override
    /**
     * Override the standard behaviour of the statement collector.
     * If the statement is a void statement, then gather in linkset
     * metadata. Use the information to collect only those statements
     * that are part of the mapping linkset.
     */
    public void handleStatement(Statement st) throws RDFHandlerException {
        try {
            if (processingHeader) {
                processHeaderStatement(st);
            } else {
                if (st.getPredicate().equals(linkPredicate)) {
                    /* Only store those statements that correspond to the link predicate */
                    insertLink(st);
                }
            }
        } catch (IRSException ex) {
            String msg = "Problem inserting link into data store. Statement ignored. \n\t" + st.toString();
            Logger.getLogger(LinksetCollector.class.getName()).log(Level.SEVERE, msg, ex);
            System.err.println(msg);
        }
    }

    /**
     * Process an RDF statement that forms part of the VoID header for the 
     * linkset file.
     * 
     * Once the header processor detects that it is starting to process links
     * it sets a flag, inserts the VoID header information into the database,
     * and then goes into a link insert only mode.
     * 
     * @param st an RDF statement
     * @throws RDFHandlerException
     * @throws IRSException 
     */
    private void processHeaderStatement(Statement st) throws RDFHandlerException, IRSException {
        final URI predicate = st.getPredicate();
        final String predicateStr = predicate.stringValue();
        final Value object = st.getObject();
        if (predicateStr.equals(RdfConstants.TYPE)
                && object.stringValue().equals(VoidConstants.DATASET)) {
            if (datasets.size() == 2) {
                throw new RDFHandlerException("Two datasets have already been declared.");
            }
            datasets.add(st.getSubject().stringValue());
        } else if (predicateStr.equals(VoidConstants.SUBJECTSTARGET)) {
            subjectTarget = (URI) object;
        } else if (predicateStr.equals(VoidConstants.OBJECTSTARGET)) {
            objectTarget = (URI) object;
        } else if (predicateStr.equals(VoidConstants.TARGET)) {
            if (subjectTarget == null) {
                subjectTarget = (URI) object;
            } else if (objectTarget == null) {
                objectTarget = (URI) object;
            } else {
                throw new RDFHandlerException("More than two targets have been declared.");
            }
        } else if (predicateStr.equals(VoidConstants.SUBSET)) {
            if (subset != null) {
                throw new RDFHandlerException("Linkset can only be declared to be the subset of at most one dataset.");
            }
            subset = (URI) object;
        } else if (predicateStr.equals(VoidConstants.LINK_PREDICATE)) {
            if (linkPredicate != null) {
                throw new RDFHandlerException("Linkset can only be declared to have one link predicate.");
            }
            linkPredicate = (URI) object;
        } else if (predicate.equals(DctermsConstants.CREATED)) {
            if (dateCreated != null) {
                throw new RDFHandlerException("Linkset can only have one creation date.");
            }
            dateCreated = (Literal) object;
        } else if (predicate.equals(DctermsConstants.CREATOR)) {
            if (creator != null) {
                throw new RDFHandlerException("Linkset can only have one creator.");
            }
            creator = object;
        } else if (linkPredicate != null && predicate.equals(linkPredicate)) {
            /* Assumes all metadata is declared before the links */
            finishProcessingHeader();
            insertLink(st);
        }
        // Ignores any predicate that we do not know how to process
    }
    
    private void finishProcessingHeader() throws IRSException {
        LinksetMetadata linksetMetadata = new LinksetMetadata(subjectTarget, objectTarget,
                linkPredicate, dateCreated, creator);
        linksetId = dbAccess.insertLinksetMetadata(linksetMetadata);
        processingHeader = false;
    }

    /**
     * Inserts the given link statement into the data store.
     * @param st link triple
     */
    private void insertLink(Statement st) throws IRSException {
        Mapping mapping = new Mapping();
        mapping.setLinksetId(linksetId);
        mapping.setSource(st.getSubject().stringValue());
        mapping.setPredicate(st.getPredicate().stringValue());
        mapping.setTarget(st.getObject().stringValue());
        tempMappings.add(mapping);
        if (tempMappings.size() >= BLOCK_SIZE) {
        	dbAccess.insertLinkCollection(tempMappings);
        	tempMappings = new ArrayList<Mapping>(BLOCK_SIZE);
        }
        count++;
    }

    public void endRDF() {
    	try {
			dbAccess.insertLinkCollection(tempMappings);
		} catch (IRSException e) {
			String msg = "Problem inserting link into data store. ";
            Logger.getLogger(LinksetCollector.class.getName()).log(Level.SEVERE, msg, e);
            System.err.println(msg + e);
		}
    }
    
    public int getNumberLinksInserted() {
        return count;
    }
    
}
