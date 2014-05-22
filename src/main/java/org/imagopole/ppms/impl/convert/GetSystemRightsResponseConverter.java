/**
 *
 */
package org.imagopole.ppms.impl.convert;

import static org.imagopole.ppms.util.Check.empty;
import static org.imagopole.ppms.util.PumapiUtil.DOUBLE_QUOTE;
import static org.imagopole.ppms.util.PumapiUtil.trimAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.dto.PpmsPrivilege;
import org.imagopole.ppms.api.dto.PpmsSystemPrivilege;
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
public class GetSystemRightsResponseConverter
       implements PumapiDataConverter<String, List<PpmsSystemPrivilege>> {

    /** The <code>getuserrights</code> API call currently provides a tuple per line. */
    private static final int MIN_RECORDS_PER_LINE = 2;

    /** Record separator for the <code>getsysrights</code> API call.
     *  May be a comma or a colon. */
    private Character recordsSeparator;

    /**
     * Vanilla constructor.
     */
    public GetSystemRightsResponseConverter() {
        super();
    }

    /**
     * Parameterized constructor.
     */
    public GetSystemRightsResponseConverter(Character recordsSeparator) {
        super();

        Check.notNull(recordsSeparator, "recordsSeparator");
        this.recordsSeparator = recordsSeparator;
    }

    @Override
    public List<PpmsSystemPrivilege> map(String input) {
        List<PpmsSystemPrivilege> result = new ArrayList<PpmsSystemPrivilege>();

        if (!empty(input)) {
            Scanner scanner = new Scanner(input);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String sanitizedLine = trimAll(line);

                PpmsSystemPrivilege systemRight = toSystemPrivilege(sanitizedLine);
                if (null != systemRight) {

                    result.add(systemRight);

                }
            }
        }

        return result;
    }

    private PpmsSystemPrivilege toSystemPrivilege(String sanitizedLine) {

        PpmsSystemPrivilege result = null;

        if (!empty(sanitizedLine)) {
            String[] lineParts = sanitizedLine.split(getRecordsSeparator().toString());

            if (null != lineParts && lineParts.length >= MIN_RECORDS_PER_LINE) {
                String privilegeCode = lineParts[0];
                String userName = lineParts[1];

                result = parseSystemPrivilege(privilegeCode, userName);
            }
        }

        return result;
    }

    private PpmsSystemPrivilege parseSystemPrivilege(String privilegeCode, String userName)
                    throws IllegalArgumentException {

        PpmsSystemPrivilege result = null;

        if (!empty(privilegeCode) && !empty(userName)) {

            // perform line parsing
            // may throw IllegalArgumentException
            PpmsPrivilege privilege = PpmsPrivilege.fromString(privilegeCode);

            // remove quotes from usernames when in CSV mode
            String login = userName.replaceAll(DOUBLE_QUOTE.toString(), "");

            // parsing successful
            result = new PpmsSystemPrivilege(login, privilege);

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
