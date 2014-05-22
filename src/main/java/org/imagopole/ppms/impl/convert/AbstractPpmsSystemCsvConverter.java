/**
 *
 */
package org.imagopole.ppms.impl.convert;

import java.io.StringReader;

import org.csveed.api.CsvReader;
import org.csveed.api.CsvReaderImpl;
import org.csveed.bean.BeanReaderInstructions;
import org.csveed.bean.BeanReaderInstructionsImpl;
import org.csveed.bean.ColumnIndexMapper;
import org.imagopole.ppms.api.dto.PpmsSystem;
import org.imagopole.ppms.util.Check;
import org.imagopole.ppms.util.PumapiUtil;


/**
 * PUMAPI data converter: maps a response body to a PPMS system's attributes.
 *
 * @author seb
 *
 */
public abstract class AbstractPpmsSystemCsvConverter {

    /**
     * Vanilla constructor.
     */
    public AbstractPpmsSystemCsvConverter() {
        super();
    }

    /**
     * Builds a {@link org.csveed.api.CsvReader} configured to map the PUMAPI CSV response body (with header)
     * to a {@link org.imagopole.ppms.api.dto.PpmsSystem} object.
     *
     * @param input the CSV response body
     * @return the CSV reader
     *
     * @see org.csveed.bean.BeanProperties#mapNameToProperty(String, String)
     */
    protected CsvReader<PpmsSystem> buildCsvReader(String input) {
        Check.notEmpty(input, "input");

        BeanReaderInstructions instructions =
            new BeanReaderInstructionsImpl(PpmsSystem.class).setSeparator(PumapiUtil.COMMA);

        // note: use column index mapper as a workaround for BeanProperties name to property mapping
        // method which forces all column names to lowercase before mapping
        CsvReader<PpmsSystem> csvReader =
               new CsvReaderImpl<PpmsSystem>(new StringReader(input), instructions)
                   .setMapper(ColumnIndexMapper.class)

                   //.mapColumnNameToProperty("Core facility ref", "coreFacilityRef").setRequired("coreFacilityRef", true)
                   .mapColumnIndexToProperty(1, "coreFacilityRef").setRequired("coreFacilityRef", true)

                   //.mapColumnNameToProperty("System id", "systemId").setRequired("systemId", true)
                   .mapColumnIndexToProperty(2, "systemId").setRequired("systemId", true)

                   //.mapColumnNameToProperty("Type", "type")
                   .mapColumnIndexToProperty(3, "type")

                   //.mapColumnNameToProperty("Name", "name").setRequired("name", true)
                   .mapColumnIndexToProperty(4, "name").setRequired("name", true)

                   //.mapColumnNameToProperty("Localisation", "localisation")
                   .mapColumnIndexToProperty(5, "localisation")

                   //.mapColumnNameToProperty("Active", "active").setRequired("active", true)
                   .mapColumnIndexToProperty(6, "active").setRequired("active", true)

                   //.mapColumnNameToProperty("Schedules", "schedules")
                   .mapColumnIndexToProperty(7, "schedules")

                   //.mapColumnNameToProperty("Stats", "stats")
                   .mapColumnIndexToProperty(8, "stats")

                   //.mapColumnNameToProperty("Bookable", "bookable")
                   .mapColumnIndexToProperty(9, "bookable")

                   //.mapColumnNameToProperty("Autonomy Required", "autonomyRequired")
                   .mapColumnIndexToProperty(10, "autonomyRequired")

                   //.mapColumnNameToProperty("Autonomy Required After Hours", "autonomyRequiredAfterHours");
                   .mapColumnIndexToProperty(11, "autonomyRequiredAfterHours");

        return csvReader;
    }

}
