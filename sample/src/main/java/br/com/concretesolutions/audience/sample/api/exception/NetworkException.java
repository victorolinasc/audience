package br.com.concretesolutions.audience.sample.api.exception;

public class NetworkException extends ApiException {

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
