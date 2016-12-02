package br.com.concretesolutions.audience.core.exception;

public class TragedyException extends RuntimeException {

    public TragedyException() {
    }

    public TragedyException(String message) {
        super(message);
    }

    public TragedyException(String message, Throwable cause) {
        super(message, cause);
    }

    public TragedyException(Throwable cause) {
        super(cause);
    }

    public TragedyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
