package org.softuni.residentevil.domain.api;

import java.io.Serializable;

/**
 * Identifiable markup interface for entities or models.
 * Provides type-safety in generic methods and abstract classes.
 * <p>
 * Extends {@link Serializable} as instances of implementing
 * classes could end in Session storage or in Cache
 *
 * @param <I> ID type of the entity class
 */
public interface Identifiable<I> extends Serializable {

    I getId();
}
