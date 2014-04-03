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
          expectedExceptionsMessageRegExp = "^Condition not met .* login")
    public void nullUsernameRequestShouldBeRejected() {
        getClient().getUserRights(null);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met .* login")
    public void emptyUsernameRequestShouldBeRejected() {
        getClient().getUserRights("");
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met .* login")
    public void blankUsernameRequestShouldBeRejected() {
        getClient().getUserRights("    ");
    }

    @Test(enabled = false, groups = { Groups.BROKEN })
    public void malformedResponseBodyShouldBeIgnored() {
        // the "echo" mock invoker returns a multiline string with a syntax that
        // should not be recognised (hence ignored) by the response converter
        // eg. 'any-non-null-endpoint <LF> any.username.using.echo.mock.invoker'
        List<PpmsUserPrivilege> grantedInstruments =
            getClient().getUserRights("any.username.using.echo.mock.invoker");

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.isEmpty(), "Empty results expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void unknownUserameRequestShouldBeEmpty() {
        List<PpmsUserPrivilege> grantedInstruments =
            getClient().getUserRights("unknown-ppms-username");

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.isEmpty(), "Empty results expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void knownUserameRequestShouldNotBeEmpty() {
        List<PpmsUserPrivilege> grantedInstruments = getClient().getUserRights("test");

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.size() >= 1, "At least one system expected");
    }

    // not sure the call is supposed to be case-insensitive, but that's
    // how the API actually behaves
    @Test(groups = { Groups.INTEGRATION }, dataProvider = "usernameDataProvider")
    public void knownUserameRequestShouldBeCaseInsensitive(String username) {
        List<PpmsUserPrivilege> grantedInstruments = getClient().getUserRights(username);

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.size() >= 1, "At least one system expected");
    }

    @DataProvider(name = "usernameDataProvider")
    public Object[][] createMixedCaseUsernames() {
        return new Object[][] {
            { "test" },
            { "TEST" },
            { "tEsT" }
        };
    }

}
