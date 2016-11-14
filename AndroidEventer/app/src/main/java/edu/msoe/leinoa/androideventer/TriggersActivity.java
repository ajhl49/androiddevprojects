package edu.msoe.leinoa.androideventer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import edu.msoe.leinoa.androideventer.model.triggers.Trigger;
import edu.msoe.leinoa.androideventer.sqlite.EventerDBAdapter;

public class TriggersActivity extends AppCompatActivity {

    private EventerDBAdapter dbAdapter;
    private List<Trigger> triggerList;
    private ArrayAdapter triggerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triggers);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Triggers");

        dbAdapter = EventerDBAdapter.getDbAdapter((getParent() != null) ? getParent() : this);

        ListView triggerListView = (ListView) findViewById(R.id.triggers_list);
        triggerList = dbAdapter.getAllTriggers();
        triggerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, triggerList);
        triggerListView.setAdapter(triggerArrayAdapter);

        triggerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TriggersActivity.this, TriggersViewActivity.class);
                intent.putExtra("trigger_uuid", triggerList.get(position).getUuid());
                startActivityForResult(intent, AndroidEventer.TRIGGER_DETAIL_VIEW_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case AndroidEventer.TRIGGER_ADD_REQUEST: {
                break;
            }
            case AndroidEventer.TRIGGER_DETAIL_VIEW_REQUEST: {
                break;
            }
            default: {
                break;
            }
        }
    }

    public void addTrigger(View view) {
        Intent intent = new Intent(this, TriggersAddActivity.class);
        startActivityForResult(intent, AndroidEventer.TRIGGER_ADD_REQUEST);
    }
}
