package org.softuni.exodia.config;

import org.hibernate.dialect.MySQL8Dialect;

public class MySQL8UnicodeDialect extends MySQL8Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
    }
}
