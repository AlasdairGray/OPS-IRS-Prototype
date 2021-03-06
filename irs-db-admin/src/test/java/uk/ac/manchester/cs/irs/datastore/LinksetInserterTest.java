/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.manchester.cs.irs.datastore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.turtle.TurtleParser;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.beans.LinksetBean;
import uk.ac.manchester.cs.irs.beans.LinksetMetadata;
import uk.ac.manchester.cs.irs.beans.Mapping;
import uk.ac.manchester.cs.irs.beans.Match;

/**
 *
 */
public class LinksetInserterTest {

    private static class DummyDBAccess implements DBAccess {

        public DummyDBAccess() throws IRSException {
        }

        @Override
        public Mapping getMappingDetails(int mappingId) throws IRSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<Match> getMappingsWithURI(String uri, int limit) throws IRSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int insertLinksetMetadata(LinksetMetadata metadata) {
            return 100;
        }
        
        @Override
        public void insertLink(Mapping link) throws IRSException {
        }
        
        @Override
        public void insertLinkCollection(Collection<Mapping> link) throws IRSException {
        }

        @Override
        public int getNumberMappings() throws IRSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getNumberLinksets() throws IRSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public LinksetBean getLinksetDetails(int linksetId) throws IRSException {
            throw new UnsupportedOperationException("Not supported yet.");
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
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com");
        URI predicate = new URIImpl(RdfConstants.TYPE);
        URI object = new URIImpl(VoidConstants.DATASET);
        Statement st = new StatementImpl(subject, predicate, object);
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
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com");
        URI predicate = new URIImpl(RdfConstants.TYPE);
        URI object = new URIImpl(VoidConstants.DATASET);
        Statement st = new StatementImpl(subject, predicate, object);
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
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com");
        URI predicate = new URIImpl(RdfConstants.TYPE);
        URI object = new URIImpl(VoidConstants.DATASET);
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        subject = new URIImpl("http://example.org");
        st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        subject = new URIImpl("http://example.co.uk");
        st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
    }

    /**
     * Verify that a void:subjectsTarget gets stored locally
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test
    public void testHandleStatement_voidSubjectsTarget() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = new URIImpl(VoidConstants.SUBJECTSTARGET);
        URI object = new URIImpl("http://example.com");
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        assertEquals(object, instance.subjectTarget);
    }

    /**
     * Verify that a void:objectsTarget gets stored locally
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test
    public void testHandleStatement_voidObjectsTarget() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = new URIImpl(VoidConstants.OBJECTSTARGET);
        URI object = new URIImpl("http://example.com");
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        assertEquals(object, instance.objectTarget);
    }
    
    /**
     * Test that we can handle linksets that have two targets declared rather 
     * than the subclasses.
     * First is expected to be the subjectsTarget while the second is interpreted
     * as the objectsTarget.
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test
    public void testHandleStatement_voidTwoTargets() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = new URIImpl(VoidConstants.TARGET);
        URI object = new URIImpl("http://example.com/subject");
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        URI object2 = new URIImpl("http://example.com/object");
        st = new StatementImpl(subject, predicate, object2);
        instance.handleStatement(st);
        assertEquals(object, instance.subjectTarget);
        assertEquals(object2, instance.objectTarget);
    } 
    
    /**
     * Test that we give up linksets with three targets declared.
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test(expected=RDFHandlerException.class)
    public void testHandleStatement_voidThreeTargets() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = new URIImpl(VoidConstants.TARGET);
        URI object = new URIImpl("http://example.com/subject");
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        URI object2 = new URIImpl("http://example.com/object");
        st = new StatementImpl(subject, predicate, object2);
        instance.handleStatement(st);
        instance.handleStatement(st);
    }

    /**
     * Verify that a void:subset gets stored locally
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test
    public void testHandleStatement_voidSubset() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = new URIImpl(VoidConstants.SUBSET);
        URI object = new URIImpl("http://example.com");
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        assertEquals(object, instance.subset);
    }

    /**
     * Verify that an exception is thrown if more than one subset declaration
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test(expected=RDFHandlerException.class)
    public void testHandleStatement_voidMultipleSubset() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = new URIImpl(VoidConstants.SUBSET);
        URI object = new URIImpl("http://example.com");
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        instance.handleStatement(st);
    }

    /**
     * Verify that a void:linkPredicate gets stored locally
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test
    public void testHandleStatement_voidLinkPredicate() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = new URIImpl(VoidConstants.LINK_PREDICATE);
        URI object = new URIImpl("http://example.com");
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        assertEquals(object, instance.linkPredicate);
    }

    /**
     * Verify that an exception is thrown if more than one linkPredicate declaration
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test(expected=RDFHandlerException.class)
    public void testHandleStatement_voidMultipleLinkPredicates() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = new URIImpl(VoidConstants.LINK_PREDICATE);
        URI object = new URIImpl("http://example.com");
        Statement st = new StatementImpl(subject, predicate, object);
        instance.handleStatement(st);
        instance.handleStatement(st);
    }

    /**
     * Verify that a dcterms:created gets stored locally
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test
    public void testHandleStatement_dctermsCreated() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = DctermsConstants.CREATED;
        ValueFactory valueFactory = new ValueFactoryImpl();
        Literal date = valueFactory.createLiteral("2012-01-06", new URIImpl("http://www.w3.org/2001/XMLSchema#date"));
        Statement st = new StatementImpl(subject, predicate, date);
        instance.handleStatement(st);
        assertEquals(date, instance.dateCreated);
    }

    /**
     * Verify that an exception is thrown if more than one created date declaration
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test(expected=RDFHandlerException.class)
    public void testHandleStatement_dctermsMultipleCreated() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = DctermsConstants.CREATED;
        ValueFactory valueFactory = new ValueFactoryImpl();
        Literal date = valueFactory.createLiteral("2012-01-06", new URIImpl("http://www.w3.org/2001/XMLSchema#date"));
        Statement st = new StatementImpl(subject, predicate, date);
        instance.handleStatement(st);
        instance.handleStatement(st);
    }

    /**
     * Verify that a dcterms:creator gets stored locally when it is a URI
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test
    public void testHandleStatement_dctermsCreatorURI() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = DctermsConstants.CREATOR;
        URI creator = new URIImpl("http://example.org/someone");
        Statement st = new StatementImpl(subject, predicate, creator);
        instance.handleStatement(st);
        assertEquals(creator, instance.creator);
    }

    /**
     * Verify that a dcterms:creator gets stored locally when it is a literal
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test
    public void testHandleStatement_dctermsCreatorLiteral() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = DctermsConstants.CREATOR;
        ValueFactory valueFactory = new ValueFactoryImpl();
        Literal creator = valueFactory.createLiteral("someones name");
        Statement st = new StatementImpl(subject, predicate, creator);
        instance.handleStatement(st);
        assertEquals(creator, instance.creator);
    }

    /**
     * Verify that an exception is thrown if more than one creator declaration
     * 
     * @throws IRSException
     * @throws RDFHandlerException 
     */
    @Test(expected=RDFHandlerException.class)
    public void testHandleStatement_dctermsMultipleCreator() throws IRSException, RDFHandlerException {
        DBAccess dummyDatabase = new DummyDBAccess();
        LinksetInserter instance = new LinksetInserter(dummyDatabase);
        URI subject = new URIImpl("http://example.com/linkset");
        URI predicate = DctermsConstants.CREATOR;
        URI creator = new URIImpl("http://example.com/someone");
        Statement st = new StatementImpl(subject, predicate, creator);
        instance.handleStatement(st);
        instance.handleStatement(st);
    }
    
    @Test
    public void testLinksetFile() 
            throws IRSException, FileNotFoundException, IOException, 
            RDFParseException, RDFHandlerException, URISyntaxException {
        DBAccess dummyDatabase = new DummyDBAccess();
        URL fileUrl = LinksetInserterTest.class.getClassLoader()
                .getResource("linksets/test-linkset.ttl");
        File file = new File(fileUrl.toURI());
        FileReader fileReader = new FileReader(file);
        RDFParser rdfParser = new TurtleParser();
        LinksetInserter inserter = new LinksetInserter(dummyDatabase);
        rdfParser.setRDFHandler(inserter);
        rdfParser.parse(fileReader, "http://example.org");

        //Check that the behaviour is as expected
        assertEquals(2, inserter.getDatasets().size());
        assertEquals(new URIImpl("http://www.example.org"), inserter.creator);
        ValueFactory valueFactory = new ValueFactoryImpl();
        Literal date = valueFactory.createLiteral("2012-01-09", new URIImpl("http://www.w3.org/2001/XMLSchema#date"));       
        assertEquals(date, inserter.dateCreated);
        assertEquals(new URIImpl("http://www.w3.org/2004/02/skos/core#exactMatch"), inserter.linkPredicate);
        assertEquals(100, inserter.linksetId);
        assertEquals(new URIImpl("http://example.org#dataset2"), inserter.objectTarget);
        assertEquals(new URIImpl("http://example.org#dataset1"), inserter.subjectTarget);
        assertEquals(new URIImpl("http://example.org#dataset1"), inserter.subset);
        assertEquals(false, inserter.processingHeader);
        assertEquals(8, inserter.getNumberLinksInserted());
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
