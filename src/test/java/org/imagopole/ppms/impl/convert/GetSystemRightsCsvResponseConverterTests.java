package org.imagopole.ppms.impl.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.imagopole.ppms.api.dto.PpmsPrivilege;
import org.imagopole.ppms.api.dto.PpmsSystemPrivilege;
import org.imagopole.ppms.util.PumapiUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author seb
 *
 */
public class GetSystemRightsCsvResponseConverterTests {

    private GetSystemRightsResponseConverter converter = new GetSystemRightsResponseConverter(PumapiUtil.COMMA);

    @Test(dataProvider = "emptyInputDataProvider")
    public void shouldConvertNullToEmptyList(String input) {
        List<PpmsSystemPrivilege> result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.isEmpty(), "Empty result expected");
    }

    @Test(dataProvider = "trailingInputDataProvider")
    public void shouldTrimInput(String input) {
        List<PpmsSystemPrivilege> result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.size() == 1, "One element expected");

        PpmsSystemPrivilege priv = (PpmsSystemPrivilege) result.get(0);
        assertEquals(priv.getPrivilege(), PpmsPrivilege.Autonomous);
        assertEquals(priv.getUsername(), "some.user");
    }

    @Test
    public void responseSampleTest() {
       String response = "A,\"some.user\" \r\n D,\"another.user\" \r\n N,\"and another\" \r\n S,\"some.user\" \r\n";
       List<PpmsSystemPrivilege> result = converter.map(response);

       assertNotNull(result, "Non-null result expected");
       assertTrue(result.size() == 4, "Four elements expected");

       PpmsSystemPrivilege priv1 = (PpmsSystemPrivilege) result.get(0);
       assertEquals(priv1.getPrivilege(), PpmsPrivilege.Autonomous);
       assertEquals(priv1.getUsername(), "some.user");

       PpmsSystemPrivilege priv2 = (PpmsSystemPrivilege) result.get(1);
       assertEquals(priv2.getPrivilege(), PpmsPrivilege.Deactivated);
       assertEquals(priv2.getUsername(), "another.user");

       PpmsSystemPrivilege priv3 = (PpmsSystemPrivilege) result.get(2);
       assertEquals(priv3.getPrivilege(), PpmsPrivilege.Novice);
       assertEquals(priv3.getUsername(), "and another");

       PpmsSystemPrivilege priv4 = (PpmsSystemPrivilege) result.get(3);
       assertEquals(priv4.getPrivilege(), PpmsPrivilege.SuperUser);
       assertEquals(priv4.getUsername(), "some.user");
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
            { "A,\"some.user\"\r"        },
            { "A,\"some.user\"\n"        },
            { "A,\"some.user\"\r\n"      },
            { "  A,\"some.user\" \r\n  " }
        };
    }

}
