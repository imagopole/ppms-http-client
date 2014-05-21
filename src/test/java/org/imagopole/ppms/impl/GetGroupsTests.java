package org.imagopole.ppms.impl;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.testng.annotations.Test;
/**
 * Tests for error conditions and preconditions validation.
 *
 * Some tests may be run in isolation, others may require a valid endpoint for integration testing.
 *
 * @author seb
 *
 */
public class GetGroupsTests extends AbstractPumapiTest {

    @Test(groups = { Groups.INTEGRATION })
    public void allGroupsRequestShouldNotBeEmpty() throws IOException {
        Collection<String> allGroups = getClient().getGroups(null);

        assertNotNull(allGroups, "Non-null results expected");
        assertTrue(allGroups.size() >= 1, "At least one group expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void activeGroupsRequestShouldNotBeEmpty() {
        Collection<String> aciveGroups = getClient().getGroups(Boolean.TRUE);

        assertNotNull(aciveGroups, "Non-null results expected");
        assertTrue(aciveGroups.size() >= 1, "At least one active group expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void inactiveGroupsRequestShouldNotBeEmpty() {
        Collection<String> inactiveGroups = getClient().getGroups(Boolean.FALSE);

        assertNotNull(inactiveGroups, "Non-null results expected");
        assertTrue(inactiveGroups.size() >= 1, "At least one inactive group expected");
    }

}
