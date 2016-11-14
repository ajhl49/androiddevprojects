package edu.msoe.leinoa.androideventer.model.triggers;

import java.util.Calendar;

import edu.msoe.leinoa.androideventer.model.ExternalEventInterface;

/**
 * Created by aleino on 11/13/16.
 */

public class Trigger {
    private String uuid;
    private String title;
    private String description;
    private String triggerType;
    private byte[] triggerData;
    private Calendar dateCreated;

    public Trigger(String uuid, String title, String description, String triggerType,
                   byte[] triggerData, Calendar dateCreated) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.triggerType = triggerType;
        this.triggerData = triggerData;
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

    public String getTriggerType() {
        return triggerType;
    }

    public byte[] getTriggerData() {
        return triggerData;
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

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public void setTriggerData(byte[] triggerData) {
        this.triggerData = triggerData;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isTriggered(ExternalEventInterface externalEvent) {
        return false;
    }

    @Override
    public String toString() {
        return this.triggerType + ": " + this.title;
    }
}
