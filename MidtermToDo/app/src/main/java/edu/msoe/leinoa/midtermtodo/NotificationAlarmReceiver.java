package edu.msoe.leinoa.midtermtodo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import edu.msoe.leinoa.midtermtodo.model.ToDoItem;
import edu.msoe.leinoa.midtermtodo.sqlite.ToDoDBAdapter;

/**
 * Created by aleino on 10/21/16.
 */

public class NotificationAlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent viewIntent = new Intent(context, ToDoViewActivity.class);
        long rowId = intent.getLongExtra("todo_id", -1);
        viewIntent.putExtra("todo_id", rowId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123456123, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (rowId != -1 && ToDoDBAdapter.getAdapter(context).hasItemById(rowId)) {

            ToDoItem tdi = ToDoDBAdapter.getAdapter(context).getItemById(rowId);
            Notification n = new Notification.Builder(context)
                    .setContentTitle("ToDo item due!")
                    .setContentText(tdi.getTitle())
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        }

    }
}
