package org.sterl.cloudadmin.connector.exception;

import lombok.Getter;

public class ConnectorException extends Exception {
    @Getter
    protected final Object value;
    
    
    public ConnectorException(String message, Object value) {
        super(message);
        this.value = value;
    }
    public ConnectorException(String message, Object value, Exception cause) {
        super(message, cause);
        this.value = value;
    }
    
    public static class NotSupportedException extends ConnectorException {
        public NotSupportedException(String methodName, Class connector) {
            super("The method " + methodName + " isn't supported by the connector " + connector.getName(), methodName);
        }
    }

    public static class UnknownResourceException extends ConnectorException {
        public UnknownResourceException(String value) {
            super("The resource " + value + " is unknown.", value);
        }
    }
}