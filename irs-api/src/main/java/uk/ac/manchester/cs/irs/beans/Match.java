package uk.ac.manchester.cs.irs.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="match")
@XmlAccessorType(XmlAccessType.FIELD)
public class Match {
    
    @XmlElement(name="MappingURI")
    private String mappingUri;
    
    @XmlElement(name="MatchingURI")
    private String matchUri;
    
    public String getId() {
        return mappingUri;
    }
    
    public void setId(String mappingUri) {
        this.mappingUri = mappingUri;
    }
    
    public String getMatchUri() {
        return matchUri;
    }
    
    public void setMatchUri(String matchUri) {
        this.matchUri = matchUri;
    }

    @Override
    public String toString() {
        return String.format("{id=%s,matchUri=%s}", 
                mappingUri, matchUri);
    }
    
}
