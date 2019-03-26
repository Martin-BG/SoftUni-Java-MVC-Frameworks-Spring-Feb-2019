package org.softuni.residentevil.config;

import org.hibernate.dialect.MySQL8Dialect;

/**
 * Custom MySQL dialect settings:
 * <ul>
 * <li>engine: InnoDB</li>
 * <li>charset: utf8mb4</li>
 * <li>collate: utf8mb4_unicode_ci</li>
 * </ul>
 * <p>
 * Set in application.properties:
 * <p>
 * spring.jpa.properties.hibernate.dialect=my.package.MySQL8UnicodeDialect
 */
public class MySQL8UnicodeDialect extends MySQL8Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
    }
}
