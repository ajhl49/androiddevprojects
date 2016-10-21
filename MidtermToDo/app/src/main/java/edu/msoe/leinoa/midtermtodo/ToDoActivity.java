package edu.msoe.leinoa.midtermtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.msoe.leinoa.midtermtodo.model.ToDoItem;
import edu.msoe.leinoa.midtermtodo.sqlite.ToDoDBAdapter;

public class ToDoActivity extends AppCompatActivity {

    public static final int NEW_TODO_REQUEST = 1;
    public static final int VIEW_TODO_REQUEST = 2;

    private ToDoDBAdapter dbAdapter;
    private List<ToDoItem> toDoItemList;
    private ArrayAdapter<ToDoItem> toDoItemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        dbAdapter = ToDoDBAdapter.getAdapter(this);

        ListView itemListView = (ListView)findViewById(R.id.todoList);

        toDoItemList = dbAdapter.getAllItems();
        toDoItemArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, toDoItemList);
        itemListView.setAdapter(toDoItemArrayAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ToDoActivity.this, ToDoViewActivity.class);
                intent.putExtra("todo_id", toDoItemList.get(position).getId());
                startActivityForResult(intent, VIEW_TODO_REQUEST);
            }
        });

        getSupportActionBar().setTitle("Aj's ToDo App");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == NEW_TODO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "New ToDo item created", Toast.LENGTH_SHORT).show();
                toDoItemList.add(dbAdapter.getItemById(data.getExtras().getLong("newId")));
                toDoItemArrayAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "ToDo item canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, NewToDoActivity.class);
        startActivityForResult(intent, NEW_TODO_REQUEST);
    }
}
