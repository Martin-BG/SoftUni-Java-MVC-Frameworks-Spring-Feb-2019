package org.softuni.realestate.service;

import org.modelmapper.ModelMapper;
import org.softuni.realestate.domain.enities.Identifiable;
import org.softuni.realestate.domain.models.binding.Bindable;
import org.softuni.realestate.domain.models.view.Viewable;
import org.springframework.data.repository.CrudRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

abstract class BaseService<E extends Identifiable<I>, I, R extends CrudRepository<E, I>> implements Service<E, I> {

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

    public final <B extends Bindable<E>> boolean create(B bindingModel) {
        return validateAndCreate(bindingModel).isPresent();
    }

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
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(t -> mapper.map(t, viewModelClass))
                .collect(Collectors.toList());
    }

    private <B extends Bindable<E>> Optional<E> validateAndCreate(B bindingModel) {
        if (validateModel(bindingModel)) {
            return Optional.of(repository.save(mapper.map(bindingModel, entityClass)));
        }
        return Optional.empty();
    }

    protected final <M extends Bindable<E>> boolean validateModel(M model) {
        Set<ConstraintViolation<M>> violations = validator.validate(model);
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

    @SuppressWarnings("unchecked")
    private Class<E> initEntityClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
