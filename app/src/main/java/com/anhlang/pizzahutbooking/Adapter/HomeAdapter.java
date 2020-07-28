package com.anhlang.pizzahutbooking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.anhlang.pizzahutbooking.R;

public class HomeAdapter extends BaseAdapter {
    private Context context;
    private int[] news;
    private LayoutInflater inflater;
    private ImageView imageView;

    public HomeAdapter(Context c, int[] t ){
        this.context = c;
        this.news = t;
    }

    @Override
    public int getCount() {
        return news.length;
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
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null) {
            view = inflater.inflate(R.layout.list_news, null);
        }

        imageView = view.findViewById(R.id.news_img);

        imageView.setImageResource(news[i]);
        return view;
    }
}
