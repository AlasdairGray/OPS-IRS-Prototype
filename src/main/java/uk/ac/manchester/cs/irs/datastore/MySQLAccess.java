package uk.ac.manchester.cs.irs.datastore;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.Mapping;

/**
 *
 * Class for interacting with the underlying MySQL database for retrieving
 * mappings and their details.
 */
public class MySQLAccess {
    
    /** JDBC URL for the database */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/irs";
    /** username for the database */
    private static final String USER = "irs";
    /** password for the database */
    private static final String PASS = "irs";
    
    /** Connection to the database */
    private Connection conn;
    
    /**
     * Instantiate a connection to the database
     * 
     * @throws IRSException If there is a problem connecting to the database.
     */
    public MySQLAccess() 
            throws IRSException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException ex) {
            String msg = "Problem loading in MySQL JDBC driver.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } catch (SQLException ex) {
            final String msg = "Problem connecting to database.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        }
    }
    
    /**
     * Retrieve all mappings that have the supplied URI as either a source or
     * a target of the mapping.
     * 
     * @param uri URI to search for
     * @return List of mappings containing the URI
     * @throws IRSException problem retrieving data from database
     */
    public List<Mapping> getMappings(URI uri) 
            throws IRSException {
        List<Mapping> mappings = new ArrayList<Mapping>();
        Statement stmt = null;
        String uriString = uri.toASCIIString();
        String query = "SELECT id, source, predicate, target "
                + "FROM mapping "
                + "WHERE source = '" + uriString + "' "
                + "OR target = '" + uriString + "'"; 
        Mapping mapping;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                mapping = new Mapping();
                mapping.setId(rs.getInt("id"));
                mapping.setSource(new URI(rs.getString("source")));
                mapping.setPredicate(new URI(rs.getString("predicate")));
                mapping.setTarget(new URI(rs.getString("target")));
                mappings.add(mapping);
            }
        } catch (URISyntaxException ex) {
            final String msg = "Problem converting stored value to a URI.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } catch (SQLException ex) {
            final String msg = "Problem accessing datastore";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    final String msg = "Unable to close database connection.";
                    Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
                    throw new IRSException(msg, ex);
                }
             }
        }
        return mappings;
    }

    /**
     * Retrieve the selected mapping from the database
     * 
     * @param mappingId identifier of the mapping
     * @return mapping details
     */
    public Mapping getMappingDetails(int mappingId) 
            throws IRSException {
        if (mappingId <= 0) {
            String msg = "No such mapping identifier.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg);
            throw new IRSException(msg);
        }
        String queryString = "SELECT * FROM mapping where id = " + mappingId;
        Mapping mapping = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                mapping = new Mapping();
                mapping.setId(rs.getInt("id"));
                mapping.setSource(new URI(rs.getString("source")));
                mapping.setPredicate(new URI(rs.getString("predicate")));
                mapping.setTarget(new URI(rs.getString("target")));
            }
        } catch (URISyntaxException ex) {
            final String msg = "Problem converting stored value to a URI.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } catch (SQLException ex) {
            final String msg = "Problem accessing datastore";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    String msg = "Unable to close database connection.";
                    Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
                    throw new IRSException(msg, ex);
                }
             }
        }
        if (mapping == null) {
            final String msg = "No mapping with the given identifier " + mappingId;
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg);
            throw new IRSException(msg);
        }
        return mapping;
    }
    
}
