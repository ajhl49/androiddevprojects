package edu.msoe.leinoa.midtermtodo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.msoe.leinoa.midtermtodo.model.ToDoItem;
import edu.msoe.leinoa.midtermtodo.model.ToDoPriority;
import edu.msoe.leinoa.midtermtodo.model.ToDoStatus;

/**
 * Created by aleino on 10/21/16.
 */

public class ToDoItemAdapter<T extends ToDoItem> extends ArrayAdapter {

    private static LayoutInflater inflater = null;

    public ToDoItemAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holder {
        ImageView priorityIcon;
        TextView titleText;
        TextView dateDueText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
        View rowView;

        rowView = inflater.inflate(R.layout.todo_list, null);
        holder.priorityIcon = (ImageView) rowView.findViewById(R.id.listPriorityIcon);
        holder.titleText = (TextView) rowView.findViewById(R.id.listTitleText);
        holder.dateDueText = (TextView) rowView.findViewById(R.id.listDateText);

        ToDoItem tdi = (ToDoItem) this.getItem(position);
        ToDoStatus tdiStatus = tdi.getStatus();
        if (tdiStatus.equals(ToDoStatus.READY)) {
            rowView.setBackgroundResource(R.drawable.ready_gradient);
        } else if (tdiStatus.equals(ToDoStatus.STARTED)) {
            rowView.setBackgroundResource(R.drawable.started_gradient);
        } else if (tdiStatus.equals(ToDoStatus.IN_PROGRESS)) {
            rowView.setBackgroundResource(R.drawable.in_progress_gradient);
        } else if (tdiStatus.equals(ToDoStatus.COMPLETE)) {
            rowView.setBackgroundResource(R.drawable.completed_gradient);
        }


        Drawable pDrawable;

        if (tdi.getPriority().equals(ToDoPriority.PRIORITY_HIGH)) {
            pDrawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_priority_high, null);
        } else if (tdi.getPriority().equals(ToDoPriority.PRIORITY_MEDIUM)) {
            pDrawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_priority_medium, null);
        } else {
            pDrawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_priority_low, null);
        }

        holder.priorityIcon.setImageDrawable(pDrawable);
        holder.titleText.setText(tdi.getTitle());
        holder.dateDueText.setText(dateFormat.format(tdi.getDateDue().getTime()));

        return rowView;
    }
}
