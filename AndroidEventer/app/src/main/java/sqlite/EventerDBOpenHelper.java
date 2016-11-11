package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aleino on 11/11/16.
 */

class EventerDBOpenHelper extends SQLiteOpenHelper {
    static final String DATABASE_CREATE_ACTION_TABLE = "";

    static final String DATABASE_CREATE_TRIGGER_TABLE = "";

    static final String DATABASE_CREATE_EVENT_TABLE = "";

    public EventerDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
