package edu.msoe.leinoa.androideventer.networking;

import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.msoe.leinoa.androideventer.events.ServerExternalEvent;

/**
 * Created by aleino on 11/13/16.
 */

public class ServerCommunicator {

    private ServerExternalEvent streamToEvent(InputStream in) {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(in));

        try {
            String status = null;
            List<ServerExternalEvent> events;
            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("status")) {
                    status = jsonReader.nextString();
                } else if (name.equals("events")) {
                    events = readEvents(jsonReader);
                } else {
                    jsonReader.skipValue();
                    Log.w("ServerCommunicator", "Unknown identifier in main parser");
                }
            }

            jsonReader.beginArray();
            while (jsonReader.hasNext()) {

            }
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ServerExternalEvent> readEvents(JsonReader reader) throws IOException {
        reader.beginArray();
        List<ServerExternalEvent> events = new ArrayList<>();
        while(reader.hasNext()) {
            events.add(readEvent(reader));
        }
        reader.endArray();
        return events;
    }

    private ServerExternalEvent readEvent(JsonReader reader) throws IOException {
        String eventName = null;
        String eventIdentifier = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                eventName = reader.nextString();
            } else if (name.equals("identifier")) {
                eventIdentifier = reader.nextString();
            } else {
                reader.skipValue();
                Log.w("ServerCommunicator", "Reader encountered unknown value");
            }
        }
        reader.endObject();
        if (eventName == null || eventIdentifier == null) {
            throw new IOException("Event name or identifier not defined!");
        }
        return new ServerExternalEvent(eventName, eventIdentifier);
    }
}
