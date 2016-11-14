package edu.msoe.leinoa.androideventer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TriggersAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triggers_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Trigger");
    }
}
