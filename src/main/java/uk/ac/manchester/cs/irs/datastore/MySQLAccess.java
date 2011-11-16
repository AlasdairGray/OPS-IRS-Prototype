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
import uk.ac.manchester.cs.irs.beans.Match;

/**
 *
 * Class for interacting with the underlying MySQL database for retrieving
 * mappings and their details.
 */
public class MySQLAccess {
    private String MAPPING_NAMESPACE = "http://ondex2.cs.man.ac.uk/irs/";
    
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
     * Retrieve all matches that have the supplied URI as either a source or
     * a target of a mapping.
     * 
     * @param uri URI to search for
     * @return List of matches containing the URI
     * @throws IRSException problem retrieving data from database
     */
    public List<Match> getMappingsWithURI(URI uri, int limit) 
            throws IRSException {
        List<Match> matches = new ArrayList<Match>();
        Statement stmt = null;
        String uriString = uri.toASCIIString();
        String query = "SELECT DISTINCT id, source AS hit "
                + "FROM mapping "
                + "WHERE source = '" + uriString + "' "
                + "UNION "
                + "SELECT DISTINCT id, target AS hit "
                + "FROM mapping "
                + "WHERE target = '" + uriString + "'"
                + "LIMIT " + limit; 
        Match match;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                match = new Match();
                match.setId(new URI(MAPPING_NAMESPACE + rs.getInt("id")));
                match.setMatchUri(new URI(rs.getString("hit")));
                matches.add(match);
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
        return matches;
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
                mapping.setId(new URI(MAPPING_NAMESPACE + rs.getInt("id")));
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
