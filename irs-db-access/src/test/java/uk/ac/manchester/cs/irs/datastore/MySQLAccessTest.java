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
import org.junit.Ignore;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.irs.IRSConstants;

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
     * Should return an exception if we pass in a null uri
     */
    @Test(expected=AssertionError.class)
    public void testGetMappingsWithURI_nullURI() throws Exception {
        String uri = null;
        int limit = 10;
        MySQLAccess instance = new MySQLAccess();
        List result = instance.getMappingsWithURI(uri, limit);
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
        expResult.setId(IRSConstants.BASE_URI + mappingId);
        Mapping result = instance.getMappingDetails(mappingId);
        assertEquals(expResult.getId(), result.getId());
    }
    
}
