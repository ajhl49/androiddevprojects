package edu.msoe.leinoa.androideventer.model.actions;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import edu.msoe.leinoa.androideventer.AndroidEventer;
import edu.msoe.leinoa.androideventer.R;

/**
 * Created by aleino on 11/13/16.
 */

public class NotificationAction extends Action {
    public NotificationAction(String uuid, String title, String description, String actionType, byte[] actionData, Calendar dateCreated) {
        super(uuid, title, description, actionType, actionData, dateCreated);
    }

    @Override
    public void execute(Context context) {
        //The action data will contain the message to display in the notification, so the byte array
        //must be converted to a string. Strings may contain extra formatting
        Log.d("NotificationAction", "Test notification action");
        Intent intent = new Intent(context, AndroidEventer.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification n = new Notification.Builder(context)
                .setContentTitle(this.title)
                .setContentText(this.description)
                .setSmallIcon(R.drawable.ic_notification_alert)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}
