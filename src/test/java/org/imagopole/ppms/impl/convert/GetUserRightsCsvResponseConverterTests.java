package org.imagopole.ppms.impl.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.imagopole.ppms.api.dto.PpmsPrivilege;
import org.imagopole.ppms.api.dto.PpmsUserPrivilege;
import org.imagopole.ppms.impl.convert.GetUserRightsResponseConverter;
import org.imagopole.ppms.util.PumapiUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author seb
 *
 */
public class GetUserRightsCsvResponseConverterTests {

    private GetUserRightsResponseConverter converter = new GetUserRightsResponseConverter(PumapiUtil.COMMA);

    @Test(dataProvider = "emptyInputDataProvider")
    public void shouldConvertEmptyInputToEmptyList(String input) {
        List<PpmsUserPrivilege> result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.isEmpty(), "Empty result expected");
    }

    @Test(dataProvider = "trailingInputDataProvider")
    public void shouldTrimInput(String input) {
        List<PpmsUserPrivilege> result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.size() == 1, "One element expected");

        PpmsUserPrivilege priv = (PpmsUserPrivilege) result.get(0);
        assertEquals(priv.getPrivilege(), PpmsPrivilege.Autonomous);
        assertEquals(priv.getSystemId(), new Long(123));
    }

    @Test
    public void responseSampleTest() {
       String response = "A,57 \r\n D,70 \r\n N,53 \r\n S,126 \r\n";
       List<PpmsUserPrivilege> result = converter.map(response);

       assertNotNull(result, "Non-null result expected");
       assertTrue(result.size() == 4, "Four elements expected");

       PpmsUserPrivilege priv1 = (PpmsUserPrivilege) result.get(0);
       assertEquals(priv1.getPrivilege(), PpmsPrivilege.Autonomous);
       assertEquals(priv1.getSystemId(), new Long(57));

       PpmsUserPrivilege priv2 = (PpmsUserPrivilege) result.get(1);
       assertEquals(priv2.getPrivilege(), PpmsPrivilege.Deactivated);
       assertEquals(priv2.getSystemId(), new Long(70));

       PpmsUserPrivilege priv3 = (PpmsUserPrivilege) result.get(2);
       assertEquals(priv3.getPrivilege(), PpmsPrivilege.Novice);
       assertEquals(priv3.getSystemId(), new Long(53));

       PpmsUserPrivilege priv4 = (PpmsUserPrivilege) result.get(3);
       assertEquals(priv4.getPrivilege(), PpmsPrivilege.SuperUser);
       assertEquals(priv4.getSystemId(), new Long(126));
    }

    @DataProvider(name = "emptyInputDataProvider")
    private Object[][] provideEmptyInput() {
        return new Object[][] {
            { null   },
            { ""     },
            { "    " }
        };
    }

    @DataProvider(name = "trailingInputDataProvider")
    private Object[][] provideTrailingInput() {
        return new Object[][] {
            { "A,123\r"        },
            { "A,123\n"        },
            { "A,123\r\n"      },
            { "  A,123 \r\n  " }
        };
    }

}
