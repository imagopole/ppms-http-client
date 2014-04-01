package org.imagopole.ppms.api;


/**
 * Bridge between PUMAPI request objects and a remote PPMS service.
 *
 * Handles request mappping from PUMAPI to the underlying representation,
 * remote service invocation and response introspection for API error handling.
 *
 * @author seb
 *
 */
public interface PumapiHttpInvoker {

    /**
     * Default PUMAPI HTTP POST request invocation with "raw" buffered result.
     *
     * @param url the PUMAPI endpoint URL
     * @param pumapiRequest the PUMAPI request
     * @return the response body as String
     */
    String executePost(String url, PumapiRequest pumapiRequest);

}
