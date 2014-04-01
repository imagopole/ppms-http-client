package org.imagopole.ppms.impl;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

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
public class GetUsersTests extends AbstractPumapiTest {

    @Test(groups = { Groups.INTEGRATION })
    public void allUsersRequestShouldNotBeEmpty() {
        Collection<String> allUsers = getClient().getUsers(null);

        assertNotNull(allUsers, "Non-null results expected");
        assertTrue(allUsers.size() >= 1, "At least one user expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void activeUsersRequestShouldNotBeEmpty() {
        Collection<String> allUsers = getClient().getUsers(Boolean.TRUE);

        assertNotNull(allUsers, "Non-null results expected");
        assertTrue(allUsers.size() >= 1, "At least one active user expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void inactiveUsersRequestShouldNotBeEmpty() {
        Collection<String> allUsers = getClient().getUsers(Boolean.FALSE);

        assertNotNull(allUsers, "Non-null results expected");
        assertTrue(allUsers.size() >= 1, "At least one inactive user expected");
    }

}
