package com.anhlang.pizzahutbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anhlang.pizzahutbooking.Object.MealsCart;
import com.anhlang.pizzahutbooking.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MealView extends AppCompatActivity {

    ImageView imageView, btn_back_menu;
    TextView name, price, description, count;
    Button plusCount, minusCount, add;
    EditText note;

    DatabaseReference reference;
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
        btn_back_menu = findViewById(R.id.btn_back_menu);

        Intent intent = getIntent();

        final String priceInt = intent.getStringExtra("price");

        Glide.with(MealView.this).load(intent.getStringExtra("picture")).into(imageView);
        name.setText(intent.getStringExtra("name"));
        price.setText(intent.getStringExtra("price") + " đ");
        description.setText(intent.getStringExtra("description"));

        btn_back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count.getText().toString().equals("0")){
                    finish();
                }else{
                    reference = FirebaseDatabase.getInstance().getReference().child("TABLE").child(getIntent().getStringExtra("table")).child("cache");
                    String id = reference.push().getKey();
                    String price = getIntent().getStringExtra("price");
                    MealsCart mealsCart = new MealsCart(name.getText().toString(),
                                                        price,
                                                        count.getText().toString(),
                                                        note.getText().toString(),
                                                        Integer.parseInt(price)*Integer.parseInt(count.getText().toString()) + "",
                                                        true,
                                                        id);

                    reference.child(id).setValue(mealsCart);
                    finish();
                }
            }
        });
    }

    private  void setCount(){
        if(count.getText().toString().equals("0")){
            add.setText(getResources().getString(R.string.back_to_menu));
        }else {
            add.setText(getResources().getString(R.string.add_to_cart));
        }
    }

}