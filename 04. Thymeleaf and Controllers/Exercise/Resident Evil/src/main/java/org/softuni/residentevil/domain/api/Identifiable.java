package org.softuni.residentevil.domain.api;

/**
 * Identifiable markup interface for entities or models.
 * Provides type-safety in generic methods and abstract classes.
 *
 * @param <I> ID type of the entity class
 */
public interface Identifiable<I> {

    I getId();
}
