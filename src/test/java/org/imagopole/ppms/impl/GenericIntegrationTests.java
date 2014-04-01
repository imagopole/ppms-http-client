package org.imagopole.ppms.impl;

import static org.imagopole.ppms.TestsUtil.reconfigureClient;

import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.api.PumapiException;
import org.testng.annotations.Test;
/**
 * Tests for error conditions and preconditions validation.
 *
 * Some tests may be run in isolation, others may require a valid endpoint for integration testing.
 *
 * @author seb
 *
 */
public class GenericIntegrationTests extends AbstractPumapiTest {

    @Test(groups = { Groups.INTEGRATION },
          expectedExceptions = { PumapiException.class, ClientProtocolException.class, HttpException.class })
    public void nonHttpEndpointSchemeRequestShouldFail() {
       // silly test: replace the leading 'http' protocol
       String malformedEndpoint = getIntegrationEndpoint().replace("http:", "unknown-scheme:");

       reconfigureClient(getClient(), getIntegrationApiKey(), malformedEndpoint);

       invokePumapiRpc();
    }

    // HTTP 405: Method Not Allowed - see http://support.microsoft.com/kb/216493 ?
    @Test(groups = { Groups.INTEGRATION },
          expectedExceptions = { PumapiException.class, HttpResponseException.class },
          expectedExceptionsMessageRegExp = "Method Not Allowed")
    public void missingTrailingSlashEndpointUrlRequestShouldTriggerHttp405Error() {
        // remove the (required) URL trailing slash
        String malformedEndpoint = getIntegrationEndpoint().replaceAll("(.*)\\/$", "$1");

        reconfigureClient(getClient(), getIntegrationApiKey(), malformedEndpoint);

        invokePumapiRpc();
    }

    // Mind EOL characters in exception messages - see https://github.com/cbeust/testng/issues/417
    @Test(groups = { Groups.INTEGRATION },
          expectedExceptions = { PumapiException.class },
          expectedExceptionsMessageRegExp = "^error.*request not authorized")
    public void unauthenticatedRequestShouldReturnResponseBodyError() {
        // make sure the API config is initialised without an authentication key, but
        // still hits a fully functional endpoint
        reconfigureClient(getClient(), "invalid-key-authentication-failure", getIntegrationEndpoint());

        invokePumapiRpc();
    }

    /**
     * Just want to exercise the functionality against the remote endpoint.
     *
     * @throws PumapiException
     */
    private void invokePumapiRpc() throws PumapiException {
        // could be any other call
        getClient().getUsers(null);
    }

}
