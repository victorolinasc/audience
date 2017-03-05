package br.com.concretesolutions.audience.retrofit.calladapter;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.retrofit.RetrofitActor;
import retrofit2.Call;

public final class MessageCall<T> {

    private final Call<T> call;
    private final boolean isResponse;
    private ActorRef replyTo;
    private boolean replyOnStage = true;

    public MessageCall(Call<T> call, boolean isResponse) {
        this.call = call;
        this.isResponse = isResponse;
    }

    public MessageCall<T> replyOnStage(boolean onStage) {
        this.replyOnStage = onStage;
        return this;
    }

    public MessageCall<T> replyTo(ActorRef ref) {
        this.replyTo = ref;
        return this;
    }

    public void tell() {
        replyTo.tell(this).to(Director.actorRef(RetrofitActor.class));
    }

    public Call<T> getCall() {
        return call;
    }

    public ActorRef getReplyTo() {
        return replyTo;
    }

    public boolean isReplyOnStage() {
        return replyOnStage;
    }

    public boolean isResponse() {
        return isResponse;
    }
}
