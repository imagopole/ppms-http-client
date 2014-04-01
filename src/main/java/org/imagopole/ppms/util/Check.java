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
        return (null == input || input.isEmpty());
    }

    public final static void notNull(Object obj, String argName) {
        if (null == obj) {
            throw new IllegalArgumentException(
                "Condition not met - expected : non-null parameter for " + argName);
        }
    }

    public final static void notEmpty(String obj, String argName) {
        Check.notNull(obj, argName);

        if (obj.trim().length() == 0) {
            throw new IllegalArgumentException(
                "Condition not met - expected : non-empty parameter for " + argName);
        }
    }

    public final static void notEmpty(Map<?, ?> obj, String argName) {
        Check.notNull(obj, argName);

        if (obj.keySet().isEmpty()) {
            throw new IllegalArgumentException(
                "Condition not met - expected : non-empty parameter for " + argName);
        }
    }

    public final static void notEmpty(Properties obj, String argName) {
        Check.notNull(obj, argName);

        if (obj.isEmpty()) {
            throw new IllegalArgumentException(
                "Condition not met - expected : non-empty parameter for " + argName);
        }
    }

    public final static void strictlyPositive(Integer number, String argName) {
        Check.notNull(number, argName);

        if (number < 1) {
            throw new IllegalArgumentException(
                "Condition not met - expected : strictly positive number for " + argName);
        }
    }
}
