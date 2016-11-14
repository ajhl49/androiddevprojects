package edu.msoe.leinoa.androideventer.networking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import edu.msoe.leinoa.androideventer.events.ExternalEventHandler;
import edu.msoe.leinoa.androideventer.events.ServerExternalEvent;

/**
 * Created by aleino on 11/13/16.
 */

public class ServerCommunicator {

    public static final String URL_ALL_EVENTS = "events";

    private String serverUrl = null;
    private ExternalEventHandler handler;

    public ServerCommunicator(String baseUrl, ExternalEventHandler handler) {
        serverUrl = baseUrl;
        this.handler = handler;
    }

    public void makeUpdates() {
        new AccessWebServiceTask().execute();
    }

    private List<ServerExternalEvent> getAllEvents() throws IOException {
        InputStream in = openHttpConnection(serverUrl + "/" + URL_ALL_EVENTS);
        JsonReader jsonReader = new JsonReader(new InputStreamReader(in));
        List<ServerExternalEvent> events = null;

        try {
            String status = null;
            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("status")) {
                    status = jsonReader.nextString();
                    if (!status.equals("OK")) {
                        Log.w("ServerCommunicator", "Status not OK, status: " + status);
                    }
                } else if (name.equals("events")) {
                    events = readEvents(jsonReader);
                } else {
                    jsonReader.skipValue();
                    Log.w("ServerCommunicator", "Unknown identifier in main parser");
                }
            }
            if (status == null || events == null) {
                Log.e("ServerCommunicator", "Status or Events not read from response!");
            }

            jsonReader.endObject();
        } catch (IOException e) {
            Log.e("ServerCommunicator", "IOException on main read", e);
        }

        return events;
    }

    private class AccessWebServiceTask extends AsyncTask<String, ServerExternalEvent, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            List<ServerExternalEvent> externalEvents = null;
            try {
                externalEvents = getAllEvents();
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.handleExternalEvents(externalEvents);
            return (externalEvents != null) ? externalEvents.size() : 0;
        }
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
        String eventUUID = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                eventName = reader.nextString();
            } else if (name.equals("uuid")) {
                eventUUID = reader.nextString();
            } else {
                reader.skipValue();
                Log.w("ServerCommunicator", "Reader encountered unknown value");
            }
        }
        reader.endObject();
        if (eventName == null || eventUUID == null) {
            throw new IOException("Event name or identifier not defined!");
        }
        return new ServerExternalEvent(eventName, eventUUID);
    }

    private InputStream openHttpConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream in = null;
        int response = -1;

        if (!(conn instanceof HttpURLConnection)) {
            throw new IOException("Not an HTTP connection");
        }

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            Log.d("ServerCommunicator", "General failure on connection open", ex);
            throw new IOException("Error connecting", ex);
        }

        return in;
    }
}
