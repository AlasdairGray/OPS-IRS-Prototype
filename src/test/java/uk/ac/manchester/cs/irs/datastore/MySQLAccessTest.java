/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.manchester.cs.irs.datastore;

import java.net.URI;
import java.net.URISyntaxException;
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
     */
    @Test@Ignore
    public void testGetMappingsWithURI() throws Exception {
        URI uri = null;
        int limit = 10;
        MySQLAccess instance = new MySQLAccess();
        List expResult = null;
        List result = instance.getMappingsWithURI(uri, limit);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        int mappingId = 1029340;
        MySQLAccess instance = new MySQLAccess();
        instance.getMappingDetails(mappingId);
    }

    /**
     * Test of getMappingDetails method, of class MySQLAccess.
     * Retrieve the details of a mapping using a valid identifier
     */
    @Test
    public void testGetMappingDetails_validID() 
            throws IRSException, URISyntaxException {
        System.out.println("getMappingDetails");
        int mappingId = 1;
        MySQLAccess instance = new MySQLAccess();
        Mapping expResult = new Mapping();
        expResult.setId(mappingId);
        Mapping result = instance.getMappingDetails(mappingId);
        assertEquals(expResult.getId(), result.getId());
    }
    
}
