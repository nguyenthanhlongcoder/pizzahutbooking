package com.anhlang.pizzahutbooking.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anhlang.pizzahutbooking.Object.Notification;
import com.anhlang.pizzahutbooking.R;


import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {
    Context context;
    ArrayList<Notification> arrayList;
    int layout;
    TextView info;
    TextView time;

    public NotificationAdapter(Context context, ArrayList<Notification> arrayList, int layout) {
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_notification, null, true);

        info = rowView.findViewById(R.id.txt_info_notification);
        time = rowView.findViewById(R.id.text_txt_time_notification);

        if (arrayList.get(position).getRead().equals(false)){
            info.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            info.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        String content = "";
        if(arrayList.get(position).getType().equals("order")){
            content = arrayList.get(position).getName() + " " + context.getResources().getString(R.string.has_ordered)
                        + " " + arrayList.get(position).getMeal()
                        + " " + context.getResources().getString(R.string.forr)
                        + " " + arrayList.get(position).getTable();
        }else{
            content = arrayList.get(position).getName() + " " + context.getResources().getString(R.string.has_removed)
                    + " " + arrayList.get(position).getMeal()
                    + " " + context.getResources().getString(R.string.from)
                    + " " + arrayList.get(position).getTable();
        }
        info.setText(content);
        time.setText(arrayList.get(position).getTime());
        return  rowView;
    }
}
