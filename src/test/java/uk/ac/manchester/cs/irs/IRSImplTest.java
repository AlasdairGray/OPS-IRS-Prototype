package uk.ac.manchester.cs.irs;

import java.net.URI;
import java.util.List;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
     */
    @Test(expected=IRSException.class)
    public void testGetMappingsWithSubject_allNull() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        System.out.println("getMappingsWithSubject");
        URI termURI = null;
        URI profile = null;
        Integer limit = null;
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        List expResult = null;
        instance.getMappingsWithSubject(termURI, profile, limit);
    }

    /**
     * Test of getMappingsWithSubject method, of class IRSImpl.
     */
    @Test(expected=IRSException.class)
    public void testGetMappingsWithSubject_nullSubject() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        System.out.println("getMappingsWithSubject");
        URI termURI = null;
        URI profile = null;
        Integer limit = null;
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        List expResult = null;
        instance.getMappingsWithSubject(termURI, profile, limit);
    }

    /**
     * Test of getMappingsWithSubject method, of class IRSImpl.
     */
    @Test
    public void testGetMappingsWithSubject() throws Exception {
        final MySQLAccess mockDBAccess = createMock(MySQLAccess.class);
        System.out.println("getMappingsWithSubject");
        URI termURI = null;
        URI profile = null;
        Integer limit = null;
        IRS instance = new IRSImpl() {
                protected MySQLAccess instantiateDBAccess() throws IRSException {
                    return mockDBAccess;
                }
        };
        List expResult = null;
        List result = instance.getMappingsWithSubject(termURI, profile, limit);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMappingsWithTarget method, of class IRSImpl.
     */
    @Test
    public void testGetMappingsWithTarget() throws Exception {
        System.out.println("getMappingsWithTarget");
        URI termURI = null;
        URI profile = null;
        Integer limit = null;
        IRSImpl instance = new IRSImpl();
        List expResult = null;
        List result = instance.getMappingsWithTarget(termURI, profile, limit);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMappingsWithURI method, of class IRSImpl.
     */
    @Test
    public void testGetMappingsWithURI() throws Exception {
        System.out.println("getMappingsWithURI");
        URI termURI = null;
        URI profile = null;
        Integer limit = null;
        IRSImpl instance = new IRSImpl();
        List expResult = null;
        List result = instance.getMappingsWithURI(termURI, profile, limit);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMappingDetails method, of class IRSImpl.
     */
    @Test
    public void testGetMappingDetails() throws Exception {
        System.out.println("getMappingDetails");
        int mappingId = 0;
        IRSImpl instance = new IRSImpl();
        Mapping expResult = null;
        Mapping result = instance.getMappingDetails(mappingId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
