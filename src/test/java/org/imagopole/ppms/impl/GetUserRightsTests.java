package org.imagopole.ppms.impl;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.api.dto.PumapiParams.PpmsSystemPrivilege;
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
        getClient().getUserRights(null, PpmsSystemPrivilege.Autonomous);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met .* login")
    public void emptyUsernameRequestShouldBeRejected() {
        getClient().getUserRights("", PpmsSystemPrivilege.Novice);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met .* login")
    public void blankUsernameRequestShouldBeRejected() {
        getClient().getUserRights("    ", PpmsSystemPrivilege.SuperUser);
    }

    @Test(enabled = false, groups = { Groups.BROKEN })
    public void malformedResponseBodyShouldBeIgnored() {
        // the "echo" mock invoker returns a multiline string with a syntax that
        // should not be recognised (hence ignored) by the response converter
        // eg. 'any-non-null-endpoint <LF> any.username.using.echo.mock.invoker'
        List<Long> grantedInstruments =
            getClient().getUserRights("any.username.using.echo.mock.invoker", null);

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.isEmpty(), "Empty results expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void unknownUserameRequestShouldBeEmpty() {
        List<Long> grantedInstruments =
            getClient().getUserRights("unknown-ppms-username", null);

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.isEmpty(), "Empty results expected");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void knownUserameRequestShouldNotBeEmpty() {
        List<Long> grantedInstruments = getClient().getUserRights("test", null);

        assertNotNull(grantedInstruments, "Non-null results expected");
        assertTrue(grantedInstruments.size() >= 1, "At least one system expected");
    }

    // not sure the call is supposed to be case-insensitive, but that's
    // how the API actually behaves
    @Test(groups = { Groups.INTEGRATION }, dataProvider = "usernameDataProvider")
    public void knownUserameRequestShouldBeCaseInsensitive(String username) {
        List<Long> grantedInstruments = getClient().getUserRights(username, null);

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
