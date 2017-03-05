package br.com.concretesolutions.audience.retrofit.exception;

public class NetworkException extends ApiException {

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
