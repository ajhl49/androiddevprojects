package edu.msoe.leinoa.midtermtodo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aleino on 10/14/16.
 */

class ToDoDBOpenHelper extends SQLiteOpenHelper {

    public ToDoDBOpenHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ToDoDBAdapter.DATABASE_CREATE_TDI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ToDoDBOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL(ToDoDBAdapter.DATABASE_DROP_TDI_TABLE);
        onCreate(db);
    }
}
