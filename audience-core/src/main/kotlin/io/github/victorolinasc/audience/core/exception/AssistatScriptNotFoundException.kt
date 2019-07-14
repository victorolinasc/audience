package io.github.victorolinasc.audience.core.exception

class AssistatScriptNotFoundException(message: String) : RuntimeException("There was no assistant script for the message: $message")
