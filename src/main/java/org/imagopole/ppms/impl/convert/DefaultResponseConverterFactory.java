/**
 *
 */
package org.imagopole.ppms.impl.convert;

import org.imagopole.ppms.api.PumapiRequest;
import org.imagopole.ppms.api.PumapiRequest.Action;
import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.convert.PumapiResponseConverterFactory;
import org.imagopole.ppms.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
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
        Check.notNull(action, "action");

        //TODO: take this into account when PUMAPI fully supports it
        boolean isNoHeaders = forSourceObject.isNoHeaders();

        PumapiDataConverter<String, ?> result = null;

        switch (action) {

            case Authenticate:
                result = new AuthCsvResponseConverter();
                break;

            case GetGroup:
                result = new GetGroupCsvResponseConverter();
                break;

            case GetUser:
                result = new GetUserCsvResponseConverter();
                break;

            case GetUserRights:
                result = new GetUserRightsColonResponseConverter();
                break;

            case GetUsers:
                result = new GetUsersCsvResponseConverter();
                break;

            case GetSystem:
                result = new GetSystemCsvResponseConverter();
                break;

            default:
                throw new IllegalStateException(
                    String.format("No response converter for PUMAPI request with action: %s and format: %s",
                                  action, forSourceObject.getResponseFormat()));
        }

        log.debug("[pumapi] Using response converter: {} for request action: {} [noheader:{}]",
                  result.getClass().getSimpleName(), action, isNoHeaders);

        return result;
    }

}
