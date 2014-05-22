package org.imagopole.ppms.impl.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.imagopole.ppms.api.dto.PpmsGroup;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetGroupCsvResponseConverterTests {

    private GetGroupCsvResponseConverter converter = new GetGroupCsvResponseConverter();

    @Test(dataProvider = "emptyInputDataProvider")
    public void shouldNullifyEmptyInput(String input) {
        PpmsGroup result = converter.map(input);

        assertNull(result, "Null result expected");
    }

    @Test
    public void responseSampleTest() {
        String input =
            "unitlogin,unitname,headname,heademail,unitbcode,department,institution,address,affiliation,ext,active \r\n"
          + "\"some.unit\",\"some.unit.name\",\"some.head.name\",\"some.email\",\"123\",\"some.dept\",\"some.institution\",\"\",\"\",false,true \r\n";

        PpmsGroup result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertEquals(result.getUnitlogin(), "some.unit");
        assertEquals(result.getUnitname(), "some.unit.name");
        assertEquals(result.getHeadname(), "some.head.name");
        assertEquals(result.getHeademail(), "some.email");
        assertEquals(result.getUnitbcode(), "123");
        assertEquals(result.getDepartment(), "some.dept");
        assertEquals(result.getInstitution(), "some.institution");
        assertNull(result.getAddress());
        assertNull(result.getAffiliation());
        assertFalse(result.getExt());
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
