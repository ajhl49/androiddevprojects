package model;

/**
 * Created by aleino on 11/11/16.
 */

public interface TriggerInterface {

    public long getId();

    public void setId();

    public boolean isTriggered(ExternalEventInterface externalEvent);
}