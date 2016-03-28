package br.com.concretesolutions.audience.system;

import java.lang.ref.WeakReference;

import br.com.concretesolutions.audience.script.api.AssistantScript1;
import br.com.concretesolutions.audience.script.api.AssistantScript2;
import br.com.concretesolutions.audience.script.api.AssistantScript3;
import br.com.concretesolutions.audience.script.api.Script1;
import br.com.concretesolutions.audience.script.api.Script2;
import br.com.concretesolutions.audience.script.api.Script3;

final class ActorRefImpl implements ActorRef {

    private final ActorSystem mSystem;
    private final WeakReference<Actor> mActor;
    private final String mPath;

    public ActorRefImpl(ActorSystem system, Actor actor, String path) {
        mSystem = system;
        mActor = new WeakReference<>(actor);
        mPath = path;
    }

    @Override
    public ActorRef tellOnStage(Object message) {
        return tellOnStage(message, this);
    }

    @Override
    public ActorRef tellOnStage(Object message, int discriminator) {
        return tellOnStage(message, discriminator, this);
    }

    @Override
    public ActorRef tellOnStage(Object message, ActorRef sender) {
        return tellOnStage(message, ActorRef.NO_DISCRIMINATOR, sender);
    }

    @Override
    public ActorRef tellOnStage(Object message, int discriminator, ActorRef sender) {
        mSystem.sendUI(this, message, discriminator, sender);
        return this;
    }

    @Override
    public ActorRef tell(Object message) {
        return tell(message, this);
    }

    @Override
    public ActorRef tell(Object message, int discriminator) {
        return tell(message, discriminator, this);
    }

    @Override
    public ActorRef tell(Object message, ActorRef sender) {
        return tell(message, ActorRef.NO_DISCRIMINATOR, sender);
    }

    @Override
    public ActorRef tell(Object message, int discriminator, ActorRef sender) {
        mSystem.send(this, message, discriminator, sender);
        return this;
    }

    @Override
    public ActorRef tellAssistant(String reference) {
        return tellAssistant(reference, null, ActorRef.NO_DISCRIMINATOR, this);
    }

    @Override
    public ActorRef tellAssistant(String reference, ActorRef sender) {
        return tellAssistant(reference, null, ActorRef.NO_DISCRIMINATOR, sender);
    }

    @Override
    public ActorRef tellAssistant(String reference, int discriminator, ActorRef sender) {
        return tellAssistant(reference, null, discriminator, sender);
    }

    @Override
    public ActorRef tellAssistant(String reference, Object message, int discriminator, ActorRef sender) {
        mSystem.send(this, new ReferenceMessage(reference, message), discriminator, sender);
        return this;
    }

    @Override
    public ActorRef tellAssistantOnStage(String reference) {
        return tellAssistant(reference, null, ActorRef.NO_DISCRIMINATOR, this);
    }

    @Override
    public ActorRef tellAssistantOnStage(String reference, ActorRef sender) {
        return tellAssistant(reference, null, ActorRef.NO_DISCRIMINATOR, sender);
    }

    @Override
    public ActorRef tellAssistantOnStage(String reference, int discriminator, ActorRef sender) {
        return tellAssistant(reference, null, discriminator, sender);
    }

    @Override
    public ActorRef tellAssistantOnStage(String reference, Object message, int discriminator, ActorRef sender) {
        mSystem.sendUI(this, new ReferenceMessage(reference, message), discriminator, sender);
        return this;
    }

    @Override
    public <T> ActorRef passScript(Class<T> clazz, Script1<T> script) {
        mActor.get().registerScript(clazz, script);
        return this;
    }

    @Override
    public <T> ActorRef passScript(Class<T> clazz, Script2<T> script) {
        mActor.get().registerScript(clazz, script);
        return this;
    }

    @Override
    public <T> ActorRef passScript(Class<T> clazz, Script3<T> script) {
        mActor.get().registerScript(clazz, script);
        return this;
    }

    @Override
    public <T> ActorRef passAssistantScript(String eventRef, AssistantScript1<T> script) {
        mActor.get().registerScript(eventRef, script);
        return this;
    }

    @Override
    public <T> ActorRef passAssistantScript(String eventRef, AssistantScript2<T> script) {
        mActor.get().registerScript(eventRef, script);
        return this;
    }

    @Override
    public <T> ActorRef passAssistantScript(String eventRef, AssistantScript3<T> script) {
        mActor.get().registerScript(eventRef, script);
        return this;
    }

    Actor getActor() {
        return mActor.get();
    }

    @Override
    public String getPath() {
        return mPath;
    }

    @Override
    public String toString() {
        return "ActorRefImpl{" +
                "mActor=" + mActor.get() +
                ", mPath='" + mPath + '\'' +
                '}';
    }
}
