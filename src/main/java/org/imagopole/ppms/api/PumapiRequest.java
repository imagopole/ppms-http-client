/**
 *
 */
package org.imagopole.ppms.api;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.imagopole.ppms.api.config.PumapiConfig;
import org.imagopole.ppms.util.Check;

/**
 * PUMAPI request wrapper/builder with a fluent mapping around the native RPC invocations.
 *
 * Notes:
 * - only a subset of all available PUMAPI actions and settings is currently implemented.
 * - only a subset of the response formats is implemented (ie. CSV only, no JSON and
 *   no CSV without headers) due to partial support from PUMAPI itself.
 *
 * @author seb
 *
 */
public class PumapiRequest {

    /**  Client-wide API configuration parameters. */
    private PumapiConfig clientConfig;

    /** Request parameters as key-value pairs. */
    private Map<String, String> parameterMap;

    /**
     * Configuration-aware constructor.
     *
     * @param config configuration settings
     */
    public PumapiRequest(PumapiConfig config) {
        super();

        Check.notNull(config, "clientConfig");

        //config.validateOrFail();
        Check.notEmpty(config.getApiKey(), "config:api_key required");
        Check.notEmpty(config.getEndpoint(), "config:endpoint required");

        this.clientConfig = config;
        this.parameterMap = Collections.synchronizedMap(new TreeMap<String, String>());
    }

    /**
     * Uses the request's globally configured API key for authentication.
     * @return this request instance
     */
    public PumapiRequest withKeyAuth() {
        addKeyValuePair(GlobalParams.API_KEY, getClientConfig().getApiKey());
        return this;
    }

    /**
     * Uses the CSV response serialization format with header.
     * @return this request instance
     */
    public PumapiRequest toCsv() {
        addKeyValuePair(GlobalParams.FORMAT, Format.csv.name());
        return this;
    }

    /**
     * Uses the CSV response serialization format with no header.
     * @return this request instance
     */
    public PumapiRequest toCsvNoHeaders() {
        this.toCsv();
        addKeyValuePair(GlobalParams.NO_HEADERS, "true");
        return this;
    }

    /**
     * Returns a view on the current request's parameters.
     * @return this request's parameters
     */
    public Map<String, String> getParameterMap() {
        return Collections.unmodifiableMap(this.parameterMap);
    }

    /**
     * Defines the remote API action for this request.
     * @param action the PUMAPI action
     * @return this request instance
     */
    public PumapiRequest forAction(Action action) {
        addKeyValuePair(GlobalParams.ACTION, action.getPumapiName());
        return this;
    }

    /**
     * Adds an action parameter for this request.
     * @param filter the parameter key
     * @param value the parameter value
     * @return this request instance
     */
    public PumapiRequest withFilter(Filter filter, String value) {
        addKeyValuePair(filter.getPumapiName(), value);
        return this;
    }

    /**
     * Adds an action parameter for this request.
     * @param key the parameter key
     * @param value he parameter value
     */
    private void addKeyValuePair(String key, String value) {
        Check.notEmpty(key, "key");
        Check.notEmpty(value, String.format("value for key: %s", key));

        this.parameterMap.put(key, value);
    }

    /**
     * Looks up the requested response format.
     * May be null.
     *
     * @return the PUMAPI requested format
     */
    public Format getResponseFormat() {
        Format result = null;

        String formatParam = this.parameterMap.get(GlobalParams.FORMAT);
        if (null != formatParam) {
            result = Format.valueOf(formatParam);
        }

        return result;
    }

    /**
     * Indicates whether the requested format is CSV without header.
     * @return true if the request uses the CSV format with no header
     */
    public boolean isNoHeaders() {
        boolean result = false;

        String noHeadersParam = this.parameterMap.get(GlobalParams.NO_HEADERS);
        if (null != noHeadersParam) {
            result = Boolean.parseBoolean(noHeadersParam);
        }

        return result;
    }

    /**
     * Looks up the requested action key for this request.
     * @return the action enumeration
     */
    public Action getAction() {
        Action result = null;

        String actionParam = this.parameterMap.get(GlobalParams.ACTION);
        if (null != actionParam) {
            result = Action.fromString(actionParam);
        }

        return result;
    }

    /**
     * Looks up the action parameter for this request.
     * @param filter the parameter to lookup
     * @return the parameter value, or null
     */
    public String getFilterValue(Filter filter) {
        Check.notNull(filter, "filter");
        return this.parameterMap.get(filter.getPumapiName());
    }

    /**
     * Constants for PUMAPI global parameter keys.
     *
     * @author seb
     *
     */
    private final class GlobalParams {
        private static final String API_KEY = "apikey";
        private static final String FORMAT = "format";
        private static final String NO_HEADERS = "noheaders";
        private static final String ACTION = "action";

        /** Constants class. */
        private GlobalParams() {
            super();
        }
    }

    /**
     * PUMAPI request formats names.
     *
     * Note: JSON currently unsupported.
     *
     * @author seb
     *
     */
    public enum Format {
        csv,
        json
    }

    /**
     * PUMAPI request parameters names.
     *
     * @author seb
     *
     */
    public enum Filter {

        Active("active"),
        Login("login"),
        UnitLogin("unitlogin"),
        Id("id"),
        Password("pwd");

        /** PUMAPI call identifier. */
        private String pumapiName;

        private Filter(String name) {
            this.pumapiName = name;
        }

        public String getPumapiName() {
            return this.pumapiName;
        }

        public static Filter fromString(String name) {
            Check.notEmpty(name, "name");

            Filter result = null;

            for (Filter filter : Filter.values()) {
                if (filter.getPumapiName().equals(name)) {
                    result = filter;
                }
            }

            Check.notNull(result, String.format("enum RPC name mapping result: %s", name));
            return result;
        }
    }

    /**
     * PUMAPI "actions" request parameters names.
     *
     * @author seb
     *
     */
    public enum Action {

        GetUsers("getusers"),
        GetUser("getuser"),
        GetUserRights("getuserrights"),
        GetGroups("getgroups"),
        GetGroup("getgroup"),
        GetSystems("getsystems"),
        GetSystemRights("getsysrights"),
        Authenticate("auth");

        /** PUMAPI call identifier. */
        private String pumapiName;

        private Action(String name) {
            this.pumapiName = name;
        }

        public String getPumapiName() {
            return this.pumapiName;
        }

        public static Action fromString(String name) {
            Check.notEmpty(name, "name");

            Action result = null;

            for (Action action : Action.values()) {
                if (action.getPumapiName().equals(name)) {
                    result = action;
                }
            }

            Check.notNull(result, String.format("enum RPC name mapping result: %s", name));
            return result;
        }
    }

    /**
     * Returns clientConfig.
     * @return the clientConfig
     */
    public PumapiConfig getClientConfig() {
        return clientConfig;
    }

    /**
     * Sets clientConfig.
     * @param clientConfig the clientConfig to set
     */
    public void setClientConfig(PumapiConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

}
