package edu.msoe.leinoa.midtermtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.msoe.leinoa.midtermtodo.model.ToDoItem;
import edu.msoe.leinoa.midtermtodo.model.ToDoPriority;
import edu.msoe.leinoa.midtermtodo.model.ToDoStatus;
import edu.msoe.leinoa.midtermtodo.sqlite.ToDoDBAdapter;

public class ToDoActivity extends AppCompatActivity {

    public static final int NEW_TODO_REQUEST = 1;
    public static final int VIEW_TODO_REQUEST = 2;

    private ToDoDBAdapter dbAdapter;
    private List<ToDoItem> toDoItemList;
    private ToDoItemAdapter toDoItemAdapter;

    private CharSequence sortBy = "Date";
    private boolean isDescending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        dbAdapter = ToDoDBAdapter.getAdapter(this);

        ListView itemListView = (ListView)findViewById(R.id.todoList);

        toDoItemList = dbAdapter.getAllItems();
        toDoItemAdapter = new ToDoItemAdapter(this, android.R.layout.simple_list_item_1, toDoItemList);
        itemListView.setAdapter(toDoItemAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ToDoActivity.this, ToDoViewActivity.class);
                intent.putExtra("todo_id", toDoItemList.get(position).getId());
                startActivityForResult(intent, VIEW_TODO_REQUEST);
            }
        });

        final Spinner sortSpinner = (Spinner)findViewById(R.id.sortSpinner);
        final ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortBy = (CharSequence) sortSpinner.getSelectedItem();
                performSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }
        });
        sortSpinner.setSelection(0);
        sortBy = (CharSequence) sortSpinner.getSelectedItem();

        final Spinner orderSpinner = (Spinner)findViewById(R.id.orderSpinner);
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(this,
                R.array.order_by_array, android.R.layout.simple_spinner_item);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderAdapter);
        orderSpinner.setSelection(0);
        isDescending = (orderSpinner.getSelectedItem().equals("Descending")) ? true : false;

        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (orderSpinner.getSelectedItem().equals("Descending")) {
                    isDescending = true;
                } else {
                    isDescending = false;
                }
                performSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        performSort();
        getSupportActionBar().setTitle("Aj's ToDo App");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == NEW_TODO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "New ToDo item created", Toast.LENGTH_SHORT).show();
                toDoItemList.add(dbAdapter.getItemById(data.getExtras().getLong("newId")));
                toDoItemAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "ToDo item canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == VIEW_TODO_REQUEST) {
            refreshItems();
        }
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, NewToDoActivity.class);
        startActivityForResult(intent, NEW_TODO_REQUEST);
    }

    private void refreshItems() {
        toDoItemList.clear();
        toDoItemList.addAll(dbAdapter.getAllItems());
        performSort();
        toDoItemAdapter.notifyDataSetChanged();
    }

    private void performSort() {
        if (sortBy.equals("Date")) {
            sortByDate();
        } else if (sortBy.equals("Priority")) {
            sortByPriority();
        } else if (sortBy.equals("Status")) {
            sortByStatus();
        }

        toDoItemAdapter.notifyDataSetChanged();
    }

    private void sortByDate() {
        if (isDescending) {
            Collections.sort(toDoItemList, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    return o1.getDateDue().compareTo(o2.getDateDue());
                }
            });
        } else {
            Collections.sort(toDoItemList, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    return o2.getDateDue().compareTo(o1.getDateDue());
                }
            });
        }
    }

    private void sortByPriority() {
        if (isDescending) {
            Collections.sort(toDoItemList, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    List<ToDoPriority> l = new ArrayList();
                    Collections.addAll(l, ToDoPriority.values());
                    return (l.indexOf(o1.getPriority())) - l.indexOf(o2.getPriority());
                }
            });
        } else {
            Collections.sort(toDoItemList, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    List<ToDoPriority> l = new ArrayList();
                    Collections.addAll(l, ToDoPriority.values());
                    return (l.indexOf(o2.getPriority())) - l.indexOf(o1.getPriority());
                }
            });
        }
    }

    private void sortByStatus() {
        if (isDescending) {
            Collections.sort(toDoItemList, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    List<ToDoStatus> l = new ArrayList();
                    Collections.addAll(l, ToDoStatus.values());
                    return (l.indexOf(o1.getStatus())) - l.indexOf(o2.getStatus());
                }
            });
        } else {
            Collections.sort(toDoItemList, new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    List<ToDoStatus> l = new ArrayList();
                    Collections.addAll(l, ToDoStatus.values());
                    return (l.indexOf(o2.getStatus())) - l.indexOf(o1.getStatus());
                }
            });
        }
    }
}
