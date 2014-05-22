package org.imagopole.ppms.impl.convert;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AuthCsvResponseConverterTests {

    private AuthCsvResponseConverter converter = new AuthCsvResponseConverter();

    @Test(dataProvider = "emptyInputDataProvider")
    public void shouldNotAuthEmptyInput(String input) {
        Boolean result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertFalse(result, "False result expected");
    }

    @Test(dataProvider = "trailingInputDataProvider")
    public void shouldTrimInput(String input) {
        Boolean result = converter.map(input);

        assertNotNull(result, "Non-null result expected");
        assertTrue(result, "True result expected");
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
            { "OK\r"        },
            { "OK\n"        },
            { "OK\r\n"      },
            { "  OK \r\n  " }
        };
    }

}
