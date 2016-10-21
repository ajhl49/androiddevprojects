package edu.msoe.leinoa.midtermtodo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.msoe.leinoa.midtermtodo.model.ToDoItem;
import edu.msoe.leinoa.midtermtodo.model.ToDoPriority;
import edu.msoe.leinoa.midtermtodo.model.ToDoStatus;

/**
 * Created by aleino on 10/14/16.
 */

public class ToDoDBAdapter {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tododb";

    public static final String TODO_TABLE = "todoitems";

    static final String TODO_TABLE_COLUMN_ID = "id";

    static final String DATABASE_CREATE_TDI_TABLE =
            "create table if not exists todoitems ( " +
                    "id integer primary key autoincrement, " +
                    "title text, " +
                    "description text, " +
                    "status text, " +
                    "priority text, " +
                    "datedue integer, " +
                    "datecreated integer " +
                    ");";

    static final String DATABASE_DROP_TDI_TABLE =
            "drop table if exists " + TODO_TABLE;

    private static ToDoDBAdapter dbAdapter = null;

    private String[] allColumns = {
            "id",
            "title",
            "description",
            "status",
            "priority",
            "datedue",
            "datecreated"
    };

    private ToDoDBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public static synchronized ToDoDBAdapter getAdapter(Context context) {
        if (dbAdapter == null) {
            dbAdapter = new ToDoDBAdapter(context);
        }
        return dbAdapter;
    }

    private ToDoDBAdapter(Context context) {
        dbOpenHelper = new ToDoDBOpenHelper(context, DATABASE_NAME,
                DATABASE_VERSION);
        db = dbOpenHelper.getWritableDatabase();
    }

    public ToDoItem addToDoItem(ToDoItem tdi) {
        ContentValues values = new ContentValues();

        values.put("title", tdi.getTitle());
        values.put("description", tdi.getDescription());
        values.put("status", tdi.getStatus().name());
        values.put("priority", tdi.getPriority().name());
        values.put("datedue", tdi.getDateDue().getTimeInMillis());
        values.put("datecreated", tdi.getDateCreated().getTimeInMillis());

        long rowid = db.insert(TODO_TABLE, null, values);
        Cursor cursor = db.query(TODO_TABLE, allColumns,
                "id = " + rowid, null, null, null, null);
        cursor.moveToFirst();
        ToDoItem newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;
    }

    public int updateToDoItem(ToDoItem tdi) {
        ContentValues values = new ContentValues();

        values.put("title", tdi.getTitle());
        values.put("description", tdi.getDescription());
        values.put("status", tdi.getStatus().name());
        values.put("priority", tdi.getPriority().name());
        values.put("datedue", tdi.getDateDue().getTimeInMillis());
        values.put("datecreated", tdi.getDateCreated().getTimeInMillis());

        return db.update(TODO_TABLE, values, "id = ?",
                new String[] {String.valueOf(tdi.getId())});
    }

    public void deleteItem(ToDoItem tdi) {
        long id = tdi.getId();
        System.out.println("ToDoItem deleted with id: " + id);
        db.delete(TODO_TABLE, "id = " + id, null);
    }

    public ToDoItem getItemById(long id) {
        Cursor cursor = db.query(TODO_TABLE, allColumns, "id = ?", new String[] {String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        ToDoItem tdi = cursorToItem(cursor);
        return tdi;
    }

    public List<ToDoItem> getAllItems() {
        List<ToDoItem> toDoItems = new ArrayList<>();

        Cursor cursor = db.query(TODO_TABLE, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            ToDoItem tdi = cursorToItem(cursor);
            toDoItems.add(tdi);
            cursor.moveToNext();
        }
        cursor.close();
        return toDoItems;
    }

    private ToDoItem cursorToItem(Cursor cursor) {
        ToDoItem tdi = new ToDoItem();
        tdi.setId(cursor.getLong(0));
        tdi.setTitle(cursor.getString(1));
        tdi.setDescription(cursor.getString(2));
        tdi.setStatus(ToDoStatus.valueOf(cursor.getString(3)));
        tdi.setPriority(ToDoPriority.valueOf(cursor.getString(4)));

        Calendar dateDue = Calendar.getInstance();
        dateDue.clear();
        dateDue.setTimeInMillis(cursor.getLong(5));
        tdi.setDateDue(dateDue);

        Calendar dateCreated = Calendar.getInstance();
        dateCreated.clear();
        dateCreated.setTimeInMillis(cursor.getLong(6));
        tdi.setDateCreated(dateCreated);
        return tdi;
    }

    private synchronized SQLiteDatabase open() {
        return dbOpenHelper.getWritableDatabase();
    }
}
