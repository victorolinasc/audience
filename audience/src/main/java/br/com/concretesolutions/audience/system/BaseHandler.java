package br.com.concretesolutions.audience.system;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import br.com.concretesolutions.audience.script.api.AssistantScript1;
import br.com.concretesolutions.audience.script.api.AssistantScript2;
import br.com.concretesolutions.audience.script.api.AssistantScript3;
import br.com.concretesolutions.audience.script.api.Script;
import br.com.concretesolutions.audience.script.api.Script1;
import br.com.concretesolutions.audience.script.api.Script2;
import br.com.concretesolutions.audience.script.api.Script3;

abstract class BaseHandler extends Handler {

    BaseHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        final Part part = (Part) msg.obj;
        final Actor actor = part.getTarget();
        final ActorRef sender = part.getSender();
        final Object message = part.getMessage();

        final Script<?> scriptToPlay;

        if (message instanceof ReferenceMessage)
            scriptToPlay = actor.getReferenceScripts().get(((ReferenceMessage) message).getReference());

        else {
            final Class<?> aClass = message.getClass();
            scriptToPlay = actor.getScripts().get(aClass);
        }

        try {
            if (scriptToPlay == null) {
                actor.onReceive(message, msg.arg1, sender);
                return;
            }

            if (scriptToPlay instanceof Script1)
                ((Script1) scriptToPlay).onPart(message);

            else if (scriptToPlay instanceof Script2)
                ((Script2) scriptToPlay).onPart(message, msg.arg1);

            else if (scriptToPlay instanceof Script3)
                ((Script3) scriptToPlay).onPart(message, msg.arg1, sender);

            else if (scriptToPlay instanceof AssistantScript1)
                ((AssistantScript1) scriptToPlay).onPart(sender);

            else if (scriptToPlay instanceof AssistantScript2)
                ((AssistantScript2) scriptToPlay).onPart(msg.arg1, sender);

            else if (scriptToPlay instanceof AssistantScript3)
                ((AssistantScript3) scriptToPlay).onPart(((ReferenceMessage) message).getMessage(), msg.arg1, sender);

        } catch (Exception e) {
            part.getSender().tell(e);
        }
    }
}
