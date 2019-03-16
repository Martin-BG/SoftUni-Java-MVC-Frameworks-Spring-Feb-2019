package org.softuni.residentevil.service;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.api.Bindable;
import org.softuni.residentevil.domain.api.Identifiable;
import org.softuni.residentevil.domain.api.Viewable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Abstract class, implements common functionality for services
 *
 * @param <E> Entity
 * @param <I> ID class used by Entity
 * @param <R> Repository for Entity
 */

@Validated
@Transactional
abstract class BaseService<E extends Identifiable<I>, I, R extends JpaRepository<E, I>> implements Service<E, I> {

    protected final R repository;
    protected final ModelMapper mapper;
    private final Class<E> entityClass;

    protected BaseService(R repository,
                          ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        entityClass = initEntityClass();
    }

    protected abstract Logger logger();

    @Override
    public <B extends Bindable<E>> void create(@NotNull @Valid B bindingModel) {
        repository.save(mapper.map(bindingModel, entityClass));
    }

    @Override
    public <B extends Bindable<E>, V extends Viewable<E>>
    V createAndGet(@NotNull @Valid B bindingModel, @NotNull Class<V> viewModelClass) {
        E entity = repository.save(mapper.map(bindingModel, entityClass));
        return mapper.map(entity, viewModelClass);
    }

    @Override
    @Transactional(readOnly = true)
    public <V extends Viewable<E>> Optional<V> findById(@NotNull I id, @NotNull Class<V> viewModelClass) {
        return repository
                .findById(id)
                .map(e -> mapper.map(e, viewModelClass));
    }

    @Override
    @Transactional(readOnly = true)
    public <V extends Viewable<E>> List<V> findAll(@NotNull Class<V> viewModelClass) {
        return repository
                .findAll()
                .stream()
                .map(t -> mapper.map(t, viewModelClass))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(@NotNull I id) {
        return deleteByIdAndGet(id)
                .isPresent();
    }

    @Override
    public <V extends Viewable<E>> Optional<V> deleteByIdAndGet(@NotNull I id, @NotNull Class<V> viewModelClass) {
        return deleteByIdAndGet(id)
                .map(e -> mapper.map(e, viewModelClass));
    }

    private Optional<E> deleteByIdAndGet(@NotNull I id) {
        return repository
                .findById(id)
                .map(e -> {
                    repository.delete(e);
                    return e;
                });
    }

    @SuppressWarnings("unchecked")
    private Class<E> initEntityClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
