package edu.msoe.leinoa.androideventer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import edu.msoe.leinoa.androideventer.model.actions.Action;
import edu.msoe.leinoa.androideventer.sqlite.EventerDBAdapter;

public class EventsAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_add);

        Spinner actionSpinner = (Spinner) findViewById(R.id.events_add_action);
        ArrayAdapter<Action> actionArrayAdapter = new ArrayAdapter<Action>(this, android.R.layout.simple_spinner_item,
                android.R.id.text1);
        actionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionSpinner.setAdapter(actionArrayAdapter);
        actionArrayAdapter.addAll(EventerDBAdapter.getDbAdapter(this).getAllActions());
        actionSpinner.setSelection(0);

        //Spinner triggerSpinner = (Spinner) findViewById()

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Event");
    }

    public void btn_saveEvent(View view) {
    }

    public void btn_cancelEventer(View view) {
    }
}
