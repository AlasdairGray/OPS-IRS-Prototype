package uk.ac.manchester.cs.irs.beans;

/**
 *
 */
public class ServerStatistics {

    private int numberLinksets;
    private int numberMappings;
    
    public ServerStatistics() {        
    }
    
    public ServerStatistics(int numberLinksets, int numberMappings) {
        this.numberLinksets = numberLinksets;
        this.numberMappings = numberMappings;
    }

    public int getNumberLinksets() {
        return numberLinksets;
    }

    public void setNumberLinksets(int numberLinksets) {
        this.numberLinksets = numberLinksets;
    }

    public int getNumberMappings() {
        return numberMappings;
    }

    public void setNumberMappings(int numberMappings) {
        this.numberMappings = numberMappings;
    }
    
}
