/**
 *
 */
package org.imagopole.ppms;

import java.util.Collection;
import java.util.List;

import org.imagopole.ppms.api.PumapiClient;
import org.imagopole.ppms.api.config.PumapiConfig;
import org.imagopole.ppms.impl.PumapiBufferedHttpClient;
import org.imagopole.ppms.impl.PumapiHcFluentClientInvoker;
import org.imagopole.ppms.impl.convert.DefaultResponseConverterFactory;



/**
 * @author seb
 *
 */
public class PumapiClientMain {

    private final static String USAGE =
            "\nUsage: \n" +
            "    java org.imagopole.ppms.PumapiClientMain <ppms_endpoint_url> <ppms_api_key> [conn_timeout_ms] [sock_timeout_ms]" +
            "\nExample: \n" +
            "    java org.imagopole.ppms.PumapiClientMain http://www.ppms.info/ppms/pumapi/ SuperSecretApiKey 1000 1000" ;

    private final static String PROXY_HOST_KEY = "http.proxyHost";
    private final static String PROXY_PORT_KEY = "http.proxyPort";

    /**
     * Vanilla constructor
     */
    public PumapiClientMain() {
       super();
    }

    private static void exit(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }

    private static PumapiClient boot(String[] args) {
        PumapiConfig config = parseArgs(args);
        updateConfigFromEnv(config);
        config.validateOrFail();

        PumapiClient client = loadClient(config);

        return client;
    }

    private static PumapiConfig parseArgs(String[] args) {
        // required args
        String endpointUrl = args[0];
        String apiKey = args[1];

        PumapiConfig config = new PumapiConfig();
        config.setEndpoint(endpointUrl);
        config.setApiKey(apiKey);

        //optional args
        if (args.length > 2) {
            String connectTimeout = args[2];

            if (null != connectTimeout) {
                config.setConnectTimeout(Integer.parseInt(connectTimeout));
            }
        }

        if (args.length > 3) {
            String readTimeout = args[3];

            if (null != readTimeout) {
                config.setConnectTimeout(Integer.parseInt(readTimeout));
            }
        }

        return config;
    }

    private static void updateConfigFromEnv(PumapiConfig config) {
        // proxy
        String proxyHost = System.getProperty(PROXY_HOST_KEY);
        String proxyPort = System.getProperty(PROXY_PORT_KEY);

        if (null != proxyHost) {
            config.setProxyHost(proxyHost);
        }

        if (null != proxyPort) {
            config.setProxyPort(Integer.parseInt(proxyPort));
        }
    }

    private static PumapiClient loadClient(PumapiConfig config) {

        PumapiBufferedHttpClient client = new PumapiBufferedHttpClient(config);
        client.setHttpInvoker(new PumapiHcFluentClientInvoker());
        client.setDataConverterFactory(new DefaultResponseConverterFactory());

        return client;
    }

    private static void stdoutChecks(PumapiClient client) {
        // users
        Collection<String> allUsers = client.getUsers(null);
        System.out.println("all users: " + allUsers.size());
        //System.out.println("all users: " + allUsers);

        List<String> activeUsers = client.getUsers(Boolean.TRUE);
        System.out.println("active users: " + activeUsers.size());

        List<String> inactiveUsers = client.getUsers(Boolean.FALSE);
        System.out.println("inactive users: " + inactiveUsers.size());
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        PumapiClient client = null;

        switch (args.length) {

            case 2 : client = boot(args);
                     break;

            case 3 : client = boot(args);
                     break;

            case 4 : client = boot(args);
                     break;

            default: exit(USAGE);

        }

        if (null == client) {
            exit("Unable to get client handle - aborting");
        }

        stdoutChecks(client);
    }

}
