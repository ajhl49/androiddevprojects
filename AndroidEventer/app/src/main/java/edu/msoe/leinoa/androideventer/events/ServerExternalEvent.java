package edu.msoe.leinoa.androideventer.events;

import android.util.JsonReader;

import java.io.InputStream;

import edu.msoe.leinoa.androideventer.model.ExternalEventInterface;

/**
 * Created by aleino on 11/12/16.
 */

public class ServerExternalEvent implements ExternalEventInterface {

    private String eventName;
    private String eventIdentifier;

    public ServerExternalEvent(String eventName, String eventIdentifier) {
        this.eventName = eventName;
        this.eventIdentifier = eventIdentifier;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public String getEventIdentifier() {
        return eventIdentifier;
    }
}
