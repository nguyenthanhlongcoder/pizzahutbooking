package com.anhlang.pizzahutbooking.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.anhlang.pizzahutbooking.R;

import java.util.zip.Inflater;


public class MainAdapter extends BaseAdapter {

    private Context context;
    private int[] table;
    private LayoutInflater inflater;
    private  ImageView imageView;

    public MainAdapter(Context c, int[] t ){
        this.context = c;
        this.table = t;
    }

    @Override
    public int getCount() {
        return table.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null)
        {
            view = inflater.inflate(R.layout.order_table, null);
        }

        imageView = view.findViewById(R.id.table_image);

        imageView.setImageResource(table[i]);
        return view;
    }
}
