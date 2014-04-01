/**
 *
 */
package org.imagopole.support.unitils;

import org.testng.annotations.BeforeClass;
import org.unitils.UnitilsTestNG;

/**
 * @author seb
 *
 */
public class UnitilsSpringTestNG extends UnitilsTestNG {

    /**
     * {@inheritDoc}
     *
     * @see org.unitils.UnitilsTestNG#unitilsBeforeClass()
     * @see org.unitils.UnitilsTestNG#unitilsBeforeTestSetUp()
     */
    @Override
    @BeforeClass(alwaysRun = true)
    protected void unitilsBeforeClass() {
        // default super behaviour
        super.unitilsBeforeClass();

        // also force spring context initialization and beans injection at test class
        // initialization time (@BeforeClass) - by default, the SpringTestListener is
        // triggered in @BeforeMethod only.
        super.unitilsBeforeTestSetUp(null);
    }

}
