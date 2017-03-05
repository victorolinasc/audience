package br.com.concretesolutions.audience.retrofit.calladapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class AudienceCallAdapterFactory extends CallAdapter.Factory {

    public static AudienceCallAdapterFactory create() {
        return new AudienceCallAdapterFactory();
    }

    private AudienceCallAdapterFactory() {
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        if (getRawType(returnType) != MessageCall.class) {
            return null;
        }

        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("MessageCall return type must be parameterized"
                    + " as MessageCall<Foo> or MessageCall<? extends Foo>");
        }

        final Type innerType = getParameterUpperBound(0, (ParameterizedType) returnType);

        final boolean isResponse = getRawType(innerType) == Response.class;
        return new MessageCallAdapter<>(innerType, isResponse);
    }

    private static final class MessageCallAdapter<R> implements CallAdapter<R, MessageCall<R>> {

        private final Type responseType;
        private final boolean isResponse;

        MessageCallAdapter(Type responseType, boolean isResponse) {
            this.responseType = responseType;
            this.isResponse = isResponse;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public MessageCall<R> adapt(final Call<R> call) {
            return new MessageCall<>(call, isResponse);
        }
    }
}
