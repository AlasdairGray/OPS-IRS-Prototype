package uk.ac.manchester.cs.irs.datastore;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.Statement;
import org.openrdf.rio.helpers.StatementCollector;

/**
 *
 */
class LinksetCollector extends StatementCollector {

    private static final String RDF_TYPE = 
            "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    private static final String VOID_DATASET =
            "http://rdfs.org/ns/void#Dataset";
    private static final String VOID_LINK_PREDICATE =
            "http://rdfs.org/ns/void#linkPredicate";

    private List<URI> datasets = new ArrayList<URI>(2);
    private org.openrdf.model.URI linkPredicate;
    private List<Statement> tempStList = new ArrayList<Statement>();
    private final List<Statement> rdfDataList;
    
    LinksetCollector(List<Statement> rdfDataList) {
        super(rdfDataList);
        this.rdfDataList = rdfDataList;
    }
    
    public List<URI> getDatasets() {
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
            if (st.getPredicate().stringValue().equals(RDF_TYPE) && 
                st.getObject().stringValue().equals(VOID_DATASET)) {
                //TODO throw error if more than 2 datasets declared
                datasets.add(new URI(st.getSubject().stringValue()));
            } else if (st.getPredicate().stringValue().equals(VOID_LINK_PREDICATE)) {
                linkPredicate = (org.openrdf.model.URI) st.getObject();
                if (!tempStList.isEmpty()) {
                    for (Statement tmpSt : tempStList) {
                        if (tmpSt.getPredicate().equals(linkPredicate)) {
                            super.handleStatement(tmpSt);
                        }
                    }
                }
            } else if (linkPredicate == null) {
                tempStList.add(st);
            } else if (st.getPredicate().equals(linkPredicate)) {
                /* Only store those statements that correspond to the link predicate */
                super.handleStatement(st);
            }
        } catch (URISyntaxException ex) {
            String msg = "Problem converting Sesame resource to URI";
            Logger.getLogger(LinksetCollector.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }
    
}
