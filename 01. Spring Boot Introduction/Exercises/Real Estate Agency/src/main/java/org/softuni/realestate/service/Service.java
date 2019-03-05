package org.softuni.realestate.service;

import org.softuni.realestate.domain.enities.Identifiable;
import org.softuni.realestate.domain.models.binding.Bindable;
import org.softuni.realestate.domain.models.view.Viewable;

import java.util.List;
import java.util.Optional;

public interface Service<E extends Identifiable<I>, I> {

    <B extends Bindable<E>, V extends Viewable<E>> Optional<V> createAndGet(B bindingModel, Class<V> viewModelClass);

    <V extends Viewable<E>> Optional<V> findById(I id, Class<V> viewModelClass);

    <V extends Viewable<E>> List<V> findAll(Class<V> viewModelClass);
}
