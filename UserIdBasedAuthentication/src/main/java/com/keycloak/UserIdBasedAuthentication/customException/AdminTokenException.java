package com.keycloak.UserIdBasedAuthentication.customException;

public class AdminTokenException extends Exception{

    public AdminTokenException() {
        super();
    }

    public AdminTokenException(String message) {
        super(message);
    }

    public AdminTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminTokenException(Throwable cause) {
        super(cause);
    }

    protected AdminTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
