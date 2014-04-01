package org.imagopole.ppms.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.TestsUtil.TestKeys;
import org.imagopole.ppms.api.dto.PpmsGroup;
import org.imagopole.ppms.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GetGroupTests extends AbstractPumapiTest {

    /** Application logs */
    private final Logger log = LoggerFactory.getLogger(GetGroupTests.class);

    @Test(groups = { Groups.INTEGRATION })
    public void getKnownUserRequestShouldNotBeEmpty() {
        String testGroupKey = getIntegrationUnitLogin();

        if (!Check.empty(testGroupKey)) {
            PpmsGroup group = getClient().getGroup(testGroupKey);

            assertNotNull(group, "Non-null results expected");
            assertEquals(group.getUnitlogin(), testGroupKey, "Incorrect unitlogin attribute");
        } else {
            log.warn(String.format("No value for test key: %s - skipping integration test", TestKeys.UNIT_LOGIN));
        }

    }

}
