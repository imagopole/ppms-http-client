/**
 *
 */
package org.imagopole.ppms;

import org.imagopole.ppms.api.PumapiClient;

/**
 * @author seb
 *
 */
public class TestsUtil {

    public final static String DEFAULT_SPRING_CONTEXT =
        "classpath:pumapi/pumapi-client-applicationContext.xml";

    public final static String TESTING_SPRING_CONTEXT =
        "classpath:pumapi/pumapi-test-applicationContext.xml";

    public final static void reconfigureClient(PumapiClient client, String apiKey, String endpoint) {
        client.getConfig().setApiKey(apiKey);
        client.getConfig().setEndpoint(endpoint);
     }

    /**
     * Test groups.
     *
     * @author seb
     *
     */
    public final static class Groups {

        public final static String INTEGRATION = "integration";
        public final static String BROKEN = "broken";

        /** Private constructor (utility class) */
        private Groups() {
            super();
        }
    }

    /**
     * Environment variables keys for tests lookups.
     *
     * @author seb
     *
     */
    public final static class Env {

        public final static String PUMAPI_CONFIG = "PUMAPI_CONFIG";
        public final static String PUMAPI_CONFIG_LOCATION = "pumapi.config.location";

        /** Private constructor (utility class) */
        private Env() {
            super();
        }
    }

    /**
     * Extra configuration keys/values dedicated to integration testing.
     * @author seb
     *
     */
    public static class TestKeys {
        /** PPMS user name */
        public final static String LOGIN = "pumapi.tests.login";

        /** PPMS group (unit) string identifer */
        public final static String UNIT_LOGIN = "pumapi.tests.unitlogin";

        /** Private constructor (utility class) */
        private TestKeys() {
            super();
        }
    }

    /** Private constructor (utility class) */
    private TestsUtil() {
        super();
    }

}
