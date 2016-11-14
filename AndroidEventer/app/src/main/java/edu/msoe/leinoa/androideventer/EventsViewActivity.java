package edu.msoe.leinoa.androideventer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import edu.msoe.leinoa.androideventer.model.BoundEvent;
import edu.msoe.leinoa.androideventer.sqlite.EventerDBAdapter;

public class EventsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event Detail");

        Intent intent = getIntent();
        String uuid = intent.getStringExtra("event_uuid");

        BoundEvent boundEvent = EventerDBAdapter.getDbAdapter(this).getBoundEventByUuid(uuid);

        TextView title = (TextView) findViewById(R.id.events_view_title);
        TextView description = (TextView) findViewById(R.id.events_view_description);
        TextView dateCreated = (TextView) findViewById(R.id.events_view_date_created);
        TextView actionText = (TextView) findViewById(R.id.events_view_action);
        TextView triggerText = (TextView) findViewById(R.id.events_view_trigger);

        title.setText(boundEvent.getTitle());
        description.setText(boundEvent.getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
        dateCreated.setText(dateFormat.format(boundEvent.getDateCreated().getTime()));

        actionText.setText(boundEvent.getAction().toString());
        triggerText.setText(boundEvent.getTrigger().toString());
    }
}
