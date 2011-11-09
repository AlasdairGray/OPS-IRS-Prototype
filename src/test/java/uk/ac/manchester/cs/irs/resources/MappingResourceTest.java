package uk.ac.manchester.cs.irs.resources;

import static org.easymock.EasyMock.expect;

import java.net.URI;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.manchester.cs.irs.IRS;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.Mapping;

/**
 *
 */
public class MappingResourceTest extends EasyMockSupport {
    
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
    public void testGetMappings_allNull() throws Exception {
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
     * Test of getMappings method, of class MappingResource.
     * Expect a 400 status code when we provide no URI
     */
    @Test
    public void testGetMappings_nullURI() throws Exception {
        URI uri = null;
        URI profileUri = new URI("http://something.org");
        Boolean isSubject = true;
        Boolean isTarget = false;
        Integer limit = 1;
        MappingResource instance = new MappingResource();
        Response result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(400, result.getStatus());
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a 200 status code when we provide only valid URI
     */
    @Test
    public void testGetMappings_validURI() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = new URI("http://something.org");
        URI profileUri = null;
        Boolean isSubject = null;
        Boolean isTarget = null;
        Integer limit = null;
        expect(mockIRS.getMappingsWithURI(uri, null, null))
                .andReturn(new ArrayList<Mapping>());
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        Response result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(200, result.getStatus());
        verifyAll();
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a 200 status code when we provide a valid URI and profile URI
     */
    @Test
    public void testGetMappings_uriAndProfile() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = new URI("http://something.org");
        URI profileUri = new URI("http://profile.com");
        Boolean isSubject = null;
        Boolean isTarget = null;
        Integer limit = null;
        expect(mockIRS.getMappingsWithURI(uri, profileUri, null))
                .andReturn(new ArrayList<Mapping>());
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        Response result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(200, result.getStatus());
        verifyAll();
    }

    //TODO Write test for GetMappings with permutations of parameters
    
    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect a 400 status code when we provide no id
     */
    @Test
    public void testGetMappingDetails_nullID() throws IRSException {
        Integer mappingId = null;
        MappingResource instance = new MappingResource();
        Response result = instance.getMappingDetails(mappingId);
        assertEquals(400, result.getStatus());
    }
    
    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect a 404 status code when we provide an id of 0
     */
    @Test
    public void testGetMappingDetails_zeroID() throws IRSException {
        IRS mockIRS = createMock(IRS.class);
        expect(mockIRS.getMappingDetails(0)).andThrow(new IRSException(""));
        replayAll();
        Integer mappingId = 0;
        MappingResource instance = new MappingResource(mockIRS);
        Response result = instance.getMappingDetails(mappingId);
        assertEquals(404, result.getStatus());
        verifyAll();
    }
    
    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect a 404 status code when we provide an id that is not in the db
     */
    @Test
    public void testGetMappingDetails_unusedID() throws IRSException {
        IRS mockIRS = createMock(IRS.class);
        Integer mappingId = 1038470;
        expect(mockIRS.getMappingDetails(mappingId)).andThrow(new IRSException(""));
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        Response result = instance.getMappingDetails(mappingId);
        assertEquals(404, result.getStatus());
        verifyAll();
    }

    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect a Mapping back when passing in an integer id for which 
     * there is an entry
     */
    @Test
    public void testGetMappingDetails_validID() throws IRSException {
        IRS mockIRS = createMock(IRS.class);
        final Mapping expectedMapping = new Mapping();
        expectedMapping.setId(42);
        expect(mockIRS.getMappingDetails(42)).andReturn(expectedMapping);
        replayAll();
        Integer mappingId = 42;
        MappingResource instance = new MappingResource(mockIRS);
        Response result = instance.getMappingDetails(mappingId);
        assertEquals(200, result.getStatus());
        Mapping mapping = (Mapping) result.getEntity();
        assertEquals(42, mapping.getId());
        verifyAll();
    }
    
}
