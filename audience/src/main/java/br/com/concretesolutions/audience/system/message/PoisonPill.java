package br.com.concretesolutions.audience.system.message;

public final class PoisonPill {

    private final String reason;

    private final Exception e;

    public PoisonPill(String reason, Exception e) {
        this.reason = reason;
        this.e = e;
    }

    public String getReason() {
        return reason;
    }

    public Exception getE() {
        return e;
    }
}
