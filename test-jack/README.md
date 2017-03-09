# Jack (Java Android Compiler Kit)

The new toolchain used by Android to provide Java features in a backwards compatible way to Android creates some difficulties.

First, all lambdas are implemented as generated classes (much like Retrolambda does) and then these classes implement the SAM (single abstract method) types. This feature makes it tough to match on a message type (in the `ActorStorage` map).
 
This module is a way to test that our `Utils` class can find the proper key.

See `br.com.concretesolutions.audience.core.UtilsJackTest`.