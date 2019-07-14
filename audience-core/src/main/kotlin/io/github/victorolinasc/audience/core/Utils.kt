package io.github.victorolinasc.audience.core


inline fun <reified T> T.messageClass(): Class<*>? {

    val messageClass = T::class.java

    return if (messageClass.isAnonymousClass
            || messageClass.simpleName.contains("-\$Lambda$")  // Jack

            || messageClass.simpleName.contains("$\$Lambda$")) messageClass.interfaces[0] else messageClass

}