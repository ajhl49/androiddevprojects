package edu.msoe.leinoa.androideventer.events;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.msoe.leinoa.androideventer.model.BoundEvent;
import edu.msoe.leinoa.androideventer.model.ExternalEventInterface;
import edu.msoe.leinoa.androideventer.model.actions.Action;
import edu.msoe.leinoa.androideventer.model.triggers.Trigger;
import edu.msoe.leinoa.androideventer.sqlite.EventerDBAdapter;

/**
 * Created by aleino on 11/12/16.
 */

public class EventerRegistrar {

    private static EventerRegistrar eventerRegistrar = null;

    public static EventerRegistrar getEventerRegistrar() {
        if (eventerRegistrar == null) {
            eventerRegistrar = new EventerRegistrar();
        }
        return eventerRegistrar;
    }

    private HashMap<String, BoundEvent> registeredEvents;
    private HashMap<String, Action> registeredActions;
    private HashMap<String, Trigger> registeredTriggers;

    private EventerRegistrar() {
        registeredEvents = new HashMap<>();
        registeredActions = new HashMap<>();
        registeredTriggers = new HashMap<>();
    }

    public List<BoundEvent> getAllMatchingBound(ExternalEventInterface externalEvent) {
        return null;
    }

    public void updateFromDatabase(Context context) {
        EventerDBAdapter dbAdapter = EventerDBAdapter.getDbAdapter(context);

        List<Action> actions = dbAdapter.getAllActions();
        for (Action action : actions) {
            registeredActions.put(action.getUuid(), action);
        }

        List<Trigger> triggers = dbAdapter.getAllTriggers();
        for (Trigger trigger : triggers) {
            registeredTriggers.put(trigger.getUuid(), trigger);
        }

        List<BoundEvent> boundEvents = dbAdapter.getAllBoundEvents();
        for (BoundEvent boundEvent : boundEvents) {
            registeredEvents.put(boundEvent.getUuid(), boundEvent);
        }
    }

    public void addBoundEvent(BoundEvent boundEvent) {
        if (boundEvent == null) {
            throw new NullPointerException("Bound events cannot be null!");
        }
        registeredEvents.put(boundEvent.getUuid(), boundEvent);
    }

    public void addAction(Action action) {
        if (action == null) {
            throw new NullPointerException("Actions cannot be null!");
        }
        registeredActions.put(action.getUuid(), action);
    }

    public void addTrigger(Trigger trigger) {
        if (trigger == null) {
            throw new NullPointerException("Triggers cannot be null!");
        }
        registeredTriggers.put(trigger.getUuid(), trigger);
    }

    public BoundEvent getBoundEvent(String uuid) {
        return registeredEvents.get(uuid);
    }

    public Trigger getTrigger(String uuid) {
        return registeredTriggers.get(uuid);
    }

    public Action getAction(String uuid) {
        return registeredActions.get(uuid);
    }

    public void removeBoundEvent(BoundEvent boundEvent) {

    }

    public void removeBoundEvent(String uuid) {

    }

    public void removeAction(Action action) {

    }

    public void removeAction(String uuid) {

    }

    public void removeTrigger(Trigger trigger) {

    }

    public void removeTrigger(String uuid) {

    }
}
