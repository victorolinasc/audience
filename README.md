# Audience

An actor-message inspired concurrent Android framework.

## What the hell is this?

Audience is about an actor system for Android. What the hell is this? Well, it is an asynchronous model made famous by [Erlang](http://erlang.org/) that inspired [Akka](http://akka.io/) and others.

The basic idea is this: actors are independent components that can only communicate with each other through passing messages.  To do that, they only need each other's references. 

So, in its most basic form it is:

``` java
actorRef.tell(message)
        .to(anotherRef);
```

With that code, we will send `message` from the actor that has reference `actorRef` to the actor that has reference `anotherRef`. This may be done asynchronously.

## Actors you say...

The concept of an actor is that of an independent concurrent (and probably parallel) entity. It does not share state with other actors and can only communicate with the outside world through messages. 

In `Audience` we are not aiming at a scrict actor implementation. We can't turn Java into a functional programming language. So what is an actor for `Audience`? Like typical Java, it is any implementation of the interface `Actor`. Let's see an example:

``` java
public final class SomeActor implements Actor {

    @Override
    public void onActorRegistered(ActorRef ref) {
        // called when you register this actor
    }
}
```

Here `SomeActor` implements our interface. It could be your `Activity`, your API client or any other class you'd like. Simple, huh? But what about that `ActorRef`?

## `ActorRef`

An actor reference (`ActorRef`) is how we interact with actors. Remember: we can't interact with it calling a method directly on the actor because it only "behaves" through message passing. This is a nice trick that enables us to avoid holding a specific instance reference. We can, therefore, avoid losing our concurrency results when an Android configuration change event occurs. The recreated `Activity` will know that a concurrent result supposed to be handled by its old instance should now be handled by the new instance.

You've noticed from the prior topic that when we register an actor we get a callback in which we are passed our own `ActorRef`. Through it we can populate our actor callback storage. That is how we process messages. Here is an example using Java 8 method references:

``` java
public final class SomeActor implements Actor {
    @Override
    public void onActorRegistered(ActorRef ref) {
        ref.passScript(ApiCall.class, this::executeCall);
    }

    void executeCall(ApiCall message, ActorRef sender, ActorRef self) {
        // do something with message and get an instance of Result
        Result result = //...

        // use message passing to reply to whoever has sent us this message
        self.tell(result)
            .to(sender);
    }
}
```

In this case, if anyone sends a message of type `ApiCall` to this actor, it will call the `executeCall` callback. Here is an example:

``` java
// from some place outside SomeActor
anActorRef.tell(new ApiCall())
    .to(someActorRef);
```

By default this will be processed ***in a background thread***. If we wanted it to be processed on UI thread we could add `onStage` call. Here is an example:

``` java
// from some place outside SomeActor
anActorRef.tell(new ApiCall())
    .onStage() // handle message on UI thread!!!
    .to(someActorRef);
```

In this example, `ApiCall` is a simple class, but it could be any instance in your application. You only must be certain that the receiving actor has registered a callback to that type.

Our callback is an implicit implementation of the `Script` interface. A script can have one, two or three arguments: 

1. The message instance
2. The sender reference
3. Our own self reference

So, other than doing something with the message, we can reply to whoever has sent us the message using our reference and the "sender" reference. There is no restriction to what can be sent back: it could be an exception, a different message or anything you'd want to.

Actually, there are two types of messages and, therefore, scripts: assistant scripts and normal scripts.

## Assistant scripts

When you don't have a type for a message, say, when you want to send a message just to trigger some behaviour, you can use `AssistantScript`. The keys to trigger those scripts are simple strings. So you could use:

``` java
public final class SomeActor implements Actor {
    @Override
    public void onActorRegistered(ActorRef ref) {
        ref.passScript(ApiCall.class, this::executeCall)
                .passAssistantScript("load", this::showLoading); // ASSISTANT!
    }

    void showLoading(ActorRef sender, ActorRef self) {
        // show loading
    }
}
```

And to trigger that, we could call:

``` java
// from some place outside SomeActor
anActorRef.tell("load")
    .onStage() // handle message on UI thread!!!
    .to(someActorRef);
```

Assistant scripts can use none up to two arguments:
 
0. You don't want to reply or do anything else other than execute your callback.
1. The sender reference.
2. Our own reference

## Actor registration and scope

Actors must have a scope. If they didn't, we would leak memory everywhere. Scopes define for how long an actor should be registered and its callbacks kept. They are needed when you register an actor.
  
There are two default scopes currently (with another one coming next):

- Application: like a singleton scope
- Activity: follows the Activity lifecycle

To register an actor you must either pass a scope reference or an instance which its scope can be inferred. Example:
 
``` java
// ApiClient is registered as a singleton actor
actorRegistry.enroll(customApplication, new ApiClient());
```

Singleton actors are usually registered in the method `Application.onCreate()`. For instance:

``` java
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Director.beginShow(this)
                .enroll(this, new ApiClient());
    }
}
```

We will talk about `Director` class in a minute, but in this example we are bootstrapping the system and registering (enrolling) the `ApiClient` instance with the application scope.

All activities might be actors too. If you want your `Activity` to be an actor, all you need to do is to implement the `Actor` interface. Its registration is automatically and the framework handles its lifecycle automatically too. An example:

``` java
public final class SampleActivity extends AppCompatActivity implements Actor {

    // onCreate and etc...

    // this will be called upon Activity creation
    @Override
    public void onActorRegistered(ActorRef ref) {
        ref.passAssistantScript("load", this::showLoading);
    }
    
    void showLoading() {
        // do something :)
    }
}
```

## `Director`: managing the whole show

We've seen in the previous section that there is a class `Director`. This class has the callbacks to interact with all parts of `Audience`. It must be used to start the show, end it, handle configuration changes and getting actor references. To start using audience you will need a custom `Application` class with some `Director` calls. Example:

``` java
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Director.beginShow(this);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // MUST BE CALLED BEFORE super CALL
        Director.onConfigurationChanged();
        super.onConfigurationChanged(newConfig);
    } 
    
    @Override
    public void onTerminate() {
        Director.endShow();
        super.onTerminate();
    }    
}
```

After that you can also use `Director` to find actor references. For example:

``` java
public final class SampleActivity extends AppCompatActivity implements Actor {

    private void someMethod() {
        Director.actorRef(this) // returns an ActorRef that represents this activity actor
            .tell(someMessage)
            .toActor(ApiClient.class); // singleton actors can be called through its class
    }
}
```

There are other parts of the system that can be interacted with. Please, see at `Director` javadocs.

## Show rules

Now we have all parts for our concurrency model: actors, their references, scopes and thread handling. What we've left out is `ShowRule` which is a message filter. They can be registered globally to handle script and assistant script messages.

A good use case for filters might be logging. You want to debug all your logging messages passing through. This can easily be done with the following filter:
  

``` java
public final class LoggingShowRule implements AssistantAndScriptRule {

    private static final String TAG = LoggingShowRule.class.getSimpleName();

    @Override
    public boolean shouldIntercept(String assistantKey) {
        return true;
    }

    @Override
    public boolean shouldIntercept(Class<?> messageClass) {
        return true;
    }

    @Override
    public MessageEvent<String> interceptAssistantMessage(MessageEvent<String> message) {
        Log.i(TAG, "Intercepted assistant message: " + message);
        return message;
    }


    @Override
    public MessageEvent<?> interceptScriptMessage(MessageEvent<?> message) {
        Log.i(TAG, "Intercepted script message: " + message);
        return message;
    }
}
```

Then we need to register it with our `RuleRegistry`:

``` java
Director.getRuleRegistry().addAssistantAndScriptRule(new LoggingShowRule());
```

This rule intercepts assistant and script messages. There are also `AssistantRule` and `ScriptRule` if you want more control to which messages your should intercept. Both of them have a `shouldIntercept` method to determine the message should be handled.

## Features

This is not an attempt to fully provide all the features that Erlang has. 

This is a very minimalistic system aiming at providing an Android ***SPECIFIC*** implementation. 
So, no supervisors, code reload, PIDs and OTP. That is sad I know. But the goal is to have an easy 
asynchronous programming model that will include:

- Message passing through IO and UI threads

``` java
actorRef.tell(message)
        .onStage() // look ma, send this to UI
        .to(anotherReference);
```

- Configuration change safe (main goal here). Yes, fire a request, change your orientation as many times as you would want and have your response correctly delivered without extra efforts. 
- Convention over configuration where possible. No need to subscribe or unsubscribe on `Activity`s
- Bi-directional communication model. You always know who sent a message and can promptly reply to that specific sender. No need to pass instances down your stack.
- Based on Android's concurrent primitives (`Handler`s, `MessageQueue`s and the like).

Other features might be added like *behaviours*.

## LICENSE

APL 2.0

See [LICENSE](LICENSE)