package com.codecool.dartcc.exception;

public class NoCheckoutFoundException extends Throwable {
    public NoCheckoutFoundException() {
    }

    public NoCheckoutFoundException(String message) {
        super(message);
    }

    public NoCheckoutFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCheckoutFoundException(Throwable cause) {
        super(cause);
    }

    public NoCheckoutFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
