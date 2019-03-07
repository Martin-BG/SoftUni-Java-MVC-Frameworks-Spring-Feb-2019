package org.softuni.exodia.domain.entities;

import org.softuni.exodia.domain.api.Identifiable;

/**
 * Base Entity class
 * Defines equals() and hashCode() methods according
 * to best practices suggested by Vlad Mihalcea
 * @see <a href="https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/">The best way to implement equals, hashCode, and toString with JPA and Hibernate</a>
 */
abstract class BaseEntity<I> implements Identifiable<I> {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        return getId() != null && getId().equals(((Identifiable) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
