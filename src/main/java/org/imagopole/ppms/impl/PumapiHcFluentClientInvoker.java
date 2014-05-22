/**
 *
 */
package org.imagopole.ppms.impl;

import static org.imagopole.ppms.util.PumapiUtil.trimEol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import org.imagopole.ppms.api.PumapiException;
import org.imagopole.ppms.api.PumapiHttpInvoker;
import org.imagopole.ppms.api.PumapiRequest;
import org.imagopole.ppms.api.config.PumapiConfig;
import org.imagopole.ppms.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation for all PUMAPI calls:
 * - uses Apache HttpComponent's fluent HttpClient
 * - buffers all responses in memory
 * - converts checked exceptions to runtime PumapiExceptions
 *
 * Note: the underlying fluent HttpClient uses an
 * {@link org.apache.http.impl.conn.PoolingHttpClientConnectionManager} by default,
 * with a maximum of 100 connections per route and 200 maximum total number of connections.
 *
 * @author seb
 *
 * @see http://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html#d5e361
 * @see org.apache.http.client.fluent.Executor
 */
public class PumapiHcFluentClientInvoker implements PumapiHttpInvoker {

    /** PUMAPI response body token for error conditions detection. */
    private static final String ERROR_UPPERCASE = "ERROR";

    /** PUMAPI response body token for error conditions detection. */
    private static final String ERROR_LOWERCASE = "error";

    /** Application logs. */
    private final Logger log = LoggerFactory.getLogger(PumapiHcFluentClientInvoker.class);

    /**
     * Vanilla constructor.
     */
    public PumapiHcFluentClientInvoker() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String executePost(String url, PumapiRequest pumapiRequest) throws PumapiException {
        Check.notEmpty(url, "url");
        Check.notNull(pumapiRequest, "pumapiRequest");

        return getResponseBodyAsString(toHttpPostRequest(url, pumapiRequest));
    }

    /**
     * Executes an HttpComponents HTTP request using the default (buffering)
     * <code>ResponseHandler</code> to convert the response to String.
     *
     * Inspects the response for API errors before returning it to the caller,
     * hence may throw a <code>PumapiException</code>.
     *
     * @param request the fluent client HTTP request
     * @return the response content
     * @throws PumapiException in case of an underlying failure (ie. originating
     * from the underlying HTTP client), or from an invalid API invocation.
     * @see org.apache.http.client.ResponseHandler
     */
    private String getResponseBodyAsString(final Request request) throws PumapiException {
        Response response = null;
        String result = null;

        try {
            response = request.execute();
            result = response.returnContent().asString();
        } catch (ClientProtocolException cpe) {
            log.error("[pumapi] Failed pumapi invocation [protocol]", cpe);
            throw new PumapiException(cpe.getMessage(), cpe);
        } catch (IOException ioe) {
            log.error("[pumapi] Failed pumapi invocation [io]", ioe);
            throw new PumapiException(ioe.getMessage(), ioe);
        }

        // HTTP status OK, now still need to parse
        // the PUMAPI response body for error detection
        checkPumapiStatusOrFail(result);

        return result;
    }

    /**
     * Converts a PUMAPI request to an HttpComponents fluent client HTTP request.
     *
     * @param url the API endpoint URL
     * @param pumapiRequest the PUMAPI request
     * @return the fluent client HTTP request
     */
    private Request toHttpPostRequest(String url, PumapiRequest pumapiRequest) {
        List<NameValuePair> requestParams = toHttpParams(pumapiRequest.getParameterMap());
        PumapiConfig clientConfig = pumapiRequest.getClientConfig();

        // build HTTP request
        Request httpRequest = Request.Post(url).bodyForm(requestParams);

        // apply environment settings
        configureHttpRequest(httpRequest, clientConfig);

        return httpRequest;
    }

    private void configureHttpRequest(final Request httpRequest, final PumapiConfig clientConfig) {
        // with non-configurable settings (always check the connection has
        // not been closed by the server)
        httpRequest.staleConnectionCheck(true);

        // plus proxy settings
        String proxyHost = clientConfig.getProxyHost();
        Integer proxyPort = clientConfig.getProxyPort();
        if (null != proxyHost && null != proxyPort
            && !proxyHost.isEmpty() && proxyPort > 0) {
            httpRequest.viaProxy(new HttpHost(proxyHost, proxyPort));
            log.trace("[pumapi] Routing request trough proxy at: {}:{}", proxyHost, proxyPort);
        }

        // and socket timeouts
        Integer connectTimeout = clientConfig.getConnectTimeout();
        if (null != connectTimeout) {
            httpRequest.connectTimeout(connectTimeout);
            log.trace("[pumapi] Setting request connect timeout to {} ms", connectTimeout);
        }

        Integer readTimeout = clientConfig.getSocketTimeout();
        if (null != readTimeout) {
            httpRequest.socketTimeout(readTimeout);
            log.trace("[pumapi] Setting socket read timeout to {} ms", readTimeout);
        }
    }

    /**
     * Converts a map of Strings to a list of HttpComponents fluent client NameValuePair.
     *
     * @param parameterMap the map of parameter Strings
     * @return a list of NameValuePair
     */
    private List<NameValuePair> toHttpParams(Map<String, String> parameterMap) {
        Check.notEmpty(parameterMap, "parameterMap");

        List<NameValuePair> result = new ArrayList<NameValuePair>();

        for (String key : parameterMap.keySet()) {
            String value = parameterMap.get(key);
            result.add(new BasicNameValuePair(key, value));
        }

        return result;
    }

    /**
     * Inspects the response for API errors, and raises a
     * <code>PumapiException</code> if so.
     *
     * @param responseBody the PUMAPI response content
     * @throws PumapiException in case an invalid API invocation
     */
    private void checkPumapiStatusOrFail(String responseBody) throws PumapiException {
        if (hasErrors(responseBody)) {
            log.error("[pumapi] Detected PUMAPI response error: {}", responseBody);
            throw new PumapiException(trimEol(responseBody));
        }
    }

    /**
     * Checks the response for API errors.
     *
     * As per the PUMAPI convention, HTTP response codes are set to 200
     * and an error condition is indicated directly in the response body
     * on a line starting with "error:".
     *
     * @param responseBody the PUMAPI response content
     * @return true if the response body contains an error token, false otherwise
     */
    private boolean hasErrors(String responseBody) {
        boolean result = false;

        if (null != responseBody) {
            result = responseBody.startsWith(ERROR_LOWERCASE) || responseBody.startsWith(ERROR_UPPERCASE);
        }

        return result;
    }

}
