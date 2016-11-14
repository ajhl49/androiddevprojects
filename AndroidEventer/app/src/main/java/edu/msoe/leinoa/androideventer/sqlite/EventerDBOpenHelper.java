package edu.msoe.leinoa.androideventer.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aleino on 11/11/16.
 */

class EventerDBOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "eventerdb";

    public static final String BOUND_EVENT_TABLE_NAME = "BoundEventTable";

    public static final String ACTION_TABLE_NAME = "ActionTable";

    public static final String TRIGGER_TABLE_NAME = "TriggerTable";

    static final String DATABASE_CREATE_BOUND_EVENT_TABLE =
            "create table if not exists " + BOUND_EVENT_TABLE_NAME + " ( " +
            "uuid text primary key, " +
            "title text, " +
            "description text, " +
            "boundeventdata blob, " +
            "triggeruuid text, " +
            "actionuuid text, " +
            "datecreated integer" +
            ");";

    static final String[] BOUND_EVENT_TABLE_COLUMNS = {
            "uuid",
            "title",
            "description",
            "boundeventdata",
            "triggeruuid",
            "actionuuid",
            "datecreated"
    };

    static final String DATABASE_DROP_BOUND_EVENT_TABLE =
            "drop table if exists " + BOUND_EVENT_TABLE_NAME + ";";

    static final String DATABASE_CREATE_ACTION_TABLE =
            "create table if not exists " + ACTION_TABLE_NAME + " ( " +
            "uuid text primary key, " +
            "title text, " +
            "description text, " +
            "actiontype text, " +
            "actiondata blob, " +
            "datecreated integer" +
            ");";

    static final String[] ACTION_TABLE_COLUMNS = {
            "uuid",
            "title",
            "description",
            "actiontype",
            "actiondata",
            "datecreated"
    };

    static final String DATABASE_DROP_ACTION_TABLE =
            "drop table if exists " + ACTION_TABLE_NAME + ";";

    static final String DATABASE_CREATE_TRIGGER_TABLE =
            "create table if not exists " + TRIGGER_TABLE_NAME + " ( " +
            "uuid text primary key, " +
            "title text, " +
            "description text, " +
            "triggertype text, " +
            "triggerdata blob, " +
            "datecreated integer" +
            ");";

    static final String[] TRIGGER_TABLE_COLUMNS = {
            "uuid",
            "title",
            "description",
            "triggertype",
            "triggerdata",
            "datecreated"
    };

    static final String DATABASE_DROP_TRIGGER_TABLE =
            "drop table if exists " + TRIGGER_TABLE_NAME + ";";



    public EventerDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public EventerDBOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_BOUND_EVENT_TABLE);
        db.execSQL(DATABASE_CREATE_ACTION_TABLE);
        db.execSQL(DATABASE_CREATE_TRIGGER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(EventerDBOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL(DATABASE_DROP_BOUND_EVENT_TABLE);
        db.execSQL(DATABASE_DROP_ACTION_TABLE);
        db.execSQL(DATABASE_DROP_TRIGGER_TABLE);
        onCreate(db);
    }

}
