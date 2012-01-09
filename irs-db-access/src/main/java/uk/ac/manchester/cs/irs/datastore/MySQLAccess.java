package uk.ac.manchester.cs.irs.datastore;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.Value;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.LinksetMetadata;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.beans.Match;
import uk.ac.manchester.cs.irs.IRSConstants;

/**
 *
 * Class for interacting with the underlying MySQL database for retrieving
 * mappings and their details.
 */
public class MySQLAccess implements DBAccess {
    
    /** JDBC URL for the database */
    private String dbUrl;// = "jdbc:mysql://localhost:3306/irs";
    /** username for the database */
    private String username;// = "irs";
    /** password for the database */
    private String password;// = "irs";
    
    /**
     * Instantiate a connection to the database
     * 
     * @throws IRSException If there is a problem connecting to the database.
     */
    public MySQLAccess(String dbUrl, String username, String password) 
            throws IRSException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.dbUrl = dbUrl;
            this.username = username;
            this.password = password;
        } catch (ClassNotFoundException ex) {
            String msg = "Problem loading in MySQL JDBC driver.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        }
    }
    
    /**
     * Retrieve an active connection to the database
     * 
     * @return database connection
     * @throws IRSException if there is a problem establishing a connection
     */
    private Connection getConnection() throws IRSException {
        try {
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            return conn;
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
    @Override
    public List<Match> getMappingsWithURI(String uri, int limit) 
            throws IRSException {
        assert uri != null;
        List<Match> matches = new ArrayList<Match>();
        // Hack to add the identity mapping
        Match match = new Match();
        match.setMatchUri(uri);
        matches.add(match);
        Connection conn = null;
        Statement stmt = null;
        String query = "SELECT DISTINCT id, source AS hit "
                + "FROM mapping "
                + "WHERE target = '" + uri + "' "
                + "UNION "
                + "SELECT DISTINCT id, target AS hit "
                + "FROM mapping "
                + "WHERE source = '" + uri + "' "
                + "LIMIT " + limit; 
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                match = new Match();
                match.setId(IRSConstants.BASE_URI + "mapping/" + rs.getInt("id"));
                match.setMatchUri(rs.getString("hit"));
                matches.add(match);
            }
        } catch (SQLException ex) {
            final String msg = "Problem accessing datastore";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    conn.close();
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
    @Override
    public Mapping getMappingDetails(int mappingId) 
            throws IRSException {
        if (mappingId <= 0) {
            String msg = "No such mapping identifier.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg);
            throw new IRSException(msg);
        }
        String queryString = "SELECT * FROM mapping where id = " + mappingId;
        Mapping mapping = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                mapping = new Mapping();
                mapping.setId(IRSConstants.BASE_URI + "mapping/" + + rs.getInt("id"));
                mapping.setSource(rs.getString("source"));
                mapping.setPredicate(rs.getString("predicate"));
                mapping.setTarget(rs.getString("target"));
            }
        } catch (SQLException ex) {
            final String msg = "Problem accessing datastore";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    conn.close();
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

    /*
     * Method assumes that there is a single linkset metadata object to insert 
     * and this generates a single database id for the linkset.
     */
    @Override
    public int insertLinksetMetadata(LinksetMetadata linksetMetadata) throws IRSException {
        int linksetId;
        String subjectsTarget = linksetMetadata.getSubjectTarget().stringValue();
        String objectsTarget = linksetMetadata.getObjectTarget().stringValue();
        String linkPredicate = linksetMetadata.getLinkPredicate().stringValue();
        String dateCreated = getNullableAttribute(linksetMetadata.getDateCreated());
        String creator = getNullableAttribute(linksetMetadata.getCreator());
        String insertStatement = "INSERT INTO linkset "
                + "(subjectsTarget, objectsTarget, linkPredicate, dateCreated, creator) "
                + "VALUES('" + subjectsTarget + "', '" + objectsTarget + "', '"
                + linkPredicate + "', "
                + dateCreated + ", " + creator + ")";
                Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(insertStatement, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                linksetId = generatedKeys.getInt(1);
            } else {
                final String msg = "Problem iterating over generated keys set.";
                Logger.getLogger(MySQLAccess.class.getName()).log(Level.WARNING, msg);
                throw new IRSException(msg);
            }
        } catch (SQLException ex) {
            final String msg = "Problem accessing datastore";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    String msg = "Unable to close database connection.";
                    Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
                    throw new IRSException(msg, ex);
                }
             }
        }
//        System.out.println("Linkset ID: " + linksetId);
        return linksetId;
    }

    /**
     * If there exists a value for the given attribute, then return it within
     * quotes, otherwise return an SQL NULL.
     * 
     * @param dateCreated
     * @return string representation of the value to be inserted
     */
    private String getNullableAttribute(Value dateCreated) {
        if (dateCreated == null) {
            return "NULL";
        } else {
            return "'" + dateCreated.stringValue() + "'";
        }
    }
    
    /**
     * Insert the provided mapping into the database.
     * 
     * @param link mapping relating URIs from two datasets
     * @exception 
     */
    @Override
    public void insertLink(Mapping link) throws IRSException {
        String insertStatement = "INSERT INTO mapping (linkset_id, source, predicate, target) "
                + "VALUES(?, ?, ?, ?)";
        PreparedStatement insertMapping = null;
        Connection conn = getConnection();
        try {
            insertMapping = conn.prepareStatement(insertStatement);
            insertMapping.setInt(1, link.getLinksetId());
            insertMapping.setString(2, link.getSource());
            insertMapping.setString(3, link.getPredicate());
            insertMapping.setString(4, link.getTarget());
            insertMapping.executeUpdate();
        } catch (SQLException ex) {
            String msg = "Problem inserting values into database.";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            if (insertMapping != null) { 
                try {
                    insertMapping.close();
                    conn.close();
                } catch (SQLException ex) {
                    String msg = "Problem closing prepared insert statement.";
                    Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
                    throw new IRSException(msg, ex);
                }
            }
        }         
    }

    @Override
    public int getNumberLinksets() throws IRSException {
        String query = "SELECT COUNT(*) FROM linkset";
        return answerCountQuery(query);
    }
    
    @Override
    public int getNumberMappings() 
            throws IRSException {
        String queryString = "SELECT COUNT(*) FROM mapping";
        return answerCountQuery(queryString);
    }
    
    /**
     * Common code for processing a count query that returns a single row
     * @param queryString
     * @return
     * @throws IRSException 
     */
    private int answerCountQuery(String queryString) throws IRSException {
        int count;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                final String msg = "Problem retrieving the total number of ***. \nQuery: " + queryString;
                Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg);
                throw new IRSException(msg);
            }
        } catch (SQLException ex) {
            final String msg = "Problem accessing datastore";
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
            throw new IRSException(msg, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    String msg = "Unable to close database connection.";
                    Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, msg, ex);
                    throw new IRSException(msg, ex);
                }
             }
        }
        return count;
    }

}
