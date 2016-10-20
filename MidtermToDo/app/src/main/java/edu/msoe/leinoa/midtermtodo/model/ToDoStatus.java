package edu.msoe.leinoa.midtermtodo.model;

/**
 * Created by aleino on 10/14/16.
 */

public enum ToDoStatus {
    READY ("Ready"),
    STARTED ("Started"),
    IN_PROGRESS ("In Progress"),
    COMPLETE ("Completed");

    public static ToDoStatus getStatusFromString(String name) {
        return ToDoStatus.valueOf(name);
    }

    private String stringRep;

    ToDoStatus(String stringRep) {
        this.stringRep = stringRep;
    }

    @Override
    public String toString() {
        return stringRep;
    }
}
