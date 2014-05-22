package org.imagopole.ppms.impl;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.api.dto.PpmsUserPrivilege;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for error conditions and preconditions validation.
 *
 * Some tests may be run in isolation, others may require a valid endpoint for integration testing.
 *
 * @author seb
 *
 */
public class GetUserRightsTests extends AbstractPumapiTest {

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met .* login",
          dataProvider = "invalidUsernameDataProvider")
    public void nullUsernameRequestShouldBeRejected(String login) {
        getClient().getUserRights(login);
    }

    @Test(groups = { Groups.INTEGRATION })
    public void unknownUsernameRequestShouldBeEmpty() {
        List<PpmsUserPrivilege> grantedInstruments =
            getClient().getUserRights("unknown-ppms-username");

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.isEmpty(), "Empty results expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void knownUsernameRequestShouldNotBeEmpty() {
        List<PpmsUserPrivilege> grantedInstruments = getClient().getUserRights("test");

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.size() >= 1, "At least one system expected");
    }

    // not sure the call is supposed to be case-insensitive, but that's
    // how the API actually behaves
    @Test(groups = { Groups.INTEGRATION }, dataProvider = "usernameDataProvider")
    public void knownUsernameRequestShouldBeCaseInsensitive(String username) {
        List<PpmsUserPrivilege> grantedInstruments = getClient().getUserRights(username);

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.size() >= 1, "At least one system expected");
    }

    @DataProvider(name = "usernameDataProvider")
    private Object[][] provideMixedCaseUsernames() {
        return new Object[][] {
            { "test" },
            { "TEST" },
            { "tEsT" }
        };
    }

    @DataProvider(name="invalidUsernameDataProvider")
    private Object[][] provideInvalidUsernames() {
        return new Object[][] {
            { null    },
            { ""      },
            { "    "  }
        };
    }

}
