/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.manchester.cs.irs.datastore;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.beans.Match;
import uk.ac.manchester.cs.irs.IRSConstants;

/**
 *
 * These tests rely on a operational MySQL database
 * 
 */
public class MySQLAccessTest {
    
    public MySQLAccessTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        IRSConstants.BASE_URI = "http://www.example.com/OPS-IRS/";
    }
    
    @After
    public void tearDown() {
    }

    /*************************************************************************
     * 
     * tests for getMappingsWithURI
     * 
     *************************************************************************/

    /**
     * Test of getMappingsWithURI method, of class MySQLAccess.
     * 
     * Should return an exception if we pass in a null uri
     */
    @Test(expected=AssertionError.class)
    public void testGetMappingsWithURI_nullURI() throws Exception {
        String uri = null;
        int limit = 10;
        MySQLAccess instance = new MySQLAccess();
        List result = instance.getMappingsWithURI(uri, limit);
    }

    /**
     * Test of getMappingsWithURI method, of class MySQLAccess.
     * 
     * Should return an empty list when a valid uri is passed in but the
     * uri does not exist in the database.
     */
    @Test
    public void testGetMappingsWithURI_validNonExistingURI() throws Exception {
        String uri = "http://example.com/1.1.1.1";
        int limit = 10;
        MySQLAccess instance = new MySQLAccess();
        List<Match> result = instance.getMappingsWithURI(uri, limit);
        assertEquals(1, result.size());
        Match match = result.get(0);
        assertEquals("http://example.com/1.1.1.1", match.getMatchUri());
    }

    /**
     * Test of getMappingsWithURI method, of class MySQLAccess.
     * 
     * Should return a result set with 
     */
    @Test
    public void testGetMappingsWithURI_validExistingURI() throws Exception {
        String uri = "http://brenda-enzymes.info/1.1.1.1";
        int limit = 10;
        MySQLAccess instance = new MySQLAccess();
        List<Match> result = instance.getMappingsWithURI(uri, limit);
        //XXX: Size depends on the data contained in the database!
        assertEquals(2, result.size());
        Match match = result.get(0);
        assertEquals("http://brenda-enzymes.info/1.1.1.1", match.getMatchUri());
        match = result.get(1);
        assertEquals(IRSConstants.BASE_URI + "mapping/1", match.getId());
        assertEquals("http://purl.uniprot.org/enzyme/1.1.1.1", match.getMatchUri());
    }

    /*************************************************************************
     * 
     * tests for getMappingDetails
     * 
     *************************************************************************/
    
    /**
     * Test of getMappingDetails method, of class MySQLAccess.
     * Attempts to retrieve a mapping with the identifier of zero, which should
     * not exist.
     */
    @Test(expected=IRSException.class)
    public void testGetMappingDetails_idOfZero() 
            throws IRSException {
        System.out.println("getMappingDetails zero id");
        int mappingId = 0;
        MySQLAccess instance = new MySQLAccess();
        instance.getMappingDetails(mappingId);
    }

    /**
     * Test of getMappingDetails method, of class MySQLAccess.
     * Attempts to retrieve a mapping using a mapping identifier that does
     * not exist.
     */
    @Test(expected=IRSException.class)
    public void testGetMappingDetails_invalidId() 
            throws IRSException {
        System.out.println("getMappingDetails invalid id");
        int mappingId = 1029340087;
        MySQLAccess instance = new MySQLAccess();
        instance.getMappingDetails(mappingId);
    }

    /**
     * Test of getMappingDetails method, of class MySQLAccess.
     * Retrieve the details of a mapping using a valid identifier
     * 
     */
    @Test
    /*
     * Test relies on data actually in the database, ignored for now
     */
    public void testGetMappingDetails_validID() 
            throws IRSException {
        System.out.println("getMappingDetails");
        int mappingId = 1;
        MySQLAccess instance = new MySQLAccess();
        Mapping expResult = new Mapping();
        expResult.setId(IRSConstants.BASE_URI + "mapping/" + + mappingId);
        Mapping result = instance.getMappingDetails(mappingId);
        assertEquals(expResult.getId(), result.getId());
    }
    
}
