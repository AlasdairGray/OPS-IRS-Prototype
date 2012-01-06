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
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.rio.RDFHandlerException;
import uk.ac.manchester.cs.irs.IRSException;

/**
 *
 */
public class LinksetInserterTest {

    private static class DummyDBAccess extends MySQLAccess {

        public DummyDBAccess() throws IRSException {
        }
    }
    
    public LinksetInserterTest() {
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
     * Test of getDatasets method, of class LinksetInserter.
     */
    @Ignore
    @Test
    public void testGetDatasets() {
        System.out.println("getDatasets");
        LinksetInserter instance = null;
        List expResult = null;
        List result = instance.getDatasets();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test that when there is a single dataset statement it gets stored locally
     */
    @Test
    public void testHandleStatement_singleDatasetSt() 
            throws IRSException, RDFHandlerException {
        MySQLAccess dummyDatabase = new DummyDBAccess();
        System.out.println("handle single dataset statement");
        URI subject = new URIImpl("http://example.com");
        URI predicate = new URIImpl(RdfConstants.TYPE);
        URI object = new URIImpl(VoidConstants.DATASET);
        Statement st = new StatementImpl(subject, predicate, object);
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        instance.handleStatement(st);
        final List<String> datasets = instance.getDatasets();
        assertEquals(1, datasets.size());
        String datasetUri = datasets.get(0);
        assertEquals("http://example.com", datasetUri);
    }

    /**
     * Test that when there are two dataset statements they get stored locally
     */
    @Test
    public void testHandleStatement_twoDatasetSt() 
            throws IRSException, RDFHandlerException {
        MySQLAccess dummyDatabase = new DummyDBAccess();
        System.out.println("handle two dataset statement");
        URI subject = new URIImpl("http://example.com");
        URI predicate = new URIImpl(RdfConstants.TYPE);
        URI object = new URIImpl(VoidConstants.DATASET);
        Statement st = new StatementImpl(subject, predicate, object);
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        instance.handleStatement(st);
        subject = new URIImpl("http://example.org");
        st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        final List<String> datasets = instance.getDatasets();
        assertEquals(2, datasets.size());
        assertEquals("http://example.com", datasets.get(0));
        assertEquals("http://example.org", datasets.get(1));
    }

    /**
     * Test that when there are three dataset statements then an exception is thrown
     */
    @Test(expected=RDFHandlerException.class)
    public void testHandleStatement_threeDatasetSt() 
            throws IRSException, RDFHandlerException {
        MySQLAccess dummyDatabase = new DummyDBAccess();
        System.out.println("handle three dataset statement");
        URI subject = new URIImpl("http://example.com");
        URI predicate = new URIImpl(RdfConstants.TYPE);
        URI object = new URIImpl(VoidConstants.DATASET);
        Statement st = new StatementImpl(subject, predicate, object);
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        instance.handleStatement(st);
        subject = new URIImpl("http://example.org");
        st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        subject = new URIImpl("http://example.co.uk");
        st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
    }

    /**
     * Test of getNumberLinksInserted method, of class LinksetInserter.
     */
    @Test@Ignore
    public void testGetNumberLinksInserted() {
        System.out.println("getNumberLinksInserted");
        LinksetInserter instance = null;
        int expResult = 0;
        int result = instance.getNumberLinksInserted();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
