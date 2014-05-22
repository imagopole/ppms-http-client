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
public class StringListCsvResponseConverter
       implements PumapiDataConverter<String, List<String>> {

    /**
     * Vanilla constructor.
     */
    public StringListCsvResponseConverter() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> map(String input) {
        List<String> result = new ArrayList<String>();

        if (!empty(input)) {
            Scanner scanner = new Scanner(input);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String sanitizedLine = trimAll(line);

                if (!empty(sanitizedLine)) {
                    result.add(sanitizedLine);
                }
            }
        }

        return result;
    }

}
