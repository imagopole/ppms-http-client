package org.imagopole.ppms.impl;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collection;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
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
public class GetGroupsTests extends AbstractPumapiTest {

    @Test(groups = { Groups.INTEGRATION }, dataProvider = "booleansDataProvider")
    public void getGroupsTests(Boolean active) throws IOException {
        Collection<String> result = getClient().getGroups(active);

        assertNotNull(result, "Non-null results expected");
        assertFalse(result.isEmpty(), "At least one group expected");
    }

    @DataProvider(name="booleansDataProvider")
    private Object[][] provideBooleans() {
        return new Object[][] {
            { null           },
            { Boolean.TRUE   },
            { Boolean.FALSE  }
        };
    }

}
