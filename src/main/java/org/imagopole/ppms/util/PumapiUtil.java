/**
 *
 */
package org.imagopole.ppms.util;

/**
 * Misc utilities.
 *
 * @author seb
 *
 */
public final class PumapiUtil {

    /** CSV fields delimiter character. */
    public static final Character COMMA = ',';

    /** CSV fields delimiter character. */
    public static final Character COLON = ':';

    /** CSV fields quote character. */
    public static final Character DOUBLE_QUOTE = '"';

    /** Private constructor (utility class). */
    private PumapiUtil() {
        super();
    }

    public static final String trimEol(String line) {
        String result = null;

        if (null != line) {
            result = line.replaceAll("\r\n", "");
        }

        return result;
    }

    public static final String trimAll(String line) {
        String result = line;

        if (null != line) {
            result = trimEol(line.trim());
        }

        return result;
    }

}
