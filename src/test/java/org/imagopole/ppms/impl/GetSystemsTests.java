package org.imagopole.ppms.impl;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.imagopole.ppms.AbstractPumapiTest;
import org.imagopole.ppms.TestsUtil.Groups;
import org.imagopole.ppms.api.dto.PpmsSystem;
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
public class GetSystemsTests extends AbstractPumapiTest {

    @Test(groups = { Groups.INTEGRATION })
    public void allSystemsRequestShouldNotBeEmpty() throws IOException {
        List<PpmsSystem> allSystems = getClient().getSystems();

        assertNotNull(allSystems, "Non-null results expected");
        assertFalse(allSystems.isEmpty(), "At least one system expected");
    }

}
