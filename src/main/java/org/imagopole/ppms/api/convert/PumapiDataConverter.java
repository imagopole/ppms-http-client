/**
 *
 */
package org.imagopole.ppms.api.convert;

/**
 * A generic data converter interface.
 *
 * @author seb
 *
 */
public interface PumapiDataConverter<T, R> {

    R map(T input);

}
