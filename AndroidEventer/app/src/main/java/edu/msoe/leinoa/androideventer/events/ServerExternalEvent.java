package edu.msoe.leinoa.androideventer.events;

import android.util.JsonReader;

import java.io.InputStream;

import edu.msoe.leinoa.androideventer.model.ExternalEventInterface;

/**
 * Created by aleino on 11/12/16.
 */

public class ServerExternalEvent implements ExternalEventInterface {

    private String eventName;
    private String eventUUID;

    public ServerExternalEvent(String eventName, String eventUUID) {
        this.eventName = eventName;
        this.eventUUID = eventUUID;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public String getEventUUID() {
        return eventUUID;
    }
}
