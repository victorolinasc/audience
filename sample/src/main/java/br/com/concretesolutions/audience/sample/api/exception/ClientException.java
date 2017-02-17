package br.com.concretesolutions.audience.sample.api.exception;

public class ClientException extends ApiException {

    private final int code;
    private final String body;

    public ClientException(int code, String body) {
        this.code = code;
        this.body = body;
    }
}
