package uk.ac.manchester.cs.irs.resources;

import java.net.URISyntaxException;
import static org.easymock.EasyMock.expect;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
import uk.ac.manchester.cs.irs.beans.Match;

/**
 *
 */
public class MappingResourceTest extends EasyMockSupport {
    private String MAPPING_NAMESPACE = "http://ondex2.cs.man.ac.uk/irs/";
    
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
     * Expect an error when no uri is provided
     */
    @Test(expected=IllegalArgumentException.class)
    public void testGetMappings_allNull() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = null;
        URI profileUri = null;
        Boolean isSubject = null;
        Boolean isTarget = null;
        Integer limit = null;
        MappingResource instance = new MappingResource(mockIRS);
        instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect an error when no uri is provided
     */
    @Test(expected=IllegalArgumentException.class)
    public void testGetMappings_nullURI() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = null;
        URI profileUri = new URI("http://something.org");
        Boolean isSubject = true;
        Boolean isTarget = false;
        Integer limit = 1;
        MappingResource instance = new MappingResource(mockIRS);
        instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a List of matches back when a valid URI is provided
     */
    @Test
    public void testGetMappings_validURINoMatches() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = new URI("http://something.org");
        URI profileUri = null;
        Boolean isSubject = null;
        Boolean isTarget = null;
        Integer limit = null;
        expect(mockIRS.getMappingsWithURI(uri, null, null))
                .andReturn(new ArrayList<Match>());
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        List<Match> result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(true, result.isEmpty());
        verifyAll();
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a list of matches back with a valid URI and profile URI
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
                .andReturn(new ArrayList<Match>());
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        List<Match> result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(true, result.isEmpty());
        verifyAll();
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a list of matches back when we provide a valid URI, profile URI,
     * and set the isSubject flag
     */
    @Test
    public void testGetMappings_uriAndProfileAndSubject() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = new URI("http://something.org");
        URI profileUri = new URI("http://profile.com");
        Boolean isSubject = true;
        Boolean isTarget = false;
        Integer limit = null;
        expect(mockIRS.getMappingsWithSubject(uri, profileUri, null))
                .andReturn(new ArrayList<Match>());
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        List<Match> result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(true, result.isEmpty());
        verifyAll();
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a list of matches when we provide a valid URI, profile URI and
     * set the target flag
     */
    @Test
    public void testGetMappings_uriAndProfileAndTarget() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = new URI("http://something.org");
        URI profileUri = new URI("http://profile.com");
        Boolean isSubject = false;
        Boolean isTarget = true;
        Integer limit = null;
        expect(mockIRS.getMappingsWithTarget(uri, profileUri, null))
                .andReturn(new ArrayList<Match>());
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        List<Match> result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(true, result.isEmpty());
        verifyAll();
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a list of matches when we provide a valid URI, profile URI and
     * set the subject and target flag
     */
    @Test
    public void testGetMappings_uriAndProfileTrueSubjectAndTarget() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = new URI("http://something.org");
        URI profileUri = new URI("http://profile.com");
        Boolean isSubject = true;
        Boolean isTarget = true;
        Integer limit = null;
        expect(mockIRS.getMappingsWithURI(uri, profileUri, null))
                .andReturn(new ArrayList<Match>());
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        List<Match> result = instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
        assertEquals(true, result.isEmpty());
        verifyAll();
    }

    /**
     * Test of getMappings method, of class MappingResource.
     * Expect a 400 status code when both subject and target are set to false.
     * Either both should not be set or at least one of them set to true.
     */
    @Test(expected=IRSException.class)
    public void testGetMappings_uriAndProfileFalseSubjectAndTarget() throws Exception {
        IRS mockIRS = createMock(IRS.class);
        URI uri = new URI("http://something.org");
        URI profileUri = new URI("http://profile.com");
        Boolean isSubject = false;
        Boolean isTarget = false;
        Integer limit = null;
        MappingResource instance = new MappingResource(mockIRS);
        instance.getMappings(uri, profileUri, isSubject, isTarget, limit);
    }

    /***************************************************************************
     * 
     * Below are tests for the GetMappingDetails method
     * 
     **************************************************************************/
    
    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect an error when we provide no id
     */
    @Test(expected=IllegalArgumentException.class)
    public void testGetMappingDetails_nullID() throws IRSException {
        IRS mockIRS = createMock(IRS.class);
        Integer mappingId = null;
        MappingResource instance = new MappingResource(mockIRS);
        instance.getMappingDetails(mappingId);
    }
    
    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect an error when we provide an id of 0
     */
    @Test(expected=IRSException.class)
    public void testGetMappingDetails_zeroID() throws IRSException {
        IRS mockIRS = createMock(IRS.class);
        expect(mockIRS.getMappingDetails(0)).andThrow(new IRSException(""));
        replayAll();
        Integer mappingId = 0;
        MappingResource instance = new MappingResource(mockIRS);
        instance.getMappingDetails(mappingId);
        verifyAll();
    }
    
    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect an error when we provide an id that is not in the db
     */
    @Test(expected=IRSException.class)
    public void testGetMappingDetails_unusedID() throws IRSException {
        IRS mockIRS = createMock(IRS.class);
        Integer mappingId = 1038470;
        expect(mockIRS.getMappingDetails(mappingId)).andThrow(new IRSException(""));
        replayAll();
        MappingResource instance = new MappingResource(mockIRS);
        instance.getMappingDetails(mappingId);
        verifyAll();
    }

    /**
     * Test of getMappingDetails method, of class MappingResource.
     * Expect a Mapping back when passing in an integer id for which 
     * there is an entry
     */
    @Test
    public void testGetMappingDetails_validID() 
            throws IRSException, URISyntaxException {
        IRS mockIRS = createMock(IRS.class);
        final Mapping expectedMapping = new Mapping();
        expectedMapping.setId(42);
        expect(mockIRS.getMappingDetails(42)).andReturn(expectedMapping);
        replayAll();
        Integer mappingId = 42;
        MappingResource instance = new MappingResource(mockIRS);
        Mapping result = instance.getMappingDetails(mappingId);
        assertEquals(new URI(MAPPING_NAMESPACE + 42), result.getId());
        verifyAll();
    }
    
}
