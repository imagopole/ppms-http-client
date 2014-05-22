/**
 *
 */
package org.imagopole.ppms.impl.convert;

import java.util.List;

import org.csveed.api.CsvReader;
import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.dto.PpmsSystem;


/**
 * PUMAPI data converter: maps a response body to a PPMS system's attributes.
 *
 * @author seb
 *
 */
public class GetSystemsCsvResponseConverter extends AbstractPpmsSystemCsvConverter
       implements PumapiDataConverter<String, List<PpmsSystem>> {

    /**
     * Vanilla constructor.
     */
    public GetSystemsCsvResponseConverter() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PpmsSystem> map(String input) {
        List<PpmsSystem> result = null;

        if (null != input && !input.isEmpty()) {

            CsvReader<PpmsSystem> csvReader = buildCsvReader(input);
            result = csvReader.readBeans();

        }

        return result;
    }

}
