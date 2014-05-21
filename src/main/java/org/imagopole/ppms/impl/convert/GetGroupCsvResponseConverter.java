/**
 *
 */
package org.imagopole.ppms.impl.convert;

import java.io.StringReader;

import org.csveed.api.CsvReader;
import org.csveed.api.CsvReaderImpl;
import org.csveed.bean.BeanReaderInstructions;
import org.csveed.bean.BeanReaderInstructionsImpl;
import org.csveed.bean.ColumnNameMapper;
import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.dto.PpmsGroup;
import org.imagopole.ppms.util.Check;
import org.imagopole.ppms.util.PumapiUtil;


/**
 * PUMAPI data converter: maps a response body to a PPMS group's attributes.
 *
 * @author seb
 * @see java.util.Scanner
 *
 */
public class GetGroupCsvResponseConverter
       implements PumapiDataConverter<String, PpmsGroup> {

    /**
     * Vanilla constructor
     */
    public GetGroupCsvResponseConverter() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PpmsGroup map(String input) {
        PpmsGroup result = null;

        if (null != input && !input.isEmpty()) {

            CsvReader<PpmsGroup> csvReader = buildCsvReader(input);
            result = csvReader.readBean();

        }

        return result;
    }

    /**
     * @param input
     * @return
     */
    private CsvReader<PpmsGroup> buildCsvReader(String input) {
        Check.notEmpty(input, "input");

        BeanReaderInstructions instructions =
            new BeanReaderInstructionsImpl(PpmsGroup.class).setSeparator(PumapiUtil.COMMA);

        CsvReader<PpmsGroup> csvReader =
               new CsvReaderImpl<PpmsGroup>(new StringReader(input), instructions)
                   .setMapper(ColumnNameMapper.class)
                   .mapColumnNameToProperty("unitlogin", "unitlogin").setRequired("unitlogin", true)
                   .mapColumnNameToProperty("unitname", "unitname").setRequired("unitname", true)
                   .mapColumnNameToProperty("headname", "headname")
                   .mapColumnNameToProperty("heademail", "heademail")
                   .mapColumnNameToProperty("unitbcode", "unitbcode")
                   .mapColumnNameToProperty("department", "department")
                   .mapColumnNameToProperty("institution", "institution")
                   .mapColumnNameToProperty("address", "address")
                   .mapColumnNameToProperty("affiliation", "affiliation")
                   .mapColumnNameToProperty("ext", "ext").setRequired("ext", true)
                   .mapColumnNameToProperty("active", "active").setRequired("active", true);

        return csvReader;
    }

}
