package org.imagopole.ppms.impl.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.imagopole.ppms.api.dto.PpmsSystem;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetSystemCsvResponseConverterTests {

    private GetSystemCsvResponseConverter converter = new GetSystemCsvResponseConverter();

    @Test(dataProvider = "emptyInputDataProvider")
    public void shouldNullifyEmptyInput(String input) {
        PpmsSystem result = converter.map(input);

        assertNull(result, "Null result expected");
    }

    @Test
    public void responseSampleTest() {
        String input =
            "Core facility ref,System id,Type,Name,Localisation,Active,Schedules,Stats,Bookable,Autonomy Required,Autonomy Required After Hours \r\n"
          + "123,700,\"some.type\",\"some.name\",\"some.localisation\",False,True,True,True,True,False \r\n";

        PpmsSystem result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertEquals(result.getCoreFacilityRef(), new Long(123));
        assertEquals(result.getSystemId(), new Long(700));
        assertEquals(result.getType(), "some.type");
        assertEquals(result.getLocalisation(), "some.localisation");
        assertFalse(result.getActive());
        assertTrue(result.getSchedules());
        assertTrue(result.getStats());
        assertTrue(result.getBookable());
        assertTrue(result.getAutonomyRequired());
        assertFalse(result.getAutonomyRequiredAfterHours());
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
