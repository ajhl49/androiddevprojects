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
import java.util.Calendar;
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
        Log.d("SCS", "Cancelling all alarms...");
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.cancel(PendingIntent.getService(this, 0, new Intent(this, ServerCommunicatorService.class), 0));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getBooleanExtra("scs_cancel", false)) {
            cancelAnyAlarms();
            makeNewAlarm = false;
        }

        synchronized (ServerCommunicator.class) {
            if (serverCommunicator == null) {
                serverCommunicator = new ServerCommunicator("http://192.168.1.4:3000", ExternalEventHandler.getHandler());
            }
        }
        serverCommunicator.makeUpdates(this);

        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        if (makeNewAlarm) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 15);
            Log.d("SCS", "Sysmilli: " + System.currentTimeMillis() + ", cal: " + calendar.getTimeInMillis());
            alarm.set(alarm.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    PendingIntent.getService(this, 0, new Intent(this, ServerCommunicatorService.class), 0));
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
