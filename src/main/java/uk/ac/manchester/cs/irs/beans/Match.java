package uk.ac.manchester.cs.irs.beans;

import java.net.URI;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="match")
@XmlAccessorType(XmlAccessType.FIELD)
public class Match {
    
    @XmlElement(name="Mapping URI")
    private URI mappingUri;
    
    @XmlElement(name="Matching URI")
    private URI matchUri;
    
    public URI getId() {
        return mappingUri;
    }
    
    public void setId(URI mappingUri) {
        this.mappingUri = mappingUri;
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
                mappingUri, matchUri);
    }
    
}
