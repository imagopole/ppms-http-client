package org.imagopole.ppms.impl.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.imagopole.ppms.api.dto.PpmsSystem;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetSystemsCsvResponseConverterTests {

    private GetSystemsCsvResponseConverter converter = new GetSystemsCsvResponseConverter();

    @Test(dataProvider = "emptyInputDataProvider")
    public void shouldConvertNullToEmptyList(String input) {
        List<PpmsSystem> result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.isEmpty(), "Empty result expected");
    }

    @Test
    public void responseSampleTest() {
        String input =
            "Core facility ref,System id,Type,Name,Localisation,Active,Schedules,Stats,Bookable,Autonomy Required,Autonomy Required After Hours \r\n"
          + "123,700,\"some.type\",\"some.name\",\"some.localisation\",False,True,True,True,True,False \r\n";

        List<PpmsSystem> result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertEquals(result.size(), 1, "Wrong number of results");
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
