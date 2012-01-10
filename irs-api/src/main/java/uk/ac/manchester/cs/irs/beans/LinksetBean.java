package uk.ac.manchester.cs.irs.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement(name="linkset")
public class LinksetBean {
 
    private String linksetUri;
    private String creator;
    private String dateCreated;
    private String subjectsTarget;
    private String linkPredicate;
    private String objectsTarget;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLinkPredicate() {
        return linkPredicate;
    }

    public void setLinkPredicate(String linkPredicate) {
        this.linkPredicate = linkPredicate;
    }

    public String getLinksetUri() {
        return linksetUri;
    }

    public void setLinksetUri(String linksetUri) {
        this.linksetUri = linksetUri;
    }

    public String getObjectsTarget() {
        return objectsTarget;
    }

    public void setObjectsTarget(String objectsTarget) {
        this.objectsTarget = objectsTarget;
    }

    public String getSubjectsTarget() {
        return subjectsTarget;
    }

    public void setSubjectsTarget(String subjectsTarget) {
        this.subjectsTarget = subjectsTarget;
    }
    
}
