package edu.msoe.leinoa.androideventer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActionsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Action Detail");
    }
}
