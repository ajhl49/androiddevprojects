package edu.msoe.leinoa.androideventer.model;

import java.util.Calendar;

import edu.msoe.leinoa.androideventer.model.actions.Action;
import edu.msoe.leinoa.androideventer.model.triggers.Trigger;

/**
 * Created by aleino on 11/13/16.
 */

public class BoundEvent {
    private String uuid;
    private String title;
    private String description;
    private byte[] boundEventData;
    private Trigger trigger;
    private Action action;
    private Calendar dateCreated;

    public BoundEvent(String uuid, String title, String description, byte[] boundEventData,
                      Trigger trigger, Action action, Calendar dateCreated) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.boundEventData = boundEventData;
        this.trigger = trigger;
        this.action = action;
        this.dateCreated = dateCreated;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getBoundEventData() {
        return boundEventData;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public Action getAction() {
        return action;
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

    public void setBoundEventData(byte[] boundEventData) {
        this.boundEventData = boundEventData;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void execute() {
        this.action.execute();
    }

    @Override
    public String toString() {
        return this.title;
    }
}
