/**
 *
 */
package org.imagopole.ppms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.imagopole.ppms.TestsUtil.Env;
import org.imagopole.ppms.TestsUtil.TestKeys;
import org.imagopole.ppms.api.PumapiClient;
import org.imagopole.ppms.api.config.PumapiConfig;
import org.imagopole.ppms.api.config.PumapiConfig.Keys;
import org.imagopole.support.unitils.UnitilsSpringTestNG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import static org.imagopole.ppms.util.PumapiUtil.empty;
import static org.testng.Assert.fail;

/**
 * Base class for Pumapi Unit/Integration tests with Spring and TestNG.
 *
 * @author seb
 *
 */
@SpringApplicationContext(TestsUtil.TESTING_SPRING_CONTEXT)
public abstract class AbstractPumapiTest extends UnitilsSpringTestNG {

    /** Application logs. */
    private final Logger log = LoggerFactory.getLogger(AbstractPumapiTest.class);

    /** Client configuration settings. */
    private Properties configProperties;

    /** HTTP client instance for integration tests. */
    @SpringBeanByType
    private PumapiClient client;

    /**
     * Lookup API configuration settings from environment
     * @throws IOException
     * @throws FileNotFoundException
     */
    @BeforeClass
    @Parameters(Env.PUMAPI_CONFIG_LOCATION)
    public void setUp(@Optional String pumapiConfigLocation) throws FileNotFoundException, IOException {

        // property file config
        String pumapiConfig = System.getenv(Env.PUMAPI_CONFIG);

        Properties props = new Properties();
        if (!empty(pumapiConfig) && !empty(pumapiConfig.trim())) {
            log.debug("Loading PUMAPI configuration from 'PUMAPI_CONFIG' at {}", pumapiConfig);

            props.load(new FileInputStream(pumapiConfig));
        } else if (!empty(pumapiConfigLocation) && !empty(pumapiConfigLocation.trim())){
            log.debug("Loading PUMAPI configuration from 'pumapi.config.location' at {}", pumapiConfigLocation);

            props.load(new FileInputStream(pumapiConfigLocation));
        } else {
            fail("Run integration tests with exported PUMAPI_CONFIG=/path/to/pumapi-local.properties environment variable "
                 + "or -Dpumapi.config.location=/path/to/pumapi-local.properties JVM system property");
        }

        this.configProperties = props;
        configureIntegrationClient();
    }

    protected void configureIntegrationClient() {
        log.debug("Configuring PUMAPI client: {} with properties: {}", getClient(), getConfigProperties());
        PumapiConfig integrationTestsConfig = PumapiConfig.fromProperties(getConfigProperties());
        getClient().setConfig(integrationTestsConfig);
    }

    /**
     * Returns configProperties.
     * @return the configProperties
     */
    protected Properties getConfigProperties() {
        return configProperties;
    }

    protected String getIntegrationApiKey() {
        return getConfigProperties().getProperty(Keys.API_KEY);
    }

    protected String getIntegrationEndpoint() {
        return getConfigProperties().getProperty(Keys.ENDPOINT);
    }

    protected String getIntegrationLogin() {
        return getConfigProperties().getProperty(TestKeys.LOGIN);
    }

    protected String getIntegrationUnitLogin() {
        return getConfigProperties().getProperty(TestKeys.UNIT_LOGIN);
    }

    /**
     * Returns integrationClient.
     * @return the integrationClient
     */
    protected PumapiClient getClient() {
        return client;
    }

}
