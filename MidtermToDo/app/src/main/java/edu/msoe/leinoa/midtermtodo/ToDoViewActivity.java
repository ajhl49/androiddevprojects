package edu.msoe.leinoa.midtermtodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import edu.msoe.leinoa.midtermtodo.model.ToDoItem;
import edu.msoe.leinoa.midtermtodo.model.ToDoPriority;
import edu.msoe.leinoa.midtermtodo.model.ToDoStatus;
import edu.msoe.leinoa.midtermtodo.sqlite.ToDoDBAdapter;

public class ToDoViewActivity extends AppCompatActivity {

    private ToDoItem tdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_view);

        Intent startIntent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ToDo Item");

        long rowId = startIntent.getLongExtra("todo_id", -1);
        Log.wtf("todo_id not passed!", "todo_id was not found in the intent");
        tdi = ToDoDBAdapter.getAdapter(this).getItemById(rowId);

        final TextView titleText = (TextView)findViewById(R.id.todo_view_title);
        titleText.setText(tdi.getTitle());
        titleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //DB stuff
                tdi.setTitle(titleText.getText().toString());
                ToDoDBAdapter.getAdapter(ToDoViewActivity.this).updateToDoItem(tdi);
            }
        });

        final TextView descriptionText = (TextView)findViewById(R.id.todo_view_description);
        descriptionText.setText(tdi.getDescription());
        descriptionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tdi.setDescription(descriptionText.getText().toString());
                ToDoDBAdapter.getAdapter(ToDoViewActivity.this).updateToDoItem(tdi);
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
        ((TextView)findViewById(R.id.todo_view_due_date)).setText(dateFormat.format(tdi.getDateDue().getTime()));
        ((TextView)findViewById(R.id.todo_view_date_created)).setText(dateFormat.format(tdi.getDateCreated().getTime()));

        Spinner prioritySpinner = (Spinner)findViewById(R.id.todo_view_priority_spinner);
        final ArrayAdapter<ToDoPriority> priorityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                android.R.id.text1);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);
        priorityAdapter.addAll(ToDoPriority.values());
        prioritySpinner.setSelection(priorityAdapter.getPosition(tdi.getPriority()));

        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToDoPriority newPriority = priorityAdapter.getItem(position);
                if (!newPriority.equals(tdi.getPriority())) {
                    tdi.setPriority(newPriority);
                    ToDoDBAdapter.getAdapter(ToDoViewActivity.this).updateToDoItem(tdi);
                    Toast.makeText(ToDoViewActivity.this, "ToDo priority updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing, we don't reset to null
            }
        });

        Spinner statusSpinner = (Spinner)findViewById(R.id.todo_view_status_spinner);
        final ArrayAdapter<ToDoStatus> statusAdapter = new ArrayAdapter<ToDoStatus>(this, android.R.layout.simple_spinner_item,
                android.R.id.text1);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
        statusAdapter.addAll(ToDoStatus.values());
        statusSpinner.setSelection(statusAdapter.getPosition(tdi.getStatus()));

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToDoStatus newStatus = statusAdapter.getItem(position);
                if (!newStatus.equals(tdi.getStatus())) {
                    tdi.setStatus(newStatus);
                    ToDoDBAdapter.getAdapter(ToDoViewActivity.this).updateToDoItem(tdi);
                    Toast.makeText(ToDoViewActivity.this, "ToDo status updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing, we don't reset to null
            }
        });
    }
}
