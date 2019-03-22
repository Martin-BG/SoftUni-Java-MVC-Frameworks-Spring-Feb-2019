package org.softuni.residentevil.repository;

import org.softuni.residentevil.domain.api.Viewable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Provides generic methods for querying of Projections
 * <p>
 * Projections are proxy objects that cannot be serialized
 * (ex. cannot be stored in HTTP session or in Redis based cache).
 * Proxies should be manually mapped to objects of {@link Serializable} class when serialization is required.
 *
 * @param <E> Entity type
 * @param <I> ID type used by the Entity
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections">Spring Data docs</a>
 */
@Validated
@NoRepositoryBean
public interface GenericRepository<E, I> extends JpaRepository<E, I> {

    <T extends Viewable<E>> Optional<T> findProjectedById(@NotNull I id, @NotNull Class<T> projection);

    <T extends Viewable<E>> List<T> findAllProjectedBy(@NotNull Class<T> projection);

    <T extends Viewable<E>> List<T> findAllProjectedBy(@NotNull Class<T> projection, @NotNull Sort sort);
}
