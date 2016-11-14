package edu.msoe.leinoa.androideventer.networking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import edu.msoe.leinoa.androideventer.events.ExternalEventHandler;
import edu.msoe.leinoa.androideventer.events.ServerExternalEvent;

/**
 * Created by aleino on 11/14/16.
 */

public class ServerCommunicatorService extends Service {

    private static ServerCommunicator serverCommunicator = null;

    private boolean makeNewAlarm = true;

    public void cancelAnyAlarms() {
        Toast.makeText(this, "Cancel all alarms", Toast.LENGTH_SHORT).show();
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.cancel(PendingIntent.getService(this, 50, new Intent(this, ServerCommunicatorService.class), 0));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getBooleanExtra("scs_cancel", false)) {
            cancelAnyAlarms();
            makeNewAlarm = false;
        }

        synchronized (ServerCommunicator.class) {
            if (serverCommunicator == null) {
                serverCommunicator = new ServerCommunicator("localhost:8080/");
            }
        }
        ExternalEventHandler externalEventHandler = ExternalEventHandler.getHandler();
        List<ServerExternalEvent> externalEvents = null;
        try {
            externalEvents = serverCommunicator.getAllEvents();
        } catch (IOException e) {
            Log.e("ServerCommunicatorSrv", "Error on server read", e);
        }
        externalEventHandler.handleExternalEvents(externalEvents);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // Restart service later
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();
        if (makeNewAlarm) {
            alarm.set(alarm.RTC_WAKEUP,
                    System.currentTimeMillis() + 20000,
                    PendingIntent.getService(this, 50, new Intent(this, ServerCommunicatorService.class), 0));
        }
    }
}
