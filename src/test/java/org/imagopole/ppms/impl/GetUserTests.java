package org.imagopole.ppms.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.TestsUtil.TestKeys;
import org.imagopole.ppms.api.dto.PpmsUser;
import org.imagopole.ppms.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Tests for error conditions and preconditions validation.
 *
 * Some tests may be run in isolation, others may require a valid endpoint for integration testing.
 *
 * @author seb
 *
 */
public class GetUserTests extends AbstractPumapiTest {

    /** Application logs */
    private final Logger log = LoggerFactory.getLogger(GetUserTests.class);

    @Test(groups = { Groups.INTEGRATION })
    public void getKnownUserRequestShouldNotBeEmpty() {
       String testUserName = getIntegrationLogin();

        if (!Check.empty(testUserName)) {
            PpmsUser user = getClient().getUser(testUserName);

            assertNotNull(user, "Non-null results expected");
            assertEquals(user.getLogin(), testUserName, "Incorrect login attribute");
        } else {
            log.warn(String.format("No value for test key: %s - skipping integration test [getuser]", TestKeys.LOGIN));
        }

    }

}
