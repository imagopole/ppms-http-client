/**
 *
 */
package org.imagopole.ppms.util;

import java.util.Map;
import java.util.Properties;


/**
 * @author seb
 *
 */
public final class Check {

    /** Private constructor (utility class) */
    private Check() {
        super();
    }

    public static final boolean empty(String input) {
        return (null == input || input.trim().isEmpty());
    }

    public final static void notNull(Object obj, String argName) {
        if (null == obj) {
            rejectEmptyParam(argName);
        }
    }

    public final static void notEmpty(String obj, String argName) {
        Check.notNull(obj, argName);

        if (empty(obj)) {
            rejectEmptyParam(argName);
        }
    }

    public final static void notEmpty(Map<?, ?> obj, String argName) {
        Check.notNull(obj, argName);

        if (obj.keySet().isEmpty()) {
            rejectEmptyParam(argName);
        }
    }

    public final static void notEmpty(Properties obj, String argName) {
        Check.notNull(obj, argName);

        if (obj.isEmpty()) {
            rejectEmptyParam(argName);
        }
    }

    public final static void strictlyPositive(Number number, String argName) {
        Check.notNull(number, argName);

        if (number.intValue() < 1) {
            throw new IllegalArgumentException(
                String.format("Condition not met - expected : strictly positive number for %s", argName));
        }
    }

    private static void rejectEmptyParam(String argName) throws IllegalArgumentException {
        throw new IllegalArgumentException(
            String.format("Condition not met - expected : non-empty parameter for %s", argName));
    }
}
