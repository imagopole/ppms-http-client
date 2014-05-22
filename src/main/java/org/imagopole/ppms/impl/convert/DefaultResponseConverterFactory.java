/**
 *
 */
package org.imagopole.ppms.impl.convert;

import static org.imagopole.ppms.util.Check.empty;
import static org.imagopole.ppms.util.PumapiUtil.COLON;
import static org.imagopole.ppms.util.PumapiUtil.COMMA;

import org.imagopole.ppms.api.PumapiRequest;
import org.imagopole.ppms.api.PumapiRequest.Action;
import org.imagopole.ppms.api.PumapiRequest.Filter;
import org.imagopole.ppms.api.PumapiRequest.Format;
import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.convert.PumapiResponseConverterFactory;
import org.imagopole.ppms.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic implementation for type converters lookup based on the request metedata.
 *
 * @author seb
 *
 */
public class DefaultResponseConverterFactory implements PumapiResponseConverterFactory {

    /** Application logs. */
    private final Logger log = LoggerFactory.getLogger(DefaultResponseConverterFactory.class);

    /**
     * Vanilla constructor.
     */
    public DefaultResponseConverterFactory() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PumapiDataConverter<String, ?> getConverter(PumapiRequest forSourceObject) {
        Check.notNull(forSourceObject, "forSourceObject");

        Action action = forSourceObject.getAction();
        Format format = forSourceObject.getResponseFormat();
        Check.notNull(action, "action");

        //TODO: take this into account when PUMAPI fully supports it
        boolean isNoHeaders = forSourceObject.isNoHeaders();
        boolean isCsv = (null != format && Format.csv.equals(format));

        PumapiDataConverter<String, ?> result = null;

        switch (action) {

            case Authenticate:
                result = new AuthCsvResponseConverter();
                break;

            case GetGroup:
                result = new GetGroupCsvResponseConverter();
                break;

            case GetGroups:
                result = new StringListCsvResponseConverter();
                break;

            case GetUser:
                result = new GetUserCsvResponseConverter();
                break;

            case GetUsers:
                result = new StringListCsvResponseConverter();
                break;

            case GetUserRights:
                if (isCsv) {
                    result = new GetUserRightsResponseConverter(COMMA);
                } else {
                    result = new GetUserRightsResponseConverter(COLON);
                }
                break;

            case GetSystems:
                String systemIdFilter = forSourceObject.getFilterValue(Filter.Id);

                if (empty(systemIdFilter)) {
                    // no filter: all systems requested
                    result = new GetSystemsCsvResponseConverter();
                } else {
                    result = new GetSystemCsvResponseConverter();
                }
                break;

            case GetSystemRights:
                if (isCsv) {
                    result = new GetSystemRightsResponseConverter(COMMA);
                } else {
                    result = new GetSystemRightsResponseConverter(COLON);
                }
                break;

            default:
                throw new IllegalStateException(
                    String.format("No response converter for PUMAPI request with action: %s and format: %s",
                                  action, forSourceObject.getResponseFormat()));
        }

        log.debug("[pumapi] Using response converter: {} for request action: {} [noheader:{} - format:{}]",
                  result.getClass().getSimpleName(), action, isNoHeaders, format);

        return result;
    }

}
