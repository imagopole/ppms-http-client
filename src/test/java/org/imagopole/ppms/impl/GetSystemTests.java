package org.imagopole.ppms.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.TestsUtil.TestKeys;
import org.imagopole.ppms.api.dto.PpmsSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for error conditions and preconditions validation.
 *
 * Some tests may be run in isolation, others may require a valid
 * endpoint for integration testing.
 *
 * @author seb
 *
 */
public class GetSystemTests extends AbstractPumapiTest {

    /** Application logs */
    private final Logger log = LoggerFactory.getLogger(GetSystemTests.class);

    @Test(groups = { Groups.INTEGRATION })
    public void getKnownSystemRequestShouldNotBeEmpty() {
        Long testSystemId = getIntegrationSystemId();

        if (null != testSystemId) {
            PpmsSystem system = getClient().getSystem(testSystemId);

            assertNotNull(system, "Non-null results expected");
            assertEquals(system.getSystemId(), testSystemId, "Incorrect systemId attribute");
        } else {
            log.warn(String.format("No value for test key: %s - skipping integration test", TestKeys.SYSTEM_ID));
        }
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met - expected : .* for systemId",
          dataProvider = "invalidSystemIdsDataProvider")
    public void getInvalidSystemRequestShouldFail(Long systemId) {
        getClient().getSystem(systemId);
    }

    @DataProvider(name="invalidSystemIdsDataProvider")
    private Object[][] provideInvalidSystemIds() {
        return new Object[][] {
            { null },
            { -1L  },
            { 0L   }
        };
    }

}
