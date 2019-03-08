package org.softuni.exodia.service;

import org.modelmapper.ModelMapper;
import org.softuni.exodia.domain.api.Bindable;
import org.softuni.exodia.domain.api.Identifiable;
import org.softuni.exodia.domain.api.Viewable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Abstract class, implements common functionality for services
 *
 * @param <E> Entity
 * @param <I> ID class used by Entity
 * @param <R> Repository for Entity
 */
abstract class BaseService<E extends Identifiable<I>, I, R extends JpaRepository<E, I>> implements Service<E, I> {

    protected final R repository;
    protected final Validator validator;
    protected final ModelMapper mapper;
    private final Class<E> entityClass;

    protected BaseService(R repository,
                          Validator validator,
                          ModelMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        entityClass = initEntityClass();
    }

    protected abstract Logger logger();

    @Override
    public final <B extends Bindable<E>> boolean create(B bindingModel) {
        return validateAndCreate(bindingModel)
                .isPresent();
    }

    @Override
    public final <B extends Bindable<E>, V extends Viewable<E>>
    Optional<V> createAndGet(B bindingModel, Class<V> viewModelClass) {
        return validateAndCreate(bindingModel)
                .map(e -> mapper.map(e, viewModelClass));
    }

    @Override
    public final <V extends Viewable<E>> Optional<V> findById(I id, Class<V> viewModelClass) {
        return repository
                .findById(id)
                .map(e -> mapper.map(e, viewModelClass));
    }

    @Override
    public final <V extends Viewable<E>> List<V> findAll(Class<V> viewModelClass) {
        return repository
                .findAll()
                .stream()
                .map(t -> mapper.map(t, viewModelClass))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(I id) {
        return deleteByIdAndGet(id)
                .isPresent();
    }

    @Override
    public <V extends Viewable<E>> Optional<V> deleteByIdAndGet(I id, Class<V> viewModelClass) {
        return deleteByIdAndGet(id)
                .map(e -> mapper.map(e, viewModelClass));
    }

    private <B extends Bindable<E>> boolean validateModel(B bindingModel) {
        Set<ConstraintViolation<B>> violations = validator.validate(bindingModel);
        if (!violations.isEmpty()) {
            String msg = "Failed validation on:\r\n\t" +
                    violations.stream()
                            .map(cv -> cv.getPropertyPath().toString()
                                    + " (" + cv.getInvalidValue() + ") " + cv.getMessage())
                            .collect(Collectors.joining("\r\n\t"));
            logger().log(Level.SEVERE, msg);
            return false;
        }
        return true;
    }

    private <B extends Bindable<E>> Optional<E> validateAndCreate(B bindingModel) {
        if (validateModel(bindingModel)) {
            return Optional.of(repository.save(mapper.map(bindingModel, entityClass)));
        }
        return Optional.empty();
    }

    private Optional<E> deleteByIdAndGet(I id) {
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
