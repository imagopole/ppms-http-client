package org.imagopole.ppms.api.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.imagopole.ppms.impl.convert.GetUsersCsvResponseConverter;
import org.testng.annotations.Test;

/**
 *
 * @author seb
 *
 */
public class GetUsersResponseConverterTests {

    @Test
    public void stringCollectionConverterShouldConvertNullToEmptyList() {
        Collection<String> result = new GetUsersCsvResponseConverter().map(null);

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.isEmpty(), "Empty result expected");
    }

    @Test
    public void stringCollectionConverterShouldConvertBlankToEmptyList() {
        Collection<String> result = new GetUsersCsvResponseConverter().map("");

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.isEmpty(), "Empty result expected");
    }

    @Test
    public void stringCollectionConverterShouldConvertWhitespaceToEmptyList() {
        Collection<String> result = new GetUsersCsvResponseConverter().map("    ");

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.isEmpty(), "Empty result expected");
    }

    @Test
    public void stringCollectionConverterShouldRemoveCarriageReturns() {
        List<String> result = new GetUsersCsvResponseConverter().map("cr.start \r cr.end");

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.size() == 2, "Two lines expected");
        assertEquals(result.get(0), "cr.start");
        assertEquals(result.get(1), "cr.end");
    }

    @Test
    public void stringCollectionConverterShouldRemoveLineFeeds() {
        List<String> result = new GetUsersCsvResponseConverter().map("lf.start \n lf.end");

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.size() == 2, "Two lines expected");
        assertEquals(result.get(0), "lf.start");
        assertEquals(result.get(1), "lf.end");
    }

    @Test
    public void stringCollectionConverterShouldRemoveEndOfLines() {
        List<String> result = new GetUsersCsvResponseConverter().map("crlf.start \r\n crlf.end");

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.size() == 2, "Two lines expected");
        assertEquals(result.get(0), "crlf.start");
        assertEquals(result.get(1), "crlf.end");
    }

    @Test
    public void stringCollectionConverterShouldRemoveTrailingSpaces() {
        List<String> result = new GetUsersCsvResponseConverter().map("  trail.start with.trailing.space+eol \r\n  ");

        assertNotNull(result, "Non-null result expected");
        assertTrue(result.size() == 1, "One line expected");
        assertEquals(result.get(0), "trail.start with.trailing.space+eol");
    }

}
