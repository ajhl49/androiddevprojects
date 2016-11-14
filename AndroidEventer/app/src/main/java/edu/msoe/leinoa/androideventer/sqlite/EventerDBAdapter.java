package edu.msoe.leinoa.androideventer.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.msoe.leinoa.androideventer.events.EventerRegistrar;
import edu.msoe.leinoa.androideventer.model.actions.Action;
import edu.msoe.leinoa.androideventer.model.BoundEvent;
import edu.msoe.leinoa.androideventer.model.triggers.Trigger;
import edu.msoe.leinoa.androideventer.model.actions.NotificationAction;

/**
 * Created by aleino on 11/13/16.
 */

public class EventerDBAdapter {

    private static EventerDBAdapter dbAdapter = null;

    private EventerDBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    private Context context;

    public static synchronized EventerDBAdapter getDbAdapter(Context context) {
        if (dbAdapter == null) {
            dbAdapter = new EventerDBAdapter(context);
        }
        return dbAdapter;
    }

    private EventerDBAdapter(Context context) {
        this.context = context;

        dbOpenHelper = new EventerDBOpenHelper(context, EventerDBOpenHelper.DATABASE_NAME,
                EventerDBOpenHelper.DATABASE_VERSION);
        db = dbOpenHelper.getWritableDatabase();
    }

    public Action addAction(Action action) {
        ContentValues values = new ContentValues();
        values.put("uuid", action.getUuid());
        values.put("title", action.getTitle());
        values.put("description", action.getDescription());
        values.put("actiontype", action.getActionType());
        values.put("actiondata", action.getActionData());
        values.put("datecreated", action.getDateCreated().getTime().getTime());

        long rowid = db.insert(EventerDBOpenHelper.ACTION_TABLE_NAME, null, values);
        Cursor cursor = db.query(EventerDBOpenHelper.ACTION_TABLE_NAME, EventerDBOpenHelper.ACTION_TABLE_COLUMNS,
                "rowid = " + rowid, null, null, null, null);
        cursor.moveToFirst();
        Action newAction = cursorToAction(cursor);
        cursor.close();

        return newAction;
    }

    public Trigger addTrigger(Trigger trigger) {
        ContentValues values = new ContentValues();
        values.put("uuid", trigger.getUuid());
        values.put("title", trigger.getTitle());
        values.put("description", trigger.getDescription());
        values.put("triggertype", trigger.getTriggerType());
        values.put("triggerdata", trigger.getTriggerData());
        values.put("datecreated", trigger.getDateCreated().getTime().getTime());

        long rowid = db.insert(EventerDBOpenHelper.TRIGGER_TABLE_NAME, null, values);
        Cursor cursor = db.query(EventerDBOpenHelper.TRIGGER_TABLE_NAME, EventerDBOpenHelper.TRIGGER_TABLE_COLUMNS,
                "rowid = " + rowid, null, null, null, null);
        cursor.moveToFirst();
        Trigger newTrigger = cursorToTrigger(cursor);
        cursor.close();

        return newTrigger;
    }

    public BoundEvent addBoundEvent(BoundEvent boundEvent) {
        ContentValues values = new ContentValues();
        values.put("uuid", boundEvent.getUuid());
        values.put("title", boundEvent.getTitle());
        values.put("description", boundEvent.getDescription());
        values.put("boundeventdata", boundEvent.getBoundEventData());
        values.put("triggeruuid", boundEvent.getTrigger().getUuid());
        values.put("actionuuid", boundEvent.getAction().getUuid());
        values.put("datecreated", boundEvent.getDateCreated().getTime().getTime());

        long rowid = db.insert(EventerDBOpenHelper.BOUND_EVENT_TABLE_NAME, null, values);
        Cursor cursor = db.query(EventerDBOpenHelper.BOUND_EVENT_TABLE_NAME, EventerDBOpenHelper.BOUND_EVENT_TABLE_COLUMNS,
                "rowid = " + rowid, null, null, null, null);
        cursor.moveToFirst();
        BoundEvent newBoundEvent = cursorToBoundEvent(cursor);
        cursor.close();

        return newBoundEvent;
    }

    public int updateAction(Action action) {
        ContentValues values = new ContentValues();
        values.put("uuid", action.getUuid());
        values.put("title", action.getTitle());
        values.put("description", action.getDescription());
        values.put("actiontype", action.getActionType());
        values.put("actiondata", action.getActionData());
        values.put("datecreated", action.getDateCreated().getTime().getTime());

        return db.update(EventerDBOpenHelper.ACTION_TABLE_NAME, values, "uuid = ?",
                new String[] {"\"" + action.getUuid() + "\""});
    }

    public int updateTrigger(Trigger trigger) {
        ContentValues values = new ContentValues();
        values.put("uuid", trigger.getUuid());
        values.put("title", trigger.getTitle());
        values.put("description", trigger.getDescription());
        values.put("triggertype", trigger.getTriggerType());
        values.put("triggerdata", trigger.getTriggerData());
        values.put("datecreated", trigger.getDateCreated().getTime().getTime());

        return db.update(EventerDBOpenHelper.TRIGGER_TABLE_NAME, values, "uuid = ?",
                new String[] {"\"" + trigger.getUuid() + "\""});
    }

    public int updateBoundEvent(BoundEvent boundEvent) {
        ContentValues values = new ContentValues();
        values.put("uuid", boundEvent.getUuid());
        values.put("title", boundEvent.getTitle());
        values.put("description", boundEvent.getDescription());
        values.put("boundeventdata", boundEvent.getBoundEventData());
        values.put("triggeruuid", boundEvent.getTrigger().getUuid());
        values.put("actionuuid", boundEvent.getAction().getUuid());
        values.put("datecreated", boundEvent.getDateCreated().getTime().getTime());

        return db.update(EventerDBOpenHelper.BOUND_EVENT_TABLE_NAME, values, "uuid = ?",
                new String[] {"\"" + boundEvent.getUuid() + "\""});
    }

    public void deleteAction(Action action) {
        String uuid = action.getUuid();
        Log.d("EventerDBAdapter", "Action deleted with uuid: " + uuid);
        db.delete(EventerDBOpenHelper.ACTION_TABLE_NAME, "uuid = \"" + uuid + "\"", null);
    }

    public void deleteTrigger(Trigger trigger) {
        String uuid = trigger.getUuid();
        Log.d("EventerDBAdapter", "Trigger deleted with uuid: " + uuid);
        db.delete(EventerDBOpenHelper.TRIGGER_TABLE_NAME, "uuid = \"" + uuid + "\"", null);
    }

    public void deleteBoundEvent(BoundEvent boundEvent) {
        String uuid = boundEvent.getUuid();
        Log.d("EventerDBAdapter", "Bound event deleted with uuid: " + uuid);
        db.delete(EventerDBOpenHelper.BOUND_EVENT_TABLE_NAME, "uuid = \"" + uuid + "\"", null);
    }

    public List<BoundEvent> getAllBoundEvents() {
        List<BoundEvent> boundEvents = new ArrayList<>();

        Cursor cursor = db.query(EventerDBOpenHelper.BOUND_EVENT_TABLE_NAME, EventerDBOpenHelper.BOUND_EVENT_TABLE_COLUMNS,
                null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            BoundEvent boundEvent = cursorToBoundEvent(cursor);
            boundEvents.add(boundEvent);
            cursor.moveToNext();
        }
        cursor.close();
        return boundEvents;
    }

    public List<Action> getAllActions() {
        List<Action> actions = new ArrayList<>();

        Cursor cursor = db.query(EventerDBOpenHelper.ACTION_TABLE_NAME, EventerDBOpenHelper.ACTION_TABLE_COLUMNS,
                null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Action action = cursorToAction(cursor);
            actions.add(action);
            cursor.moveToNext();
        }
        cursor.close();
        return actions;
    }

    public BoundEvent getBoundEventByUuid(String uuid) {
        Cursor cursor = db.query(EventerDBOpenHelper.BOUND_EVENT_TABLE_NAME, EventerDBOpenHelper.BOUND_EVENT_TABLE_COLUMNS,
                "uuid = ?", new String[] {uuid}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToBoundEvent(cursor);
    }

    public List<Trigger> getAllTriggers() {
        List<Trigger> triggers = new ArrayList<>();

        Cursor cursor = db.query(EventerDBOpenHelper.TRIGGER_TABLE_NAME, EventerDBOpenHelper.TRIGGER_TABLE_COLUMNS,
                null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Trigger trigger = cursorToTrigger(cursor);
            triggers.add(trigger);
            cursor.moveToNext();
        }
        cursor.close();
        return triggers;
    }

    private Action cursorToAction(Cursor cursor) {
        String uuid = cursor.getString(0);
        String title = cursor.getString(1);
        String description = cursor.getString(2);
        String actionType = cursor.getString(3);
        byte[] actionData = cursor.getBlob(4);
        Calendar dateCreated = Calendar.getInstance();
        dateCreated.clear();
        dateCreated.setTimeInMillis(cursor.getLong(5));

        Action action = null;
        if (actionType.equals("NotificationAction")) {
            action = new NotificationAction(uuid, title, description, actionType, actionData, dateCreated);
        }
        return action;
    }

    private Trigger cursorToTrigger(Cursor cursor) {
        String uuid = cursor.getString(0);
        String title = cursor.getString(1);
        String description = cursor.getString(2);
        String triggerType = cursor.getString(3);
        byte[] triggerData = cursor.getBlob(4);
        Calendar dateCreated = Calendar.getInstance();
        dateCreated.clear();
        dateCreated.setTimeInMillis(cursor.getLong(5));

        return new Trigger(uuid, title, description, triggerType, triggerData, dateCreated);
    }

    private BoundEvent cursorToBoundEvent(Cursor cursor) {
        String uuid = cursor.getString(0);
        String title = cursor.getString(1);
        String description = cursor.getString(2);
        byte[] boundEventData = cursor.getBlob(3);
        String triggerUuid = cursor.getString(4);
        String actionUuid = cursor.getString(5);
        Calendar dateCreated = Calendar.getInstance();
        dateCreated.clear();
        dateCreated.setTimeInMillis(cursor.getLong(6));

        //Get trigger
        Trigger trigger = null;
        trigger = EventerRegistrar.getEventerRegistrar().getTrigger(triggerUuid);
        //Get action
        Action action = null;
        action = EventerRegistrar.getEventerRegistrar().getAction(actionUuid);

        return new BoundEvent(uuid, title, description, boundEventData, trigger, action, dateCreated);
    }
}
