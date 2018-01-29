package org.sterl.cloud.admin.impl.db.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.sterl.cloud.admin.api.db.DbConnectionId;

public abstract class DbException extends RuntimeException {

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(String message) {
        super(message);
    }

    // https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
    @ResponseStatus(value = HttpStatus.NOT_FOUND) 
    public static class DBConnectionNotFoundException extends DbException {
        public DBConnectionNotFoundException(DbConnectionId id) {
            super("No DB connection information found with ID: " + id);
        }
    }
}
