/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.manchester.cs.irs.resources;

import java.net.URI;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.impl.ResponseImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author agray
 */
public class MappingResourceTest {
    
    public MappingResourceTest() {
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

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a 400 status code when we provide no URI
     */
    @Test
    public void testGetMappings() throws Exception {
        System.out.println("getMappings");
        URI uri = null;
        URI profileUri = null;
        Boolean isSubject = null;
        Boolean isTarget = null;
        Integer limit = null;
        MappingResource instance = new MappingResource();
        Response result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(400, result.getStatus());
    }

    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect a 400 status code when we provide no id
     */
    @Test
    public void testGetMappingDetails_nullID() {
        System.out.println("getMappingDetails");
        Integer mappingId = null;
        MappingResource instance = new MappingResource();
        Response result = instance.getMappingDetails(mappingId);
        assertEquals(400, result.getStatus());
    }

    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect a String back when passing in an integer id
     */
    @Test
    public void testGetMappingDetails_ID() {
        System.out.println("getMappingDetails");
        Integer mappingId = 42;
        MappingResource instance = new MappingResource();
        Response result = instance.getMappingDetails(mappingId);
        assertEquals(200, result.getStatus());
    }
    
}
