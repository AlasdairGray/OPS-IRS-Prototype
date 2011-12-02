package uk.ac.manchester.cs.irs;

/**
 *
 */
public class IRSException extends Exception {

    public IRSException(String msg) {
        super(msg);
    }
    
    public IRSException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
