package com.anhlang.pizzahutbooking.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.anhlang.pizzahutbooking.Activity.MealViewEdit;
import com.anhlang.pizzahutbooking.Activity.OrderActivity;
import com.anhlang.pizzahutbooking.Object.MealsCart;
import com.anhlang.pizzahutbooking.Object.Notification;
import com.anhlang.pizzahutbooking.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MealsCartAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<MealsCart> arrayList;

    TextView subTotal;
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

        if(arrayList.get(position).getCache() == true){
            name.setTextColor(view.getResources().getColor(R.color.colorPrimary));
            edit.setText(context.getResources().getString(R.string.edit));
            edit.setTextColor(view.getResources().getColor(R.color.colorPrimary));
        }else{
            name.setTextColor(view.getResources().getColor(R.color.colorTextDefault));
            edit.setText(context.getResources().getString(R.string.remove));
            edit.setTextColor(view.getResources().getColor(R.color.colorRed));
        }

        name.setText(arrayList.get(position).getName());
        price.setText(arrayList.get(position).getTotal() + " đ");
        count.setText(arrayList.get(position).getCount() + "x");
        note.setText(arrayList.get(position).getNote());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.get(position).getCache() == true) {
                    Intent intent = new Intent(context, MealViewEdit.class);

                    intent.putExtra("position", position);
                    intent.putExtra("name", arrayList.get(position).getName());
                    intent.putExtra("count", arrayList.get(position).getCount());
                    intent.putExtra("note", arrayList.get(position).getNote());
                    intent.putExtra("price", arrayList.get(position).getPrice());
                    intent.putExtra("id", arrayList.get(position).getId());
                    intent.putExtra("table", ((Activity) context).getIntent().getStringExtra("table"));
                    ((Activity) context).startActivityForResult(intent, 0);

                    arrayList.remove(position);
                }else{
                    if (((Activity) context).getIntent().getBundleExtra("account").getString("position").equals("manager")) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

                        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

                        dialogBuilder.setView(dialogView);

                        TextView title = dialogView.findViewById(R.id.txt_dialog_title);
                        TextView content = dialogView.findViewById(R.id.txt_dialog_content);
                        Button positive = dialogView.findViewById(R.id.btn_positive);
                        Button negative = dialogView.findViewById(R.id.btn_negative);

                        title.setText(context.getResources().getString(R.string.remove_alter_title));
                        content.setText(context.getResources().getString(R.string.remove_alter_content));
                        final AlertDialog alertDialog = dialogBuilder.create();

                        alertDialog.show();
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("TABLE").child(((Activity) context).getIntent().getStringExtra("table")).child("meals").child(arrayList.get(position).getId());
                                reference.removeValue();
                                alertDialog.dismiss();

                                DatabaseReference referenceSetNotification = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION");

                                String name = ((Activity) context).getIntent().getBundleExtra("account").getString("name");
                                String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                                String meal = arrayList.get(position).getName() + "x" + arrayList.get(position).getCount();
                                String id = referenceSetNotification.push().getKey();
                                referenceSetNotification.child(id).setValue(new Notification(name, meal, ((Activity) context).getIntent().getStringExtra("table"),currentTime, false, id, "remove"));
                            }
                        });

                        negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });

                    }else{
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

                        View dialogView = inflater.inflate(R.layout.custom_alert_dialog_2, null);

                        dialogBuilder.setView(dialogView);

                        TextView title = dialogView.findViewById(R.id.txt_dialog_title_2);
                        TextView content = dialogView.findViewById(R.id.txt_dialog_content_2);
                        Button positive = dialogView.findViewById(R.id.btn_positive_2);

                        title.setText(context.getResources().getString(R.string.manger_permission));
                        content.setText(context.getResources().getString(R.string.manager_permission_content));
                        final AlertDialog alertDialog = dialogBuilder.create();

                        alertDialog.show();
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                    }
                }
            }
        });

        return view;
    }

    public int getSubTotal(){
        int total = 0;
        for(int i = 0; i < arrayList.size(); i++){
            total += Integer.parseInt(arrayList.get(i).getTotal());
        }

        return total;
    }
}
