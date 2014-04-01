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
import org.imagopole.ppms.api.dto.PpmsUser;
import org.imagopole.ppms.util.Check;
import org.imagopole.ppms.util.PumapiUtil;


/**
 * PUMAPI data converter: maps a response body to a PPMS user attributes.
 *
 * @author seb
 * @see CsvReader
 *
 */
public class GetUserCsvResponseConverter
       implements PumapiDataConverter<String, PpmsUser> {

    /**
     * Vanilla constructor
     */
    public GetUserCsvResponseConverter() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PpmsUser map(String input) {
        PpmsUser result = null;

        if (null != input && !input.isEmpty()) {

            CsvReader<PpmsUser> csvReader = buildCsvReader(input);
            result = csvReader.readBean();

        }

        return result;
    }

    /**
     * @param input
     * @return
     */
    private CsvReader<PpmsUser> buildCsvReader(String input) {
        Check.notEmpty(input, "input");

        BeanReaderInstructions instructions =
            new BeanReaderInstructionsImpl(PpmsUser.class).setSeparator(PumapiUtil.COMMA);

        CsvReader<PpmsUser> csvReader =
               new CsvReaderImpl<PpmsUser>(new StringReader(input), instructions)
                   .setMapper(ColumnNameMapper.class)
                   .mapColumnNameToProperty("login", "login").setRequired("login", true)
                   .mapColumnNameToProperty("lname", "lname").setRequired("lname", true)
                   .mapColumnNameToProperty("fname", "fname").setRequired("fname", true)
                   .mapColumnNameToProperty("email", "email").setRequired("email", true)
                   .mapColumnNameToProperty("phone", "phone")
                   .mapColumnNameToProperty("bcode", "bcode")
                   .mapColumnNameToProperty("affiliation", "affiliation")
                   .mapColumnNameToProperty("unitlogin", "unitlogin").setRequired("unitlogin", true)
                   .mapColumnNameToProperty("mustchpwd", "mustchpwd")
                   .mapColumnNameToProperty("mustchbcode", "mustchbcode")
                   .mapColumnNameToProperty("active", "active").setRequired("active", true);

        return csvReader;
    }

}
