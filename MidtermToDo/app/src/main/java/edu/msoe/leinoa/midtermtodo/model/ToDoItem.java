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

    public ToDoItem() {
        this(0L, null, null, ToDoStatus.READY,
                ToDoPriority.PRIORITY_LOW, null, null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ToDoStatus getStatus() {
        return status;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }

    public ToDoPriority getPriority() {
        return priority;
    }

    public void setPriority(ToDoPriority priority) {
        this.priority = priority;
    }

    public Calendar getDateDue() {
        return dateDue;
    }

    public void setDateDue(Calendar dateDue) {
        this.dateDue = dateDue;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
