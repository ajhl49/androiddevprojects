package edu.msoe.leinoa.androideventer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

import edu.msoe.leinoa.androideventer.events.EventerRegistrar;
import edu.msoe.leinoa.androideventer.model.BoundEvent;
import edu.msoe.leinoa.androideventer.model.actions.NotificationAction;
import edu.msoe.leinoa.androideventer.model.triggers.Trigger;
import edu.msoe.leinoa.androideventer.networking.ServerCommunicatorService;
import edu.msoe.leinoa.androideventer.sqlite.EventerDBAdapter;

public class AndroidEventer extends AppCompatActivity {

    static final int TRIGGER_LIST_VIEW_REQUEST = 5;
    static final int TRIGGER_DETAIL_VIEW_REQUEST = 6;
    static final int TRIGGER_ADD_REQUEST = 7;

    static final int ACTION_LIST_VIEW_REQUEST = 10;
    static final int ACTION_DETAIL_VIEW_REQUEST = 11;
    static final int ACTION_ADD_REQUEST = 12;

    static final int EVENT_LIST_VIEW_REQUEST = 15;
    static final int EVENT_DETAIL_VIEW_REQUEST = 16;
    static final int EVENT_ADD_REQUEST = 17;

    private EventerDBAdapter dbAdapter;
    private EventerRegistrar registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_eventer);

        getSupportActionBar().setTitle("Android Eventer");

        dbAdapter = EventerDBAdapter.getDbAdapter(this);

        registrar = EventerRegistrar.getEventerRegistrar();
        registrar.updateFromDatabase(this);

        //TEMP preload stuff
        Trigger serverTrigger = new Trigger("487aedb9-dc84-46d9-b9a6-dd30f5fb050f", "ServerTrigger",
                "Server based trigger", "Trigger", new byte[0], Calendar.getInstance());
        NotificationAction notificationAction = new NotificationAction("487aedb9-dc84-46d9-b9a6-dd30f5fb050f",
                "NotificationAction", "Notification based action", "NotificationAction", new byte[0],
                Calendar.getInstance());
        BoundEvent testBound = new BoundEvent("", "TestBound", "Test bound", new byte[0], serverTrigger,
                notificationAction, Calendar.getInstance());
        registrar.addAction(notificationAction);
        registrar.addTrigger(serverTrigger);
        //registrar.addBoundEvent(testBound);

        this.startService(new Intent(this, ServerCommunicatorService.class));
    }

    public void triggersButton(View view) {
        Intent intent = new Intent(this, TriggersActivity.class);
        startActivityForResult(intent, TRIGGER_LIST_VIEW_REQUEST);
    }

    public void actionsButton(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);
        startActivityForResult(intent, ACTION_LIST_VIEW_REQUEST);
    }

    public void eventsButton(View view) {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivityForResult(intent, EVENT_LIST_VIEW_REQUEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, ServerCommunicatorService.class);
        intent.putExtra("scs_cancel", true);
        this.startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case TRIGGER_LIST_VIEW_REQUEST: {
                break;
            }
            case ACTION_LIST_VIEW_REQUEST: {
                break;
            }
            case EVENT_LIST_VIEW_REQUEST: {
                break;
            }
            default: {
                break;
            }
        }
    }
}
