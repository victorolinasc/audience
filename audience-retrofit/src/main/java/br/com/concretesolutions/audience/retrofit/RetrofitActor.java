package br.com.concretesolutions.audience.retrofit;


import java.io.IOException;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.core.actor.SingletonActor;
import br.com.concretesolutions.audience.core.script.Script3;
import br.com.concretesolutions.audience.retrofit.calladapter.MessageCall;
import br.com.concretesolutions.audience.retrofit.exception.ClientException;
import br.com.concretesolutions.audience.retrofit.exception.NetworkException;
import br.com.concretesolutions.audience.retrofit.exception.ServerException;
import retrofit2.Response;

public final class RetrofitActor implements SingletonActor {

    @Override
    public void onActorRegistered(ActorRef thisRef) {
        thisRef.passScript(MessageCall.class, new Script3<MessageCall>() {
            @Override
            public void receive(MessageCall message, ActorRef sender, ActorRef self) {
                handleCall(message, sender, self);
            }
        });
    }

    private void handleCall(MessageCall<?> call, ActorRef sender, ActorRef self) {

        try {
            final Response<?> response = call.getCall().execute();

            if (response.isSuccessful()) {
                self.tell(call.isResponse() ? response : response.body())
                        .onStage(call.isReplyOnStage())
                        .to(sender);
                return;
            }

            final int code = response.code();
            final String body = response.errorBody().string();

            if (code >= 400 && code < 500)
                self.tell(new ClientException(code, body))
                        .onStage(call.isReplyOnStage())
                        .to(sender);

            else
                self.tell(new ServerException(code, body))
                        .onStage(call.isReplyOnStage())
                        .to(sender);

        } catch (IOException e) {
            self.tell(new NetworkException("IO exception on API call", e))
                    .onStage(call.isReplyOnStage())
                    .to(sender);
        }

    }

//    private <R> void handleCall(ApiCall<T, R> call, ActorRef sender, ActorRef self) {
//
//        final Call<R> result = call.call(apiProxy);
//
//        try {
//            final Response<R> response = result.execute();
//
//            if (response.isSuccessful()) {
//                self.tell(response.body()).onStage().to(sender);
//                return;
//            }
//
//            final int code = response.code();
//            final String body = response.errorBody().string();
//
//            if (code >= 400 && code < 500)
//                self.tell(new ClientException(code, body))
//                        .onStage()
//                        .to(sender);
//
//            else
//                self.tell(new ServerException(code, body))
//                        .onStage()
//                        .to(sender);
//
//        } catch (IOException e) {
//            self.tell(new NetworkException("IO exception on API call", e))
//                    .onStage()
//                    .to(sender);
//        }
//    }
}
