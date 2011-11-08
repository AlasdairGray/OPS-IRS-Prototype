package uk.ac.manchester.cs.irs.datastore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.helpers.StatementCollector;
import org.openrdf.rio.turtle.TurtleParser;
import uk.ac.manchester.cs.irs.IRSException;

public class IRSDatabaseCreation {
    
    //TODO: Load data from file
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/irs";
    private static final String USER = "irs";
    private static final String PASS = "irs";
    
    private Connection conn;

    protected IRSDatabaseCreation() 
            throws IRSException {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            String msg = "Problem connecting to database";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        }
    }
    
    private void createDatabase() throws IRSException {
        try {
            String sql = readFile("scripts/createMappingTable.sql");
            java.sql.Statement st = conn.createStatement();
            st.execute("DROP TABLE mapping");
            st.execute(sql);
        } catch (SQLException ex) {
            String msg = "Problem executing SQL statement to create database.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        }
    }

    private void insertRDFList(List<org.openrdf.model.Statement> rdfDataList) 
            throws IRSException {
        String insertStatement = "INSERT INTO IRS.mapping (source, predicate, target) "
                + "VALUES(?, ?, ?)";
        PreparedStatement insertMapping = null;
        try {
            insertMapping = conn.prepareStatement(insertStatement);
            for (org.openrdf.model.Statement st : rdfDataList) {
                insertMapping.setString(1, st.getSubject().stringValue());
                insertMapping.setString(2, st.getPredicate().stringValue());
                insertMapping.setString(3, st.getObject().stringValue());
                insertMapping.executeUpdate();
            }
        }         
        catch (SQLException ex) {
            String msg = "Problem inserting values into database.";
            Logger.getLogger(IRSDatabaseCreation.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            if (insertMapping != null) { 
                try {
                    insertMapping.close();
                } catch (SQLException ex) {
                    String msg = "Problem closing prepared insert statement.";
                    Logger.getLogger(IRSDatabaseCreation.class.getName()).log(Level.SEVERE, msg, ex);
                    throw new IRSException(msg, ex);
                }
            }
        } 
    }
    
    private String readFile(String fileName) 
            throws IRSException {
        {
            String fileLocation = getFileLocation(fileName);
            System.out.println("File location:" + fileLocation);
            FileReader fileReader = null;
            StringBuilder sb = new StringBuilder();
            try {
                fileReader = new FileReader(fileLocation);
                BufferedReader in = new BufferedReader(fileReader);
                String str;
                while ((str = in.readLine()) != null) {
                    sb.append(str).append("\n");
                }
                in.close();
            } catch (IOException ex) {
                String msg = "Problem reading file " + fileName;
                Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
                throw new IRSException(msg, ex);
            } finally {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    String msg = "Problem closing file " + fileName;
                    Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
                    throw new IRSException(msg, ex);
                }
            }
            return sb.toString();
        }
    }

    private String getFileLocation(String fileName) {
        String fileLocation = this.getClass().getClassLoader().getResource(fileName).getPath();
        return fileLocation;
    }

    private void loadDataFromFile(String fileName, String baseURI) 
            throws IRSException {
        FileReader fileReader = null;
        try {
            String fileLocation = getFileLocation(fileName);
            fileReader = new FileReader(fileLocation);
            List<org.openrdf.model.Statement> rdfDataList = 
                    new ArrayList<org.openrdf.model.Statement>();
            RDFParser rdfParser = new TurtleParser();
            StatementCollector collector = new StatementCollector(rdfDataList);
            rdfParser.setRDFHandler(collector);
            rdfParser.parse(fileReader, baseURI);
            insertRDFList(rdfDataList);
        } catch (IOException ex) {
            String msg = "Problem reading file " + fileName;
            Logger.getLogger(IRSDatabaseCreation.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } catch (RDFParseException ex) {
            String msg = "Problem parsing file " + fileName;
            Logger.getLogger(IRSDatabaseCreation.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } catch (RDFHandlerException ex) {
            String msg = "Problem processing RDF in file " + fileName;
            Logger.getLogger(IRSDatabaseCreation.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                String msg = "Problem closing file " + fileName;
                Logger.getLogger(IRSDatabaseCreation.class.getName()).log(Level.SEVERE, msg, ex);
                throw new IRSException(msg, ex);
            }
        }
    }
    
    public static void main(String[] args) throws IRSException {
        IRSDatabaseCreation dbCreator = new IRSDatabaseCreation();
        dbCreator.createDatabase();
        dbCreator.loadDataFromFile(
                "linksets/brenda_uniprot_has_ec_number_linkset.ttl",
                "http://brenda-enzymes.info/");
    }
    
}
