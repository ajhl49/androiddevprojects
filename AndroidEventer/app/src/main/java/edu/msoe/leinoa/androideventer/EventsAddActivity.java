package edu.msoe.leinoa.androideventer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventsAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Event");
    }
}
