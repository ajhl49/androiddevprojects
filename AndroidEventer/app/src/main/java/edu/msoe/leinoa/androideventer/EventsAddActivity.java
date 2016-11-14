package edu.msoe.leinoa.androideventer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

import edu.msoe.leinoa.androideventer.model.BoundEvent;
import edu.msoe.leinoa.androideventer.model.actions.Action;
import edu.msoe.leinoa.androideventer.model.triggers.Trigger;
import edu.msoe.leinoa.androideventer.sqlite.EventerDBAdapter;

public class EventsAddActivity extends AppCompatActivity {

    private Spinner actionSpinner;
    private ArrayAdapter<Action> actionArrayAdapter;

    private Spinner triggerSpinner;
    private ArrayAdapter<Trigger> triggerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_add);

        actionSpinner = (Spinner) findViewById(R.id.events_add_action);
        actionArrayAdapter = new ArrayAdapter<Action>(this, android.R.layout.simple_spinner_item,
                android.R.id.text1);
        actionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionSpinner.setAdapter(actionArrayAdapter);
        actionArrayAdapter.addAll(EventerDBAdapter.getDbAdapter(this).getAllActions());
        actionSpinner.setSelection(0);

        triggerSpinner = (Spinner) findViewById(R.id.events_add_trigger);
        triggerArrayAdapter = new ArrayAdapter<Trigger>(this, android.R.layout.simple_spinner_item,
                android.R.id.text1);
        triggerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        triggerSpinner.setAdapter(triggerArrayAdapter);
        triggerArrayAdapter.addAll(EventerDBAdapter.getDbAdapter(this).getAllTriggers());
        triggerSpinner.setSelection(0);

        //Spinner triggerSpinner = (Spinner) findViewById()

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Event");
    }

    public void btn_saveEvent(View view) {
        EventerDBAdapter dbAdapter = EventerDBAdapter.getDbAdapter(this);

        String uuid = UUID.randomUUID().toString();
        String title = ((EditText) findViewById(R.id.events_add_title)).getText().toString();
        String description = ((EditText) findViewById(R.id.events_add_description)).getText().toString();
        Action action = actionArrayAdapter.getItem(actionSpinner.getSelectedItemPosition());
        Trigger trigger = triggerArrayAdapter.getItem(triggerSpinner.getSelectedItemPosition());
        byte[] eventData = new byte[0];
        Calendar dateCreated = Calendar.getInstance();

        if (title == null || title.trim().equals("")) {
            Toast.makeText(this, "Needs title", Toast.LENGTH_SHORT).show();
            return;
        } else if (description == null || description.trim().equals("")) {
            Toast.makeText(this, "Needs description", Toast.LENGTH_SHORT).show();
            return;
        }

        BoundEvent boundEvent = new BoundEvent(uuid, title, description, eventData, trigger,
                action, dateCreated);
        EventerDBAdapter.getDbAdapter(this).addBoundEvent(boundEvent);

        Intent intent = new Intent();
        intent.putExtra("event_uuid", boundEvent.getUuid());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void btn_cancelEventer(View view) {
        Toast.makeText(this, "Creation cancelled", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
