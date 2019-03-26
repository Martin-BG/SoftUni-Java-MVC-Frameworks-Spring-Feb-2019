package org.softuni.residentevil.service;

import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.api.Identifiable;
import org.softuni.residentevil.domain.api.Viewable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Service interface, meant for extension by specific services
 *
 * @param <E> Entity class
 * @param <I> ID class
 */

public interface Service<E extends Identifiable<I>, I> {

    <B extends Bindable<E>> void create(@NotNull @Valid B bindingModel);

    <B extends Bindable<E>, V extends Viewable<E>>
    V createAndGet(@NotNull @Valid B bindingModel, @NotNull Class<V> viewModelClass);

    <V extends Viewable<E>> Optional<V> findById(@NotNull I id, @NotNull Class<V> viewModelClass);

    <V extends Viewable<E>> List<V> findAll(@NotNull Class<V> viewModelClass);

    boolean deleteById(@NotNull I id);

    <V extends Viewable<E>> Optional<V> deleteByIdAndGet(@NotNull I id, @NotNull Class<V> viewModelClass);
}
