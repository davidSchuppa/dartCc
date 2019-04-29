package com.codecool.dartcc.exception;

public class PlayerUpdateException extends Throwable {
    public PlayerUpdateException(String s) {
    }

    public PlayerUpdateException() {
    }

    public PlayerUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerUpdateException(Throwable cause) {
        super(cause);
    }

    public PlayerUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
