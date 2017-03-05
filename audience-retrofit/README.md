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
ApiProvider.getApi()
        .getRepositories("language:java", Api.Sort.stars, pageNo, 30)
        .replyTo(activityRef)
        .tell();
```
