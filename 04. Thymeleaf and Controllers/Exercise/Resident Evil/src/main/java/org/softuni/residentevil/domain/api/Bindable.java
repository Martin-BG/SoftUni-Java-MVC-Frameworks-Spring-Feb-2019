package org.softuni.residentevil.domain.api;

/**
 * Bindable model markup interface.
 * Provides type-safety in generic methods and abstract classes<br>
 * Extends {@link Viewable} as Bindable models can be requested by Controllers for edit.
 * @param <E> Entity class to which this Bindable model applies to
 */
public interface Bindable<E> extends Viewable<E> {
}
