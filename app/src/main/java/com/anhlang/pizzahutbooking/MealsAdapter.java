package com.anhlang.pizzahutbooking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealsAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Meals> mealsList;

    TextView title;
    TextView price;
    TextView description;
    ImageView picture;

    public MealsAdapter(Context context, int layout, ArrayList<Meals> mealsList) {
        this.context = context;
        this.layout = layout;
        this.mealsList = mealsList;
    }

    @Override
    public int getCount() {
        return mealsList.size();
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.meals_listview, null, true);

        title = (TextView)rowView.findViewById(R.id.ls_title);
        price = (TextView)rowView.findViewById(R.id.ls_price);
        description = (TextView)rowView.findViewById(R.id.ls_description);
        picture = (ImageView)rowView.findViewById(R.id.ls_img);

        title.setText(mealsList.get(i).getName());

        price.setText(mealsList.get(i).getPrice() + " đ");

        description.setText(mealsList.get(i).getDescription());

        Glide.with(context).load(mealsList.get(i).getPicture()).centerCrop().error(R.drawable.a1_table).into(picture);

        return rowView;
    }

    public void viewMeal(String name, String price, String description, String picture){
        Intent intent = new Intent(context, MealView.class);

        intent.putExtra("name", name);
        intent.putExtra("price", price);
        intent.putExtra("description", description);
        intent.putExtra("picture", picture);

        context.startActivity(intent);
    }
}

