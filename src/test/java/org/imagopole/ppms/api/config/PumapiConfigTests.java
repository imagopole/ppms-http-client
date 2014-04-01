package org.imagopole.ppms.api.config;

import java.net.MalformedURLException;

import org.testng.annotations.Test;

/**
 *
 * @author seb
 *
 */
public class PumapiConfigTests {

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met.*URL with trailing slash.*")
    public void endpointUrlWithoutTrailingSlashShouldFailStrictValidation() {
        PumapiConfig config = new PumapiConfig();
        config.setApiKey("any-non-null-key");
        config.setEndpoint("http://a.seemingly.well-formed.url/with-no-trailing-slash");

        config.validateOrFail();
    }

    @Test
    public void endpointUrlWithoutTrailingSlashShouldPassStrictValidation() {
        PumapiConfig config = new PumapiConfig();
        config.setApiKey("any-non-null-key");
        config.setEndpoint("http://a.seemingly.well-formed.url/with-a-trailing-slash/");

        config.validateOrFail();
    }

    @Test(expectedExceptions = { IllegalArgumentException.class, MalformedURLException.class },
          expectedExceptionsMessageRegExp = "^Condition not met.*well-formed URL.*")
    public void endpointUrlWithInvalidSchemeShouldFailStrictValidation() {
        PumapiConfig config = new PumapiConfig();
        config.setApiKey("any-non-null-key");
        config.setEndpoint("some://malformed.endpoint.url");

        config.validateOrFail();
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met.*api_key.*")
    public void nullApiKeyRequestShouldBeRejected() {
        PumapiConfig config = new PumapiConfig();
        config.setApiKey(null);
        config.setEndpoint("any-non-null-endpoint");

        config.validateOrFail();
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met.*api_key.*")
    public void emptyApiKeyRequestShouldBeRejected() {
        PumapiConfig config = new PumapiConfig();
        config.setApiKey("");
        config.setEndpoint("any-non-null-endpoint");

        config.validateOrFail();
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met.*endpoint.*")
    public void nullEndpointRequestShouldBeRejected() {
        PumapiConfig config = new PumapiConfig();
        config.setApiKey("any-non-null-key");
        config.setEndpoint(null);

        config.validateOrFail();
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met.*endpoint.*")
    public void emptyEndpointRequestShouldBeRejected() {
        PumapiConfig config = new PumapiConfig();
        config.setApiKey("any-non-null-key");
        config.setEndpoint("");

        config.validateOrFail();
    }

}
