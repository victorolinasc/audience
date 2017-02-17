package br.com.concretesolutions.audience.sample.api.exception;

public class ServerException extends ApiException {

    private final int code;
    private final String body;

    public ServerException(int code, String body) {
        this.code = code;
        this.body = body;
    }
}
