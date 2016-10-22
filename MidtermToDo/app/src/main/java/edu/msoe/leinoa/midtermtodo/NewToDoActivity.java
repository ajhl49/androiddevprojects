package edu.msoe.leinoa.midtermtodo;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import edu.msoe.leinoa.midtermtodo.model.ToDoItem;
import edu.msoe.leinoa.midtermtodo.model.ToDoPriority;
import edu.msoe.leinoa.midtermtodo.model.ToDoStatus;
import edu.msoe.leinoa.midtermtodo.sqlite.ToDoDBAdapter;

public class NewToDoActivity extends AppCompatActivity {

    private Calendar dateCalendar = null;
    private Calendar timeCalendar = null;

    private static String[] TODO_TITLES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_to_do);

        Spinner prioritySpinner = (Spinner) findViewById(R.id.priority_spinner);
        ArrayAdapter<ToDoPriority> priorityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                android.R.id.text1);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);
        priorityAdapter.addAll(ToDoPriority.values());
        prioritySpinner.setSelection(priorityAdapter.getPosition(ToDoPriority.PRIORITY_LOW));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create New");

        ToDoDBAdapter dbAdapter = ToDoDBAdapter.getAdapter(this);
        List<ToDoItem> toDoItemList = dbAdapter.getAllItems();
        TODO_TITLES = new String[toDoItemList.size()];
        for (int i = 0; i < TODO_TITLES.length; i++) {
            TODO_TITLES[i] = toDoItemList.get(i).getTitle();
        }
        ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TODO_TITLES);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.title_box_text);
        textView.setAdapter(titleAdapter);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void saveToDoItem(View view) {
        ToDoDBAdapter dbAdapter = ToDoDBAdapter.getAdapter(getParent());

        ToDoItem tdi = new ToDoItem();

        tdi.setTitle(((EditText)findViewById(R.id.title_box_text)).getText().toString());
        if (tdi.getTitle() == null || tdi.getTitle().trim().equals("")) {
            alertUserBadForm("Need Title for ToDo");
            return;
        }
        tdi.setDescription(((EditText)findViewById(R.id.description_box_text)).getText().toString());
        if (tdi.getDescription() == null) {
            //ToDo items can have empty descriptions
            tdi.setDescription("");
        }

        Spinner spinner = (Spinner) findViewById(R.id.priority_spinner);
        tdi.setPriority((ToDoPriority)spinner.getSelectedItem());

        tdi.setStatus(ToDoStatus.READY);

        if (dateCalendar == null) {
            alertUserBadForm("Please Select Date");
            return;
        } else if (timeCalendar == null) {
            alertUserBadForm("Please Select Time");
            return;
        }
        Calendar dateDue = Calendar.getInstance();
        dateDue.clear();
        dateDue.set(dateCalendar.get(Calendar.YEAR),
                dateCalendar.get(Calendar.MONTH),
                dateCalendar.get(Calendar.DAY_OF_MONTH),
                timeCalendar.get(Calendar.HOUR_OF_DAY),
                timeCalendar.get(Calendar.MINUTE));
        tdi.setDateDue(dateDue);

        tdi.setDateCreated(Calendar.getInstance());

        long rowid = dbAdapter.addToDoItem(tdi).getId();
        tdi.setId(rowid);

        setAlarm(tdi);

        Intent intent = new Intent();
        intent.putExtra("newId", rowid);
        setResult(RESULT_OK, intent);
        finish();
    }

    void setDateCalendar(Calendar dateCalendar) {
        this.dateCalendar = dateCalendar;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ((TextView)findViewById(R.id.dateText)).setText(dateFormat.format(dateCalendar.getTime()));
    }

    void setTimeCalendar(Calendar timeCalendar) {
        this.timeCalendar = timeCalendar;
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssa z");
        ((TextView)findViewById(R.id.timeText)).setText(timeFormat.format(timeCalendar.getTime()));
    }

    public void cancelToDoItem(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void setAlarm(ToDoItem tdi) {
        Intent intent = new Intent(this, NotificationAlarmReceiver.class);
        intent.putExtra("todo_id", tdi.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, tdi.getDateDue().getTimeInMillis(), pendingIntent);
    }

    private void alertUserBadForm(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
