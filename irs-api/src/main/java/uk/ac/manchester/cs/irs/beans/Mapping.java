package uk.ac.manchester.cs.irs.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mapping")
public class Mapping {
    private String id;
    private String source;
    private String predicate;
    private String target;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
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

//    @Override
//    public String toString() {
//        return String.format("{id=%s,source=%s,predicate=%s,target=%s}", 
//                id, source, predicate, target);
//    }
    
}
