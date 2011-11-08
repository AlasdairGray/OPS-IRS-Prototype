package uk.ac.manchester.cs.irs.beans;

import java.net.URI;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mapping")
public class Mapping {
    private int id;
    private URI source;
    private URI predicate;
    private URI target;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public URI getSource() {
        return source;
    }
    
    public void setSource(URI source) {
        this.source = source;
    }
    
    public URI getPredicate() {
        return predicate;
    }
    
    public void setPredicate(URI predicate) {
        this.predicate = predicate;
    }
    
    public URI getTarget() {
        return target;
    }
    
    public void setTarget(URI target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return String.format("{id=%s,source=%s,predicate=%s,target=%s}", 
                id, source, predicate, target);
    }
    
}
