/**
 *
 */
package org.imagopole.ppms.api.convert;


/**
 * Factory for data converters.
 *
 * @param <S> the source data type
 * @param <T> the target data type
 *
 * @author seb
 *
 */
public interface PumapiDataConverterFactory<S, T> {

    /**
     * Introspects the source object and returns the appropriate type converter.
     *
     * @param forSourceObject the instance to be converted
     * @return the bean converter suitable for this object
     */
    PumapiDataConverter<T, ?> getConverter(S forSourceObject);

}
