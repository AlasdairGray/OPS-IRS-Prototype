package uk.ac.manchester.cs.irs.beans;

import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="match")
@XmlAccessorType(XmlAccessType.FIELD)
public class Match {
    
    private String MAPPING_NAMESPACE = "http://ondex2.cs.man.ac.uk/irs/";
    
    @XmlElement
    private URI id;
    
    @XmlElement
    private URI matchUri;
    
    public URI getId() {
        return id;
    }
    
    public void setId(int id) throws URISyntaxException {
        this.id = new URI(MAPPING_NAMESPACE + id);
    }
    
    public URI getMatchUri() {
        return matchUri;
    }
    
    public void setMatchUri(URI matchUri) {
        this.matchUri = matchUri;
    }

    @Override
    public String toString() {
        return String.format("{id=%s,matchUri=%s}", 
                id, matchUri);
    }
    
}
