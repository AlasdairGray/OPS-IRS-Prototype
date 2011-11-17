package uk.ac.manchester.cs.irs.datastore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.turtle.TurtleParser;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.Mapping;

/**
 * A class for creating the IRS database tables and loading in data.
 */
public class IRSDatabaseAdministration {

    private MySQLAccess dbAccess;

    /**
     * Instantiate a connection to the database for creation and insertion
     * 
     * @throws IRSException Problem connecting to database
     */
    protected IRSDatabaseAdministration()
            throws IRSException {
        dbAccess = new MySQLAccess();
    }

    /**
     * Insert a list of RDF statements into the database. It is assumed that
     * the statements represent mappings. No checking of the RDF is performed.
     * 
     * @param rdfDataList List of RDF mapping statements
     * @throws IRSException Problem inserting data into database
     */
    private void insertRDFList(List<org.openrdf.model.Statement> rdfDataList)
            throws IRSException {
        int count = 0;
        for (org.openrdf.model.Statement st : rdfDataList) {
            try {
                Mapping mapping = new Mapping();
                mapping.setSource(st.getSubject().stringValue());
                mapping.setPredicate(st.getPredicate().stringValue());
                mapping.setTarget(st.getObject().stringValue());
                dbAccess.insertLink(mapping);
                count++;
            } catch (IRSException ex) {
                String msg = "Problem converting to URI. Statement ignored.";
                Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
            }
        }
        String msg = count + " links inserted into datastore";
        Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.INFO, msg);
    }

    /**
     * Retrieve the physical location of a file given a relative file name.
     * 
     * @param fileName relative file name to the running context
     * @return full path to the file
     */
    private String getFileLocation(String fileName) {
        String fileLocation = this.getClass().getClassLoader().getResource(fileName).getPath();
        return fileLocation;
    }

    /**
     * Load mapping data from the supplied relative file name, using the 
     * supplied baseURI to resolve any relative URI references.
     * 
     * @param fileName relative file name
     * @param baseURI The URI associated with the data in the file. 
     * @throws IRSException If the data file could not be parsed and loaded.
     */
    public void loadLinkset(String fileName, String baseURI)
            throws IRSException {
        FileReader fileReader = null;
        try {
            String fileLocation = getFileLocation(fileName);
            fileReader = new FileReader(fileLocation);
            RDFParser rdfParser = new TurtleParser();
            LinksetInserter inserter = new LinksetInserter(dbAccess);
            rdfParser.setRDFHandler(inserter);
            rdfParser.parse(fileReader, baseURI);
        } catch (IOException ex) {
            String msg = "Problem reading file " + fileName;
            Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } catch (RDFParseException ex) {
            String msg = "Problem parsing file " + fileName;
            Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } catch (RDFHandlerException ex) {
            String msg = "Problem processing RDF in file " + fileName;
            Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                String msg = "Problem closing file " + fileName;
                Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
                throw new IRSException(msg, ex);
            }
        }
    }

    /**
     * Main method for creating database and loading files.
     * 
     * @param args
     * @throws IRSException 
     */
    public static void main(String[] args) throws IRSException {
        IRSDatabaseAdministration dbCreator = new IRSDatabaseAdministration();
        dbCreator.loadLinkset(
                "linksets/brenda_uniprot.ttl",
                "http://brenda-enzymes.info/");
        dbCreator.loadLinkset(
                "linksets/cs_chembl.ttl", 
                "http://rdf.chemspider.com/");
    }
}