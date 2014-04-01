package org.imagopole.ppms.impl;

import static org.testng.Assert.assertFalse;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.TestsUtil.TestKeys;
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
public class AuthenticateTests extends AbstractPumapiTest {

    /** Application logs */
    private final Logger log = LoggerFactory.getLogger(AuthenticateTests.class);

    @Test(groups = { Groups.INTEGRATION })
    public void authenticateWrongPwd() {
       String testUserName = getIntegrationLogin();

        if (!Check.empty(testUserName)) {
            boolean success = getClient().authenticate(testUserName, "wrong-password");

            assertFalse(success, "Incorrect result");
        } else {
            log.warn(String.format("No value for test key: %s - skipping integration test [authenticate]", TestKeys.LOGIN));
        }

    }

}
