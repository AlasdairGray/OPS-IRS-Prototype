/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.manchester.cs.irs;

/**
 *
 * @author agray
 */
public class IRSException extends Exception {

    public IRSException(String msg) {
        super(msg);
    }
    
    public IRSException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
