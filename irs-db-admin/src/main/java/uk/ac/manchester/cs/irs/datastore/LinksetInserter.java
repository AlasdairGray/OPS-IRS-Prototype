package uk.ac.manchester.cs.irs.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.rio.helpers.RDFHandlerBase;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.Mapping;

/**
 *
 */
class LinksetInserter extends RDFHandlerBase {

    private static final String RDF_TYPE =
            "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    private static final String VOID_DATASET =
            "http://rdfs.org/ns/void#Dataset";
    private static final String VOID_LINK_PREDICATE =
            "http://rdfs.org/ns/void#linkPredicate";
    private List<String> datasets = new ArrayList<String>(2);
    private URI linkPredicate;
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
    public void handleStatement(Statement st) {
        try {
            if (st.getPredicate().stringValue().equals(RDF_TYPE)
                    && st.getObject().stringValue().equals(VOID_DATASET)) {
                //TODO throw error if more than 2 datasets declared
                datasets.add(st.getSubject().stringValue());
            } else if (st.getPredicate().stringValue().equals(VOID_LINK_PREDICATE)) {
                linkPredicate = (URI) st.getObject();
                if (!tempStList.isEmpty()) {
                    for (Statement tmpSt : tempStList) {
                        if (tmpSt.getPredicate().equals(linkPredicate)) {
                            insertLink(tmpSt);
                        }
                    }
                }
            } else if (linkPredicate == null) {
                tempStList.add(st);
            } else if (st.getPredicate().equals(linkPredicate)) {
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
