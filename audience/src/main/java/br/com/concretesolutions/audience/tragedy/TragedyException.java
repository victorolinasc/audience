package br.com.concretesolutions.audience.tragedy;

public class TragedyException extends RuntimeException {

    public TragedyException() {
    }

    public TragedyException(String detailMessage) {
        super(detailMessage);
    }

    public TragedyException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TragedyException(Throwable throwable) {
        super(throwable);
    }
}
