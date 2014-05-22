/**
 *
 */
package org.imagopole.ppms.api;

/**
 * Runtime exception for all error conditions originating
 * from underlying components (eg. HTTP library exceptions) or invalid API calls.
 *
 * @author seb
 *
 */
public class PumapiException extends RuntimeException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Vanilla constructor.
     */
    public PumapiException() {
        super();
    }

    /**
     * Parameterized constructor.
     *
     * @param message exception message
     */
    public PumapiException(String message) {
        super(message);
    }

    /**
     * Parameterized constructor.
     *
     * @param cause root cause
     */
    public PumapiException(Throwable cause) {
        super(cause);
    }

    /**
     * Parameterized constructor.
     *
     * @param message exception message
     * @param cause root cause
     */
    public PumapiException(String message, Throwable cause) {
        super(message, cause);
    }

}
