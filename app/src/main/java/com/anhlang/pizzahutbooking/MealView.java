package com.anhlang.pizzahutbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.internal.InternalTokenProvider;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MealView extends AppCompatActivity {

    ImageView imageView;

    TextView name, price, description, count;

    Button plusCount, minusCount, add;

    EditText note;

    SharedPreferences preferences;

    ArrayList<MealsCart> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view);

        imageView = findViewById(R.id.img_meal);
        name = findViewById(R.id.txt_mealName);
        price = findViewById(R.id.text_mealPrice);
        description = findViewById(R.id.txt_description);
        plusCount = findViewById(R.id.btn_plus);
        minusCount = findViewById(R.id.btn_minus);
        count = findViewById(R.id.txt_count);
        add = findViewById(R.id.btn_add);
        note = findViewById(R.id.edt_note);

        Intent intent = getIntent();

        final String priceInt = intent.getStringExtra("price");

        Glide.with(MealView.this).load(intent.getStringExtra("picture")).into(imageView);
        name.setText(intent.getStringExtra("name"));
        price.setText(intent.getStringExtra("price") + " đ");
        description.setText(intent.getStringExtra("description"));

        plusCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.setText((Integer.parseInt(count.getText().toString()) + 1) + "");
                setCount();
            }
        });

        minusCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(count.getText().toString()) > 0) {
                    count.setText((Integer.parseInt(count.getText().toString()) - 1) + "");
                    setCount();
                }
            }
        });

        preferences = getSharedPreferences("mealscart", MODE_PRIVATE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count.getText().toString().equals("0")){
                    finish();
                }else{
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("name", name.getText().toString());
                    editor.putString("price", priceInt);
                    editor.putString("count", count.getText().toString());
                    editor.putString("note", note.getText().toString());

                    editor.apply();
                    finish();
                }
            }
        });
    }

    private  void setCount(){
        if(count.getText().toString().equals("0")){
            add.setText("Back to Menu");
        }else {
            add.setText("Add to Cart");
        }
    }

}