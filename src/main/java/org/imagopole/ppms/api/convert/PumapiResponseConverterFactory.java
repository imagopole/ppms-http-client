/**
 *
 */
package org.imagopole.ppms.api.convert;

import org.imagopole.ppms.api.PumapiRequest;

/**
 * A factory for conversion of PUMAPI responses based on request parameters.
 *
 * @author seb
 *
 */
public interface PumapiResponseConverterFactory
       extends PumapiDataConverterFactory<PumapiRequest, String> {

}
