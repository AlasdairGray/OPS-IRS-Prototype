package uk.ac.manchester.cs.irs.beans;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

//TODO: Hide the linkset ID but leave it available for internal use but have URI available for external display
//@XmlTransient is not working :(
@XmlRootElement(name="mapping")
public class Mapping {
    private String id;
    @XmlTransient
    private int linksetId;
    private String linksetUri;
    private String source;
    private String predicate;
    private String target;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public int getLinksetId() {
        return linksetId;
    }
    
    public void setLinksetId(int linksetId) {
        this.linksetId = linksetId;
    }

    public String getLinksetUri() {
        return linksetUri;
    }

    public void setLinksetUri(String linksetUri) {
        this.linksetUri = linksetUri;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public String getPredicate() {
        return predicate;
    }
    
    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }
    
    public String getTarget() {
        return target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
    
}
