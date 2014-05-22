/**
 *
 */
package org.imagopole.ppms.api.convert;

/**
 * A generic data converter interface.
 *
 * @param <T> the input data type
 * @param <R> the result data type
 *
 * @author seb
 *
 */
public interface PumapiDataConverter<T, R> {

    /**
     * Performs bean mapping conversion.
     *
     * @param input the source instance
     * @return the target instance
     */
    R map(T input);

}
