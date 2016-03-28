package br.com.concretesolutions.audience.system;

import br.com.concretesolutions.audience.script.api.AssistantScript1;
import br.com.concretesolutions.audience.script.api.AssistantScript2;
import br.com.concretesolutions.audience.script.api.AssistantScript3;
import br.com.concretesolutions.audience.script.api.Script1;
import br.com.concretesolutions.audience.script.api.Script2;
import br.com.concretesolutions.audience.script.api.Script3;

public interface ActorRef {

    int NO_DISCRIMINATOR = -1;

    ActorRef tell(Object message);

    ActorRef tell(Object message, int discriminator);

    ActorRef tell(Object message, ActorRef sender);

    ActorRef tell(Object message, int discriminator, ActorRef sender);

    ActorRef tellOnStage(Object message);

    ActorRef tellOnStage(Object message, int discriminator);

    ActorRef tellOnStage(Object message, ActorRef sender);

    ActorRef tellOnStage(Object message, int discriminator, ActorRef sender);

    ActorRef tellAssistant(String reference);

    ActorRef tellAssistant(String reference, ActorRef sender);

    ActorRef tellAssistant(String reference, int discriminator, ActorRef sender);

    ActorRef tellAssistant(String reference, Object message, int discriminator, ActorRef sender);

    ActorRef tellAssistantOnStage(String reference);

    ActorRef tellAssistantOnStage(String reference, ActorRef sender);

    ActorRef tellAssistantOnStage(String reference, int discriminator, ActorRef sender);

    ActorRef tellAssistantOnStage(String reference, Object message, int discriminator, ActorRef sender);

    <T> ActorRef passScript(Class<T> clazz, Script1<T> script);

    <T> ActorRef passScript(Class<T> clazz, Script2<T> script);

    <T> ActorRef passScript(Class<T> clazz, Script3<T> script);

    <T> ActorRef passAssistantScript(String eventRef, AssistantScript1<T> script);

    <T> ActorRef passAssistantScript(String eventRef, AssistantScript2<T> script);

    <T> ActorRef passAssistantScript(String eventRef, AssistantScript3<T> script);

    String getPath();
}
