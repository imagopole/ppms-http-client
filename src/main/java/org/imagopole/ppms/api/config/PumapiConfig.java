/**
 *
 */
package org.imagopole.ppms.api.config;

import static org.imagopole.ppms.util.Check.empty;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.imagopole.ppms.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Simple configuration holder for client-wide settings.
 *
 * @author seb
 *
 */
public class PumapiConfig {

    /** Remote endpoint URL */
    private String endpoint;

    /** Authentication credentials */
    private String apiKey;

    /** Proxy host setting */
    private String proxyHost;

    /** Proxy port setting */
    private Integer proxyPort;

    /** Socket read timeout */
    private Integer socketTimeout;

    /** Socket connect timeout */
    private Integer connectTimeout;

    /**
     * Vanilla constructor
     */
    public PumapiConfig() {
        super();
    }

    /**
     * Converts a {@link Properties} object into a configuration object.
     *
     * @param configProperties the source configuration
     * @return PumapiConfig
     * @throws NumberFormatException
     */
    public static PumapiConfig fromProperties(Properties configProperties) throws NumberFormatException {
        Check.notEmpty(configProperties, "configProperties");

        String apiKeyProp = configProperties.getProperty(Keys.API_KEY);
        String endpointProp = configProperties.getProperty(Keys.ENDPOINT);
        String proxyHostProp = configProperties.getProperty(Keys.PROXY_HOST);
        String proxyPortProp = configProperties.getProperty(Keys.PROXY_PORT);
        String connectTimeoutProp = configProperties.getProperty(Keys.CONNECT_TIMEOUT);
        String socketTimeoutProp = configProperties.getProperty(Keys.SOCKET_TIMEOUT);

        Integer proxyPort = null;
        Integer connectTimeout = null;
        Integer socketTimeout = null;
        if (!empty(proxyPortProp)) {
            proxyPort = Integer.parseInt(proxyPortProp);
        }
        if (!empty(connectTimeoutProp)) {
            connectTimeout = Integer.parseInt(connectTimeoutProp);
        }
        if (!empty(socketTimeoutProp)) {
            socketTimeout = Integer.parseInt(socketTimeoutProp);
        }

        PumapiConfig result = new PumapiConfig();
        result.setApiKey(apiKeyProp);
        result.setEndpoint(endpointProp);
        result.setProxyHost(proxyHostProp);
        result.setProxyPort(proxyPort);
        result.setConnectTimeout(connectTimeout);
        result.setSocketTimeout(socketTimeout);

        return result;
    }

    /**
     * Config validation: checks for invalid/malformed settings.
     *
     * @throws IllegalArgumentException in case of missing or invalid parameters
     */
    public void validateOrFail() throws IllegalArgumentException {
        new ConfigValidator().validateOrFail(this);
    }

    /**
     * Configuration keys for the PUMAPI HTTP client.
     *
     * @author seb
     *
     */
    public static class Keys {
        public final static String API_KEY         = "pumapi.api_key";
        public final static String ENDPOINT        = "pumapi.endpoint";
        public final static String PROXY_HOST      = "pumapi.proxy_host";
        public final static String PROXY_PORT      = "pumapi.proxy_port";
        public final static String CONNECT_TIMEOUT = "pumapi.connect_timeout";
        public final static String SOCKET_TIMEOUT  = "pumapi.socket_timeout";

        /** Constants class. */
        private Keys() {
            super();
        }
    }

    /**
     * A config validator: eagerly checks for invalid/malformed settings.
     *
     * @author seb
     *
     */
    private class ConfigValidator {

        /** Issue a warning if the API key is surprisingly short */
        private final static int MIN_API_KEY_LENGTH = 5;

        /** Application logs */
        private final Logger log = LoggerFactory.getLogger(ConfigValidator.class);

        private void validateOrFail(PumapiConfig config) throws IllegalArgumentException {
            Check.notNull(config, "config");

            // check required parameters (applies to all API calls)
            String endpointConfig = config.getEndpoint();
            String apiKeyConfig = config.getApiKey();

            Check.notEmpty(endpointConfig, "config:endpoint");
            Check.notEmpty(apiKeyConfig, "config:api_key");

            // sanity checks for required parameters "well-formedness"
            checkUrlSyntaxOrFail(endpointConfig, "config:endpoint");
            if (!endpointConfig.endsWith("/")) {
                // the PUMAPI invocation returns a HTTP 405 code ("Method Not Allowed")
                // if the endpoint does not use a trailing slash
                // see http://support.microsoft.com/kb/216493
                throw new IllegalArgumentException(
                    "Condition not met - expected : URL with trailing slash for config:endpoint");
            }
            if (!endpointConfig.startsWith("https")) {
                log.warn("PUMAPI endpoint URL not using HTTPS - usage discouraged");
            }

            if (apiKeyConfig.length() < MIN_API_KEY_LENGTH) {
                log.warn("PUMAPI authentication key length too short - " +
                         "please double check your config file (requests may be rejected)");
            }

            // optional parameters: sanity checks only
            String proxyHostConfig = config.getProxyHost();
            if (null != proxyHostConfig) {
                checkUrlSyntaxOrFail(proxyHostConfig, "config:proxy_host");
            }

            checkPositiveNumberIfPresentOrFail(config.getProxyPort(), "config:proxy_port");
            checkPositiveNumberIfPresentOrFail(config.getConnectTimeout(), "config:connect_timeout");
            checkPositiveNumberIfPresentOrFail(config.getSocketTimeout(), "config:socket_timeout");
        }

        private void checkUrlSyntaxOrFail(String urlSpec, String argName) {
            try {
                new URL(urlSpec);
            } catch (MalformedURLException mue) {
                log.error("Failed to parse PUMAPI configuration setting: {}", argName, mue);
                throw new IllegalArgumentException(
                    "Condition not met - expected : well-formed URL for " + argName, mue);
            }
        }

        private void checkPositiveNumberIfPresentOrFail(Integer number, String argName) {
            // optional parameter: only perform the check if a value has been configured
            if (null != number) {
                Check.strictlyPositive(number, argName);
            }
        }

    }

    /**
     * Returns endpoint.
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Sets endpoint.
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Returns apiKey.
     * @return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets apiKey.
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Returns proxyHost.
     * @return the proxyHost
     */
    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * Sets proxyHost.
     * @param proxyHost the proxyHost to set
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * Returns proxyPort.
     * @return the proxyPort
     */
    public Integer getProxyPort() {
        return proxyPort;
    }

    /**
     * Sets proxyPort.
     * @param proxyPort the proxyPort to set
     */
    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * Returns socketTimeout.
     * @return the socketTimeout
     */
    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * Sets socketTimeout.
     * @param socketTimeout the socketTimeout to set
     */
    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    /**
     * Returns connectTimeout.
     * @return the connectTimeout
     */
    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets connectTimeout.
     * @param connectTimeout the connectTimeout to set
     */
    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

}
