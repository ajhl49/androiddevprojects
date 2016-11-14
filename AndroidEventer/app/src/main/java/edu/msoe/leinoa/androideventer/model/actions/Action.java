package edu.msoe.leinoa.androideventer.model.actions;

import java.util.Calendar;

/**
 * Created by aleino on 11/13/16.
 */

public abstract class Action {
    protected String uuid;
    protected String title;
    protected String description;
    protected String actionType;
    protected byte[] actionData;
    protected Calendar dateCreated;

    public Action(String uuid, String title, String description, String actionType,
                  byte[] actionData, Calendar dateCreated) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.actionType = actionType;
        this.actionData = actionData;
        this.dateCreated = dateCreated;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getActionType() {
        return actionType;
    }

    public byte[] getActionData() {
        return actionData;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setActionData(byte[] actionData) {
        this.actionData = actionData;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public abstract void execute();
}
