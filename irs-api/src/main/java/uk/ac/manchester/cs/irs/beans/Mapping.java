package uk.ac.manchester.cs.irs.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mapping")
public class Mapping {
    private String id;
    private int linksetId;
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
