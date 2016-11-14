package edu.msoe.leinoa.androideventer.model.actions;

import java.util.Calendar;

/**
 * Created by aleino on 11/13/16.
 */

public class NotificationAction extends Action {
    public NotificationAction(String uuid, String title, String description, String actionType, byte[] actionData, Calendar dateCreated) {
        super(uuid, title, description, actionType, actionData, dateCreated);
    }

    @Override
    public void execute() {
        //The action data will contain the message to display in the notification, so the byte array
        //must be converted to a string. Strings may contain extra formatting
    }
}
