package edu.msoe.leinoa.fragmenttodolist.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.msoe.leinoa.fragmenttodolist.models.ToDoItem;

/**
 * Created by aleino on 10/7/16.
 */
public class ToDoDBAdapter {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "todofragment";

    public static final String TODO_TABLE = "todo";

    static final String DATABASE_CREATE_TDF_TODO_TABLE =
            "create table if not exists todo ( " +
                    "id integer primary key autoincrement, " +                             // unique global record id
                    "todotitle text, " +                         // title text of todo
                    "todobody text, " +                          // body text of todo
                    "timestamp integer " +                       // creation or last update time
                    ");";

    private static ToDoDBOpenHelper dbOpenHelper = null;

    public static void init(Context context) {
        if (dbOpenHelper == null) {
            dbOpenHelper = new ToDoDBOpenHelper(context);
        }
    }

    private ToDoDBAdapter() {
    }

    public static int addToDoItem(ToDoItem tdi) {
        final SQLiteDatabase db = open();

        ContentValues values = new ContentValues();

        values.put("todotitle", tdi.getTitle());
        values.put("todobody", tdi.getBody());
        values.put("timestamp", tdi.getTimestamp());

        int rowid = (int)db.insert(TODO_TABLE, null, values);
        db.close();
        return rowid;
    }

    public static int updateToDoItem(ToDoItem tdi) {
        final SQLiteDatabase db = open();

        ContentValues values = new ContentValues();
        values.put("todotitle", tdi.getTitle());
        values.put("todobody", tdi.getBody());
        values.put("timestamp", tdi.getTimestamp());

        return db.update(TODO_TABLE, values, "id = ?",
                new String[] {String.valueOf(tdi.getId())});
    }

    public static ToDoItem getToDoItem(int id) {
        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TODO_TABLE, new String[] {
                "id",
                "todotitle",
                "todobody",
                "timestamp"
        }, "id =?", new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        ToDoItem tdi = new ToDoItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getLong(3));

        return tdi;
    }

    public static List<ToDoItem> getAllToDoItems() {
        List<ToDoItem> todoList = new ArrayList();

        String selectQuery = "SELECT * FROM " + TODO_TABLE;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id;
                String todotitle;
                String todobody;
                ToDoItem tdi = new ToDoItem(cursor.getInt(0),
                                            cursor.getString(1),
                                            cursor.getString(2),
                                            cursor.getLong(3));

                todoList.add(tdi);
            } while (cursor.moveToNext());
        }

        return todoList;
    }

    private static synchronized SQLiteDatabase open() {
        return dbOpenHelper.getWritableDatabase();
    }


    private static class ToDoDBOpenHelper extends SQLiteOpenHelper {

        public ToDoDBOpenHelper(Context context) {
            super(context, ToDoDBAdapter.DATABASE_NAME, null, ToDoDBAdapter.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ToDoDBAdapter.DATABASE_CREATE_TDF_TODO_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
            onCreate(db);
        }
    }
}
