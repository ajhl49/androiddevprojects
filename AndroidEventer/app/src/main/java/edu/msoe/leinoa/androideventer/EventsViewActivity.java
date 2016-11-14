package edu.msoe.leinoa.androideventer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event Detail");
    }
}
