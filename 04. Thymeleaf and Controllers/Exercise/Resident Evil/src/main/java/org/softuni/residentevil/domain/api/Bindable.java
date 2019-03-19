package org.softuni.residentevil.domain.api;

import java.io.Serializable;

/**
 * Bindable model markup interface.
 * Provides type-safety in generic methods and abstract classes
 * <p>
 * Extends {@link Viewable} as Bindable models can be requested by Controllers for edit.<br>
 * Extends {@link Serializable} (also through {@link Viewable}) as instances of implementing
 * classes could end up stored in Session
 *
 * @param <E> Entity class to which this Bindable model applies to
 */
public interface Bindable<E> extends Viewable<E>, Serializable {
}
