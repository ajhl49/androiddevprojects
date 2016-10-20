package edu.msoe.leinoa.midtermtodo.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aleino on 10/14/16.
 */

public class ToDoItem  {

    private long id;

    private String title = null;
    private String description = null;

    private ToDoStatus status = ToDoStatus.READY;
    private ToDoPriority priority = ToDoPriority.PRIORITY_LOW;

    private Calendar dateCreated;
    private Calendar dateDue;

    public ToDoItem(long id, String title, String description, ToDoStatus status, ToDoPriority priority,
                    Calendar dateDue, Calendar dateCreated) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dateDue = dateDue;
        this.dateCreated = dateCreated;
    }

    public ToDoItem(long id, String title, String description, ToDoStatus status, ToDoPriority priority,
                    Calendar dateDue) {
        this(id, title, description, status, priority, dateDue, Calendar.getInstance());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ToDoStatus getStatus() {
        return status;
    }

    public ToDoPriority getPriority() {
        return priority;
    }

    public Calendar getDateDue() {
        return dateDue;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }
}
