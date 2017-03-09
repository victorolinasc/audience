# Retrolambda

Retrolambda is a tool for making possible the use of Java 8 lambdas in Android.

All lambdas are implemented as generated classes (much like what Jack does) and then these classes implement the SAM (single abstract method) types. This feature makes it tough to match on a message type (in the `ActorStorage` map).
 
This module is a way to test that our `Utils` class can find the proper key.

See `br.com.concretesolutions.audience.core.UtilsRetrolambdaTest`.