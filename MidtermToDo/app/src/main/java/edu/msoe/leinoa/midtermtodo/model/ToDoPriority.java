package edu.msoe.leinoa.midtermtodo.model;

/**
 * Created by aleino on 10/14/16.
 */

public enum ToDoPriority {
    PRIORITY_HIGH ("High Priority"),
    PRIORITY_MEDIUM ("Medium Priority"),
    PRIORITY_LOW ("Low Priority");

    public static ToDoPriority getPriorityFromString(String name) {
        return ToDoPriority.valueOf(name);
    }

    private String strRep;

    private ToDoPriority(String strRep) {
        this.strRep = strRep;
    }

    @Override
    public String toString() {
        return strRep;
    }
}
