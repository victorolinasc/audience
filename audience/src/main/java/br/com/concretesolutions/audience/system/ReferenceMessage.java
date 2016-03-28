package br.com.concretesolutions.audience.system;

public class ReferenceMessage {

    private final String reference;
    private final Object message;

    public ReferenceMessage(String reference) {
        this.reference = reference;
        this.message = null;
    }

    public ReferenceMessage(String reference, Object message) {
        this.reference = reference;
        this.message = message;
    }


    public String getReference() {
        return reference;
    }

    public Object getMessage() {
        return message;
    }
}
