package edu.msoe.leinoa.midtermtodo.sqlite;

/**
 * Created by aleino on 10/14/16.
 */

public class ToDoDBAdapter {

    private static ToDoDBAdapter dbAdapter = null;

    public static synchronized ToDoDBAdapter getAdapter() {
        if (dbAdapter == null) {
            dbAdapter = new ToDoDBAdapter();
        }
        return dbAdapter;
    }

    private ToDoDBAdapter() {

    }
}
