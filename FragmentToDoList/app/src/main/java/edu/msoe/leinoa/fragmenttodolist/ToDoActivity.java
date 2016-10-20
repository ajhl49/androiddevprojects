package edu.msoe.leinoa.fragmenttodolist;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import layout.DatePickerFragment;

public class ToDoActivity extends android.support.v4.app.FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void sendNotification(String title, String body) {
        try {
            NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            Intent intent = new Intent(this, ToDoActivity.class);

            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            Notification n = new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(pi)
                    .setSmallIcon(R.drawable.ic_stat_action_group_work)
                    .setAutoCancel(true)
                    .build();

            nm.notify(0, n);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
