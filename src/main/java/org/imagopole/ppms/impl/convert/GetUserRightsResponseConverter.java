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
import org.imagopole.ppms.api.dto.PpmsPrivilege;
import org.imagopole.ppms.api.dto.PpmsUserPrivilege;
import org.imagopole.ppms.util.Check;

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
public class GetUserRightsResponseConverter
       implements PumapiDataConverter<String, List<PpmsUserPrivilege>> {

    /** The <code>getuserrights</code> API call currently provides a tuple per line. */
    private static final int MIN_RECORDS_PER_LINE = 2;

    /** Record separator for the <code>getuserrights</code> API call.
     *  May be a comma or a colon. */
    private Character recordsSeparator;

    /**
     * Vanilla constructor.
     */
    public GetUserRightsResponseConverter() {
        super();
    }

    /**
     * Parameterized constructor.
     */
    public GetUserRightsResponseConverter(Character recordsSeparator) {
        super();

        Check.notNull(recordsSeparator, "recordsSeparator");
        this.recordsSeparator = recordsSeparator;
    }

    @Override
    public List<PpmsUserPrivilege> map(String input) {
        List<PpmsUserPrivilege> result = new ArrayList<PpmsUserPrivilege>();

        if (!empty(input)) {
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

        if (!empty(sanitizedLine)) {
            String[] lineParts = sanitizedLine.split(getRecordsSeparator().toString());

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
            PpmsPrivilege privilege = PpmsPrivilege.fromString(privilegeCode);

            // may throw NumberFormatException
            Long systemId = Long.parseLong(instrumentId);

            // parsing successful
            result = new PpmsUserPrivilege(systemId, privilege);

        }

        return result;
    }

    /**
     * Returns recordsSeparator.
     * @return the recordsSeparator
     */
    public Character getRecordsSeparator() {
        return recordsSeparator;
    }

    /**
     * Sets recordsSeparator.
     * @param recordsSeparator the recordsSeparator to set
     */
    public void setRecordsSeparator(Character recordsSeparator) {
        this.recordsSeparator = recordsSeparator;
    }

}
