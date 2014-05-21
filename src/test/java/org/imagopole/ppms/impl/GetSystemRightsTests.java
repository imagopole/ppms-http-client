package org.imagopole.ppms.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.api.dto.PpmsPrivilege;
import org.imagopole.ppms.api.dto.PpmsSystemPrivilege;
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
public class GetSystemRightsTests extends AbstractPumapiTest {

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "^Condition not met .* systemId")
    public void nullSystemIdRequestShouldBeRejected() {
        getClient().getSystemRights(null);
    }

    @Test(groups = { Groups.INTEGRATION }, dataProvider = "unknownSystemIdDataProvider")
    public void unknownSystemIdRequestShouldNotBeEmpty(Long systemId) {
        List<PpmsSystemPrivilege> grantedUsers = getClient().getSystemRights(systemId);

        assertNotNull(grantedUsers, "Non-null results expected");
        assertEquals(grantedUsers.size(), 1, "One result expected");

        PpmsSystemPrivilege systemPrivilege = grantedUsers.get(0);
        assertEquals(systemPrivilege.getPrivilege(), PpmsPrivilege.SuperUser, "Incorrect result");
        assertEquals(systemPrivilege.getUsername(), "admin", "Incorrect result");
    }

    @Test(groups = { Groups.INTEGRATION })
    public void knownUSystemIdRequestShouldNotBeEmpty() {
        // dynamic system id lookup
        List<PpmsUserPrivilege> grantedInstruments = getClient().getUserRights("test");
        assertNotNull(grantedInstruments, "Non-null results expected");
        assertFalse(grantedInstruments.isEmpty(), "At least one system expected");

        Long systemId = grantedInstruments.get(0).getSystemId();

        List<PpmsSystemPrivilege> grantedUsers = getClient().getSystemRights(systemId);

        assertNotNull(grantedUsers, "Non-null results expected");
        assertFalse(grantedUsers.isEmpty(), "At least one user expected");
    }

    @DataProvider(name = "unknownSystemIdDataProvider")
    public Object[][] provideSystemIds() {
        return new Object[][] {
            { 0L   },
            { -1L  }
        };
    }

}
