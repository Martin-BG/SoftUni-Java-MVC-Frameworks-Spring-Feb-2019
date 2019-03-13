package org.softuni.exodia.service;

import org.softuni.exodia.domain.api.Bindable;
import org.softuni.exodia.domain.api.Identifiable;
import org.softuni.exodia.domain.api.Viewable;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Service interface, meant for extension by specific services
 *
 * @param <E> Entity class
 * @param <I> ID class
 */

@Validated
public interface Service<E extends Identifiable<I>, I> {

    <B extends Bindable<E>> boolean create(B bindingModel);

    <B extends Bindable<E>, V extends Viewable<E>> Optional<V> createAndGet(B bindingModel, Class<V> viewModelClass);

    <V extends Viewable<E>> Optional<V> findById(I id, Class<V> viewModelClass);

    <V extends Viewable<E>> List<V> findAll(Class<V> viewModelClass);

    boolean deleteById(I id);

    <V extends Viewable<E>> Optional<V> deleteByIdAndGet(I id, Class<V> viewModelClass);
}
