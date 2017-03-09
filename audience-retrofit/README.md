# Audience Retrofit

This is a helper actor module for Retrofit. It provides:

- `CallAdapterFactory` for your APIs interfaces
- A `SingletonActor` implementation that handles `MessageCall` instances
- High level response handling

An example usage is in the Audience Sample.

## Setup

First add the call adapter factory to your Retrofit Builder:

```java
@NonNull
private Retrofit getRetrofit(String baseUrl, OkHttpClient client) {
    return new Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            // THIS LINE!!!
            .addCallAdapterFactory(AudienceCallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build();
}
```

Then make your calls return `MessageCall<T>`:

```java
@GET("search/repositories")
MessageCall<PageResultVO<RepositoryVO>> getRepositories(
        @Query("q") String languageKeyValue,
        @Query("sort") Sort sort,
        @Query("page") int page,
        @Query("per_page") int perPage);
```

Done! ;)

## Usage

After that, when you have an instance of your Retrofit proxy, you can call methods on it and use the returned `MessageCall` to send messages to RetrofitActor.

```java
ApiProvider.getApi() // simple singleton providing our Proxy
        .getRepositories("language:java", Api.Sort.stars, pageNo, 30)
        .replyTo(activityRef) // could be any ref
        .tell(); // fire the call away
```

## Architecture

This module has an `ApplicationActor` implementation: `RetrofitActor`. This class is registered with the `Director` once the `CallAdapterFactory.create()` method is called.
 
The message it receives is a `MessageCall<T>` populated by the Retrofit proxy. This instance needs an `ActorRef` to reply back with the result of the API call. 