package org.softuni.residentevil.domain.api;

import java.io.Serializable;

/**
 * Viewable model markup interface.
 * Provides type-safety in generic methods and abstract classes
 * <p>
 * Extends {@link Serializable} as instances of implementing
 * classes could end up stored in Session
 *
 * @param <E> Entity class to which this Viewable model applies to
 */
public interface Viewable<E> extends Serializable {
}
