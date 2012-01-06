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
    
    private List<Statement> tempStList = new ArrayList<Statement>();
    private final MySQLAccess dbAccess;
    private int count;

    LinksetInserter(MySQLAccess dbAccess) {
        super();
        this.dbAccess = dbAccess;
        count = 0;
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
                if (!tempStList.isEmpty()) {
                    for (Statement tmpSt : tempStList) {
                        if (tmpSt.getPredicate().equals(linkPredicate)) {
                            insertLink(tmpSt);
                        }
                    }
                }
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
            } else if (linkPredicate == null) {
                tempStList.add(st);
            } else if (predicate.equals(linkPredicate)) {
                /* Only store those statements that correspond to the link predicate */
                insertLink(st);
            }
        } catch (IRSException ex) {
            String msg = "Problem inserting link into data store. Statement ignored. \n\t" + st.toString();
            Logger.getLogger(LinksetCollector.class.getName()).log(Level.SEVERE, msg, ex);
            System.err.println(msg);
        }
    }

    /**
     * Inserts the given link statement into the data store.
     * @param st link triple
     */
    private void insertLink(Statement st) throws IRSException {
        Mapping mapping = new Mapping();
        mapping.setSource(st.getSubject().stringValue());
        mapping.setPredicate(st.getPredicate().stringValue());
        mapping.setTarget(st.getObject().stringValue());
        dbAccess.insertLink(mapping);
        count++;
    }
    
    public int getNumberLinksInserted() {
        return count;
    }
    
}
