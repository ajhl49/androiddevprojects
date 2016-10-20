package edu.msoe.leinoa.fragmenttodolist.models;

/**
 * Created by aleino on 10/7/16.
 */
public class ToDoItem {

    private int id;
    private String title;
    private String body;
    private long timestamp;

    public ToDoItem(int id, String title, String body, long timestamp) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
    }

    public ToDoItem(String title, String body, long timestamp) {
        this.id = 0;
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
