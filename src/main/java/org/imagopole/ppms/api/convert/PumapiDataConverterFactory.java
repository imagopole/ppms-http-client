/**
 *
 */
package org.imagopole.ppms.api.convert;


/**
 * Factory for data converters.
 *
 * @author seb
 *
 */
public interface PumapiDataConverterFactory<S, T> {

    PumapiDataConverter<T, ?> getConverter(S forSourceObject);

}
