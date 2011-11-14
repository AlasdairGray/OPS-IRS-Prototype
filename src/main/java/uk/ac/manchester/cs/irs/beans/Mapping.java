package uk.ac.manchester.cs.irs.beans;

import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mapping")
public class Mapping {
    private String MAPPING_NAMESPACE = "http://ondex2.cs.man.ac.uk/irs/";
    private URI id;
    private URI source;
    private URI predicate;
    private URI target;
    
    public URI getId() {
        return id;
    }
    
    public void setId(int id) throws URISyntaxException {
        this.id = new URI(MAPPING_NAMESPACE + id);
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
