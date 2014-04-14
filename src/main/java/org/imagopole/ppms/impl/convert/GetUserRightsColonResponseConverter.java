/**
 *
 */
package org.imagopole.ppms.impl.convert;

import static org.imagopole.ppms.util.Check.empty;
import static org.imagopole.ppms.util.PumapiUtil.trimAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.dto.PpmsUserPrivilege;
import org.imagopole.ppms.api.dto.PumapiParams.PpmsSystemPrivilege;

/**
 * PUMAPI data converter: maps a response body to an indexed list of instruments identifiers
 * (a.k.a. systems). Instruments are indexed by privilege level (for a given user).
 *
 * Indexing is performed on a line per line basis (uses a default-configured
 * <code>java.util.Scanner</code> internally).
 *
 * The returned lines are pre-processed: trailing space and line endings
 * are trimmed.
 *
 * @author seb
 * @see java.util.Scanner
 */
public class GetUserRightsColonResponseConverter
       implements PumapiDataConverter<String, List<PpmsUserPrivilege>> {

    /** Record separator for the <code>getuserrights</code> API call. */
    private final static String RECORDS_SEPARATOR = ":";

    /** The <code>getuserrights</code> API call currently provides a tuple per line */
    private final static int MIN_RECORDS_PER_LINE = 2;

    /**
     * Vanilla constructor
     */
    public GetUserRightsColonResponseConverter() {
        super();
    }

    @Override
    public List<PpmsUserPrivilege> map(String input) {
        List<PpmsUserPrivilege> result = new ArrayList<PpmsUserPrivilege>();

        if (null != input && !input.isEmpty()) {
            Scanner scanner = new Scanner(input);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String sanitizedLine = trimAll(line);

                PpmsUserPrivilege userRight = toUserPrivilege(sanitizedLine);
                if (null != userRight) {

                    result.add(userRight);

                }
            }
        }

        return result;
    }

    private PpmsUserPrivilege toUserPrivilege(String sanitizedLine) {

        PpmsUserPrivilege result = null;

        if (null != sanitizedLine && !sanitizedLine.isEmpty()) {
            String[] lineParts = sanitizedLine.split(RECORDS_SEPARATOR);

            if (null != lineParts && lineParts.length >= MIN_RECORDS_PER_LINE) {
                String privilegeCode = lineParts[0];
                String instrumentId = lineParts[1];

                result = parseUserPrivilege(privilegeCode, instrumentId);
            }
        }

        return result;
    }

    private PpmsUserPrivilege parseUserPrivilege(String privilegeCode, String instrumentId)
                    throws IllegalArgumentException, NumberFormatException {

        PpmsUserPrivilege result = null;

        if (!empty(privilegeCode) && !empty(instrumentId)) {

            // perform line parsing
            // may throw IllegalArgumentException
            PpmsSystemPrivilege privilege = PpmsSystemPrivilege.fromString(privilegeCode);

            // may throw NumberFormatException
            Long systemId = Long.parseLong(instrumentId);

            // parsing successful
            result = new PpmsUserPrivilege(systemId, privilege);

        }

        return result;
    }

}
