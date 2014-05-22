/**
 *
 */
package org.imagopole.ppms.impl.convert;

import static org.imagopole.ppms.util.Check.empty;
import static org.imagopole.ppms.util.PumapiUtil.trimAll;

import org.imagopole.ppms.api.convert.PumapiDataConverter;

/**
 * PUMAPI data converter: maps a response body to a Boolean parsed from
 * a well-known return code on a single line.
 *
 * @author seb
 *
 */
public class AuthCsvResponseConverter
       implements PumapiDataConverter<String, Boolean> {

    /** PUMAPI returns 'OK' if authentication is successful (nothing otherwise). */
    private static final String OK_RETURN_CODE = "OK";

    /**
     * Vanilla constructor.
     */
    public AuthCsvResponseConverter() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean map(String input) {
        Boolean success = Boolean.FALSE;

        if (!empty(input)) {

            String sanitizedInput = trimAll(input);
            success = sanitizedInput.contains(OK_RETURN_CODE);

        }

        return success;
    }

}
