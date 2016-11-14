package edu.msoe.leinoa.androideventer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import edu.msoe.leinoa.androideventer.model.BoundEvent;
import edu.msoe.leinoa.androideventer.sqlite.EventerDBAdapter;

public class EventsActivity extends AppCompatActivity {

    private EventerDBAdapter dbAdapter;
    private List<BoundEvent> eventList;
    private ArrayAdapter eventArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Events");

        dbAdapter = EventerDBAdapter.getDbAdapter((getParent() != null) ? getParent() : this);

        ListView eventsListView = (ListView) findViewById(R.id.events_list);
        eventList = dbAdapter.getAllBoundEvents();
        eventArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, eventList);
        eventsListView.setAdapter(eventArrayAdapter);

        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventsActivity.this, EventsViewActivity.class);
                intent.putExtra("event_uuid", eventList.get(position).getUuid());
                startActivityForResult(intent, AndroidEventer.EVENT_DETAIL_VIEW_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case AndroidEventer.EVENT_ADD_REQUEST: {
                break;
            }
            case AndroidEventer.EVENT_DETAIL_VIEW_REQUEST: {
                break;
            }
            default: {
                break;
            }
        }
    }

    public void btn_addEvent(View view) {
        Intent intent = new Intent(this, EventsAddActivity.class);
        startActivityForResult(intent, AndroidEventer.EVENT_ADD_REQUEST);
    }
}
