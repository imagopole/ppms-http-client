/**
 *
 */
package org.imagopole.ppms.util;

/**
 * @author seb
 *
 */
public class PumapiUtil {

    /** CSV fields delimiter character */
    public final static Character COMMA = ',';

    /** CSV fields delimiter character */
    public final static Character COLON = ':';

    /** Private constructor (utility class) */
    private PumapiUtil() {
        super();
    }

    public final static String trimEol(String line) {
        String result = null;

        if (null != line) {
            result = line.replaceAll("\r\n", "");
        }

        return result;
    }

    public final static String trimAll(String line) {
        String result = line;

        if (null != line) {
            result = trimEol(line.trim());
        }

        return result;
    }

    public static final boolean empty(String input) {
        return (null == input || input.isEmpty());
    }
}
