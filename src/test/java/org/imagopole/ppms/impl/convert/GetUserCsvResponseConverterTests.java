package org.imagopole.ppms.impl.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.imagopole.ppms.api.dto.PpmsUser;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetUserCsvResponseConverterTests {

    private GetUserCsvResponseConverter converter = new GetUserCsvResponseConverter();

    @Test(dataProvider = "emptyInputDataProvider")
    public void shouldNullifyEmptyInput(String input) {
        PpmsUser result = converter.map(input);

        assertNull(result, "Null result expected");
    }

    @Test
    public void responseSampleTest() {
        String input =
            "login,lname,fname,email,phone,bcode,affiliation,unitlogin,mustchpwd,mustchbcode,active \r\n"
          + "\"some.login\",\"some.last.name\",\"some.first.name\",\"some.email\",\"123\",\"\",\"\",\"some.unitlogin\",false,false,true \r\n";

        PpmsUser result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertEquals(result.getLogin(), "some.login");
        assertEquals(result.getLname(), "some.last.name");
        assertEquals(result.getFname(), "some.first.name");
        assertEquals(result.getEmail(), "some.email");
        assertEquals(result.getPhone(), "123");
        assertNull(result.getBcode());
        assertNull(result.getAffiliation());
        assertEquals(result.getUnitlogin(), "some.unitlogin");
        assertFalse(result.getMustchpwd());
        assertFalse(result.getMustchbcode());
        assertTrue(result.getActive());
    }

    @DataProvider(name = "emptyInputDataProvider")
    private Object[][] provideEmptyInput() {
        return new Object[][] {
            { null   },
            { ""     },
            { "    " }
        };
    }

}
