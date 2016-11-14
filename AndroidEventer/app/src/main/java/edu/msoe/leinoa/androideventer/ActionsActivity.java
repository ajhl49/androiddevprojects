package edu.msoe.leinoa.androideventer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import edu.msoe.leinoa.androideventer.model.actions.Action;
import edu.msoe.leinoa.androideventer.sqlite.EventerDBAdapter;

public class ActionsActivity extends AppCompatActivity {

    private EventerDBAdapter dbAdapter;
    private List<Action> actionList;
    private ArrayAdapter actionArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Actions");

        dbAdapter = EventerDBAdapter.getDbAdapter((getParent() != null) ? getParent() : this);

        final ListView actionListView = (ListView) findViewById(R.id.actions_list);
        actionList = dbAdapter.getAllActions();
        actionArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, actionList);
        actionListView.setAdapter(actionArrayAdapter);

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActionsActivity.this, ActionsViewActivity.class);
                intent.putExtra("action_uuid", actionList.get(position).getUuid());
                startActivityForResult(intent, AndroidEventer.ACTION_DETAIL_VIEW_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case AndroidEventer.ACTION_ADD_REQUEST: {
                break;
            }
            case AndroidEventer.ACTION_DETAIL_VIEW_REQUEST: {
                break;
            }
            default: {
                break;
            }
        }
    }

    public void btn_addAction(View view) {
        Intent intent = new Intent(this, ActionsAddActivity.class);
        startActivityForResult(intent, AndroidEventer.ACTION_ADD_REQUEST);
    }
}
