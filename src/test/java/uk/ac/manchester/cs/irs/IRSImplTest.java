package uk.ac.manchester.cs.irs;

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
import org.junit.Ignore;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.datastore.MySQLAccess;

/**
 *
 */
public class IRSImplTest extends EasyMockSupport {
    
    public IRSImplTest() {
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
     * Test of getMappingsWithSubject method, of class IRSImpl.
     * When all parameters are null, we expect to receive an exception
     */
    @Test(expected=IRSException.class)
    public void testGetMappingsWithSubject_allNull() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        URI termURI = null;
        URI profile = null;
        Integer limit = null;
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        instance.getMappingsWithSubject(termURI, profile, limit);
    }

    /**
     * Test of getMappingsWithSubject method, of class IRSImpl.
     * When the termURI is null we expect to receive an exception
     */
    @Test(expected=IRSException.class)
    public void testGetMappingsWithSubject_nullSubject() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        URI termURI = null;
        URI profile = new URI("http://profile.com");
        Integer limit = new Integer(4);
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        instance.getMappingsWithSubject(termURI, profile, limit);
    }

    /**
     * Test of getMappingsWithSubject method, of class IRSImpl.
     */
    @Test@Ignore
    public void testGetMappingsWithSubject_subjectNullNull() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        URI termURI = new URI("http://example.com");
        URI profile = null;
        Integer limit = null;

        expect(mockDBAccess.getMappings(termURI)).andReturn(new ArrayList<Mapping>());
        
        replayAll();
        
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        List result = instance.getMappingsWithSubject(termURI, profile, limit);
        assertEquals(true, result.isEmpty());
        verifyAll();
    }

    /**
     * Test of getMappingsWithTarget method, of class IRSImpl.
     */
    @Test@Ignore
    public void testGetMappingsWithTarget() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        System.out.println("getMappingsWithTarget");
        URI termURI = null;
        URI profile = null;
        Integer limit = null;

        replayAll();
        
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };

        List expResult = null;
        List result = instance.getMappingsWithTarget(termURI, profile, limit);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        verifyAll();
    }

    /**************************************************************************
     * 
     * Tests for getMappingWithURI
     * 
     *************************************************************************/
    
    /**
     * Test of getMappingsWithURI method, of class IRSImpl.
     * 
     * Expect an exception when a null URI is passed in
     */
    @Test(expected=IRSException.class)
    public void testGetMappingsWithURI_nullURI() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        URI termURI = null;
        URI profile = null;
        Integer limit = null;

        replayAll();
        
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        instance.getMappingsWithURI(termURI, profile, limit);
        verifyAll();
    }

    /**************************************************************************
     * 
     * Tests for getMappingDetails
     * 
     *************************************************************************/
    
    /**
     * Test of getMappingDetails method, of class IRSImpl.
     * 
     * Zero is not a valid mapping identifier
     */
    @Test(expected=IRSException.class)
    public void testGetMappingDetails_zeroId() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        int mappingId = 0;
        expect(mockDBAccess.getMappingDetails(mappingId)).andThrow(new IRSException(""));
        replayAll();
        
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        instance.getMappingDetails(mappingId);
        verifyAll();
    }

    /**
     * Test of getMappingDetails method, of class IRSImpl.
     * 
     * The mapping identifier does not exist
     */
    @Test(expected=IRSException.class)
    public void testGetMappingDetails_invalidId() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        int mappingId = 52;
        expect(mockDBAccess.getMappingDetails(mappingId)).andThrow(new IRSException(""));
        replayAll();
        
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        instance.getMappingDetails(mappingId);
        verifyAll();
    }

    /**
     * Test of getMappingDetails method, of class IRSImpl.
     * 
     * Mapping identifier exists so should return a valid mapping
     */
    @Test
    public void testGetMappingDetails_validId() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        Mapping mockMapping = createMock(Mapping.class);
        int mappingId = 32;
        expect(mockDBAccess.getMappingDetails(mappingId)).andReturn(mockMapping);
        expect(mockMapping.getId()).andReturn(mappingId);
        replayAll();
        
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        Mapping result = instance.getMappingDetails(mappingId);
        assertEquals(mappingId, result.getId());
        verifyAll();
    }

}
