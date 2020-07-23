package com.anhlang.pizzahutbooking;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MealsCartAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<MealsCart> arrayList;

    TextView name;
    TextView price;
    TextView count;
    TextView note;
    TextView edit;

    public MealsCartAdapter(Context context, int layout, ArrayList<MealsCart> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_order, null, true);

        name = (TextView)view.findViewById(R.id.txt_name);
        price = (TextView)view.findViewById(R.id.txt_price);
        count = (TextView)view.findViewById(R.id.txt_count);
        note = (TextView)view.findViewById(R.id.txt_note);
        edit = (TextView)view.findViewById(R.id.btn_edit);

        name.setText(arrayList.get(position).getName());
        price.setText(Integer.parseInt(arrayList.get(position).getPrice()) * Integer.parseInt(arrayList.get(position).getCount()) + " đ");
        count.setText(arrayList.get(position).getCount() + "x");
        note.setText(arrayList.get(position).getNote());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MealViewEdit.class);

                intent.putExtra("position", position);
                intent.putExtra("name", arrayList.get(position).getName());
                intent.putExtra("count", arrayList.get(position).getCount());
                intent.putExtra("note", arrayList.get(position).getNote());
                intent.putExtra("price", arrayList.get(position).getPrice());

                context.startActivity(intent);
            }
        });

        return view;
    }
}
