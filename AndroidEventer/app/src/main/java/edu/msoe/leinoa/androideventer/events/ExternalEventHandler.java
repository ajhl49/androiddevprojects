package edu.msoe.leinoa.androideventer.events;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import edu.msoe.leinoa.androideventer.model.BoundEvent;
import edu.msoe.leinoa.androideventer.model.ExternalEventInterface;

/**
 * Created by aleino on 11/12/16.
 */

public class ExternalEventHandler {

    private static ExternalEventHandler externalEventHandler = null;

    public static synchronized ExternalEventHandler getHandler() {
        if (externalEventHandler == null) {
            externalEventHandler = new ExternalEventHandler();
        }
        return externalEventHandler;
    }

    private EventerRegistrar registrar;

    private ExternalEventHandler() {
        registrar = EventerRegistrar.getEventerRegistrar();
    }

    public void handleExternalEvents(List<? extends ExternalEventInterface> externalEvents) {
        if (externalEvents.size() > 0) {
            Log.w("ExternalEventHandler", "Events gathered from server!");
        }

        for (ExternalEventInterface externalEvent : externalEvents) {
            Log.w("ExternalEventHandler", externalEvent.getEventName() + " / " + externalEvent.getEventUUID());
            List<BoundEvent> boundEvents = registrar.getAllMatchingBound(externalEvent);
            for (BoundEvent boundEvent : boundEvents) {
                boundEvent.execute();
            }
        }
    }
}
