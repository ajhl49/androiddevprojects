package edu.msoe.leinoa.fragmenttodolist;


import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import edu.msoe.leinoa.fragmenttodolist.models.ToDoItem;
import edu.msoe.leinoa.fragmenttodolist.sqlite.ToDoDBAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListFragment extends android.support.v4.app.ListFragment implements AdapterView.OnItemClickListener {


    public MyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);


        // Get references to UI widgets

        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ToDoDBAdapter.init(getActivity());
        final EditText myEditText = (EditText)view.findViewById(R.id.myEditText);

        final List<ToDoItem> todoItems = new ArrayList();
        final ArrayAdapter<ToDoItem> aa = new ArrayAdapter<ToDoItem>(getActivity(), android.R.layout.simple_list_item_1, todoItems);

        setListAdapter(aa);
        getListView().setOnItemClickListener(this);

        List<ToDoItem> savedTdiList = ToDoDBAdapter.getAllToDoItems();
        todoItems.addAll(savedTdiList);
        aa.notifyDataSetChanged();

        myEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        String title = myEditText.getText().toString();

                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.clear();
                        calendar.set(year, month, day);
                        long secondsSinceEpoch = calendar.getTimeInMillis() / 1000L;

                        ToDoItem tdi = new ToDoItem(myEditText.getText().toString(), "Default body text", secondsSinceEpoch);
                        todoItems.add(0, tdi);
                        aa.notifyDataSetChanged();

                        tdi.setId(ToDoDBAdapter.addToDoItem(tdi));

                        ToDoActivity td = (ToDoActivity)getActivity();
                        td.sendNotification("New ToDo Item", myEditText.getText().toString());

                        myEditText.setText("Add new ToDo item...");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToDoActivity td = (ToDoActivity)getActivity();
        td.sendNotification("Item Click", "Pos: " + position + " id: " + id);
    }


}
