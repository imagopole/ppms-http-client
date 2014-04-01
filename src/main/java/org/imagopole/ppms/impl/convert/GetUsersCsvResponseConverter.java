/**
 *
 */
package org.imagopole.ppms.impl.convert;

import static org.imagopole.ppms.util.PumapiUtil.trimAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.imagopole.ppms.api.convert.PumapiDataConverter;

/**
 * PUMAPI data converter: maps a response body to a list
 * of character strings on a line per line basis (uses a default-configured
 * <code>java.util.Scanner</code> internally).
 *
 * The returned lines are pre-processed: trailing space and line endings
 * are trimmed.
 *
 * @author seb
 * @see java.util.Scanner
 *
 */
public class GetUsersCsvResponseConverter
       implements PumapiDataConverter<String, List<String>> {

    /**
     * Vanilla constructor
     */
    public GetUsersCsvResponseConverter() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> map(String input) {
        List<String> result = new ArrayList<String>();

        if (null != input && !input.isEmpty()) {
            Scanner scanner = new Scanner(input);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String sanitizedLine = trimAll(line);

                if (null != sanitizedLine && !sanitizedLine.isEmpty()) {
                    result.add(sanitizedLine);
                }
            }
        }

        return result;
    }

}
