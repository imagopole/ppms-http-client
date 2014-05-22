/**
 *
 */
package org.imagopole.ppms.impl.convert;

import static org.imagopole.ppms.util.Check.empty;

import org.csveed.api.CsvReader;
import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.dto.PpmsSystem;


/**
 * PUMAPI data converter: maps a response body to a PPMS system's attributes.
 *
 * @author seb
 *
 */
public class GetSystemCsvResponseConverter extends AbstractPpmsSystemCsvConverter
       implements PumapiDataConverter<String, PpmsSystem> {

    /**
     * Vanilla constructor.
     */
    public GetSystemCsvResponseConverter() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PpmsSystem map(String input) {
        PpmsSystem result = null;

        if (!empty(input)) {

            CsvReader<PpmsSystem> csvReader = buildCsvReader(input);
            result = csvReader.readBean();

        }

        return result;
    }

}
