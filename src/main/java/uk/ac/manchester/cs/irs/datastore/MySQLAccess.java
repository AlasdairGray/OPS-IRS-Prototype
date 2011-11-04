/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author agray
 */
public class MySQLAccess {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/irs";
    private static final String USER = "irs";
    private static final String PASS = "irs";
    
    private Connection conn;
    
    public MySQLAccess() 
            throws IRSException {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
            throw new IRSException("Problem connecting to database.", ex);
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
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
            throw new IRSException("Problem converting stored value to a URI.", ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
            throw new IRSException("Problem accessing datastore", ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
                    throw new IRSException("Unable to close database connection.", ex);
                }
             }
        }
        return mappings;
    }
    
}
