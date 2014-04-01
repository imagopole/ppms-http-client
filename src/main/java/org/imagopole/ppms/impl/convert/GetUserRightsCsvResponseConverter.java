/**
 *
 */
package org.imagopole.ppms.impl.convert;

import static org.imagopole.ppms.util.PumapiUtil.trimAll;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.dto.PumapiParams.PpmsSystemPrivilege;

/**
 * PUMAPI data converter: maps a response body to an indexed list of intruments identifiers
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
public class GetUserRightsCsvResponseConverter
       implements PumapiDataConverter<String, Map<PpmsSystemPrivilege, List<Long>>> {

    /** Record separator for the <code>getuserrights</code> API call */
    private final static String RECORDS_SEPARATOR= ":";

    /** The <code>getuserrights</code> API call currently provides a tuple per line */
    private final static int MIN_RECORDS_PER_LINE = 2;

    /**
     * Vanilla constructor
     */
    public GetUserRightsCsvResponseConverter() {
        super();
    }

    @Override
    public Map<PpmsSystemPrivilege, List<Long>> map(String input) {
        Map<PpmsSystemPrivilege, List<Long>> result =
            new EnumMap<PpmsSystemPrivilege, List<Long>>(PpmsSystemPrivilege.class);

        if (null != input && !input.isEmpty()) {
            Scanner scanner = new Scanner(input);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String sanitizedLine = trimAll(line);

                convertLine(result, sanitizedLine);
            }
        }

        return result;
    }

    private void convertLine(
            Map<PpmsSystemPrivilege, List<Long>> result,
            String sanitizedLine) {

        if (null != sanitizedLine && !sanitizedLine.isEmpty()) {
            String[] lineParts = sanitizedLine.split(RECORDS_SEPARATOR);

            if (null != lineParts && lineParts.length >= MIN_RECORDS_PER_LINE) {
                String privilegeCode = lineParts[0];
                String instrumentId = lineParts[1];

                convertLineParts(result, privilegeCode, instrumentId);
            }
        }
    }

    private void convertLineParts(
            Map<PpmsSystemPrivilege, List<Long>> result,
            String privilegeCode,
            String instrumentId) throws IllegalArgumentException, NumberFormatException {

        if (null != privilegeCode && null != instrumentId
            && !privilegeCode.isEmpty() && !instrumentId.isEmpty()) {

            // perform line parsing
            // may throw IllegalArgumentException
            PpmsSystemPrivilege privilege =
                PpmsSystemPrivilege.fromString(privilegeCode);

            // may throw NumberFormatException
            Long systemId = Long.parseLong(instrumentId);

            // parsing successful
            addResult(result, privilege, systemId);
        }
    }

    private void addResult(
            Map<PpmsSystemPrivilege, List<Long>> result,
            PpmsSystemPrivilege privilege,
            Long instrumentId) {

        List<Long> systemsForPrivilege = null;

        if (result.containsKey(privilege)) {
            systemsForPrivilege = result.get(privilege);
        } else {
            systemsForPrivilege = new ArrayList<Long>();
            result.put(privilege, systemsForPrivilege);
        }

        systemsForPrivilege.add(instrumentId);
    }

}
