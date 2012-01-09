package uk.ac.manchester.cs.irs.beans;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

/**
 *
 */
public class LinksetMetadata {

    private URI subjectTarget;
    private URI objectTarget;
    private URI linkPredicate;
    private Literal dateCreated;
    private Value creator;
    
    public LinksetMetadata() {
        
    }
    
    public LinksetMetadata(URI subjectTarget, URI objectTarget, URI linkPredicate,
            Literal dateCreated, Value creator) {
        this.subjectTarget = subjectTarget;
        this.objectTarget = objectTarget;
        this.linkPredicate = linkPredicate;
        this.dateCreated = dateCreated;
        this.creator = creator;
    }

    public Value getCreator() {
        return creator;
    }

    public void setCreator(Value creator) {
        this.creator = creator;
    }

    public Literal getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Literal dateCreated) {
        this.dateCreated = dateCreated;
    }

    public URI getLinkPredicate() {
        return linkPredicate;
    }

    public void setLinkPredicate(URI linkPredicate) {
        this.linkPredicate = linkPredicate;
    }

    public URI getObjectTarget() {
        return objectTarget;
    }

    public void setObjectTarget(URI objectTarget) {
        this.objectTarget = objectTarget;
    }

    public URI getSubjectTarget() {
        return subjectTarget;
    }

    public void setSubjectTarget(URI subjectTarget) {
        this.subjectTarget = subjectTarget;
    }

}
