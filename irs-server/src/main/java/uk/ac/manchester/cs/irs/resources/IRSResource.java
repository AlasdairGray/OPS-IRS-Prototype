package uk.ac.manchester.cs.irs.resources;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import uk.ac.manchester.cs.irs.IRS;
import uk.ac.manchester.cs.irs.IRSException;
import uk.ac.manchester.cs.irs.IRSImpl;
import uk.ac.manchester.cs.irs.beans.ServerStatistics;

@Path("/")
public class IRSResource {
    private static IRS irs = null;

    public IRSResource() {

    }
    
    /**
     * Constructor used to enable mocking for unit testing
     * 
     * @param IRS instance of the IRS to use 
     */
    protected IRSResource(IRS irs) {
        this.irs = irs;
    }

    @Context 
    private UriInfo uriInfo;

    static {
        try {
            irs = new IRSImpl();
        } catch (IRSException ex) {
            String msg = "Cannot initialise IRS service";
            Logger.getLogger(IRSResource.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response welcomeMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\"?>");
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
        sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"/>");
        sb.append("<head><title>OPR IRS</title></head><body>");
        sb.append("<h1>Open PHACTS Identity Resolution Service</h1>");
        sb.append("<p>Welcome to the prototype Identity Resolution Service. ");
        try {
            ServerStatistics serverStats = irs.getStatistics();
            sb.append("The service contains ")
                    .append(serverStats.getNumberMappings())
                    .append(" mapping links from ")
                    .append(serverStats.getNumberLinksets())
                    .append(" linksets.</p>");
        } catch (IRSException ex) {
            sb.append("</p>");
        }
        sb.append("<p>The service provides a single method to retrieve all known "
                + "equivalent URIs for a given URI. The method has the following form:");
        sb.append("<ul>");
        sb.append("<li>getMappings");
        sb.append("<ul>");
        sb.append("<li>Required arguements:<ul>");
        sb.append("<li>uri as string</li>");
        sb.append("</ul></li>");
        sb.append("<li>Optional arguments<ul>");
        sb.append("<li>Profile uri as string (not yet implemented)</li>");
        sb.append("<li>Limit on number of matches (not yet implemented)</li>");
        sb.append("</ul></li>");
        sb.append("</ul></p>");
        sb.append("</ul></p>");        
        sb.append("<p>Some example calls are:");
        sb.append("<ul>");
        sb.append("<li><a href=\"").append(uriInfo.getBaseUri())
                .append("getMappings?uri=http://brenda-enzymes.info/1.1.1.1\">")
                .append(uriInfo.getBaseUri())
                .append("getMappings?uri=http://brenda-enzymes.info/1.1.1.1</a></li>");
        sb.append("<li><a href=\"").append(uriInfo.getBaseUri())
                .append("getMappings?uri=http://rdf.chemspider.com/2157\">")
                .append(uriInfo.getBaseUri())
                .append("getMappings?uri=http://rdf.chemspider.com/2157</a></li>");
        sb.append("</ul></p>");
        sb.append("<p>Some sample resources are:");
        sb.append("<ul>");
        sb.append("<li><a href=\"").append(uriInfo.getBaseUri()).append("mapping/1\">Mapping 1</a></li>");
        sb.append("<li><a href=\"").append(uriInfo.getBaseUri()).append("mapping/65283\">Mapping 65283</a></li>");
        sb.append("</ul></p>");
        sb.append("</body></html>");
        return Response.ok(sb.toString(), MediaType.TEXT_HTML).build();
    }

}
