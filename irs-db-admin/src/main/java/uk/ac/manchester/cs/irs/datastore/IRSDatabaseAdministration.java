package uk.ac.manchester.cs.irs.datastore;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.turtle.TurtleParser;
import uk.ac.manchester.cs.irs.IRSException;

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
        dbAccess = new MySQLAccess("jdbc:mysql://localhost:3306/irs", "irs", "irs");
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
//            String fileLocation = getFileLocation(fileName);
            fileReader = new FileReader(fileName);
            RDFParser rdfParser = new TurtleParser();
            LinksetInserter inserter = new LinksetInserter(dbAccess);
            rdfParser.setRDFHandler(inserter);
            rdfParser.parse(fileReader, baseURI);
            int count = inserter.getNumberLinksInserted();
            System.out.println(count + " links inserted into datastore");
        } catch (IOException ex) {
            String msg = "Problem reading file " + fileName;
            Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
            System.err.println(msg);
            throw new IRSException(msg, ex);
        } catch (RDFParseException ex) {
            String msg = "Problem parsing file " + fileName;
            Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
            System.err.println(msg);
            throw new IRSException(msg, ex);
        } catch (RDFHandlerException ex) {
            String msg = "Problem processing RDF in file " + fileName;
            Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
            System.err.println(msg);
            throw new IRSException(msg, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                String msg = "Problem closing file " + fileName;
                Logger.getLogger(IRSDatabaseAdministration.class.getName()).log(Level.SEVERE, msg, ex);
                System.err.println(msg);
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
        if (args.length != 2) {
            System.err.println("Please provide the path to a linkset file and "
                    + "the base URI for the file.");
            System.exit(1);
        }
        String fileName = args[0];
        String baseURI = args[1];
        dbCreator.loadLinkset(fileName, baseURI);
    }
}