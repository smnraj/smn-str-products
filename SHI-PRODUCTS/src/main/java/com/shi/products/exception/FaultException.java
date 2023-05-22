/**
 * 
 */
package com.shi.products.exception;

/**
 * @author suman.raju
 *
 */
public class FaultException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FaultException() {
        super();
    }

    public FaultException(String message) {
        super(message);
    }

}
