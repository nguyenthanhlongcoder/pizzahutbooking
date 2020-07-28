package com.anhlang.pizzahutbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anhlang.pizzahutbooking.Object.Meals;
import com.anhlang.pizzahutbooking.Object.MealsCart;
import com.anhlang.pizzahutbooking.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MealViewEdit extends AppCompatActivity {

    TextView mealName;
    TextView mealPrice, count;

    EditText mealNote;
    Button plus, minus, update;

    String id;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view_edit);

        mealName = findViewById(R.id.txt_mealName_edit);
        mealPrice = findViewById(R.id.txt_mealPrice_edit);
        mealNote = findViewById(R.id.edt_note_edit);

        count = findViewById(R.id.txt_count_edit);
        plus = findViewById(R.id.btn_plus_edit);
        minus = findViewById(R.id.btn_minus_edit);
        update = findViewById(R.id.btn_update);

        final Intent intent = getIntent();

        mealName.setText(intent.getStringExtra("name"));
        mealPrice.setText(intent.getStringExtra("price"));
        mealNote.setText(intent.getStringExtra("note"));
        count.setText(intent.getStringExtra("count"));
        id = intent.getStringExtra("id");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.setText((Integer.parseInt(count.getText().toString()) + 1) + "");
                setCount();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(count.getText().toString()) > 0) {
                    count.setText((Integer.parseInt(count.getText().toString()) - 1) + "");
                    setCount();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("TABLE").child(getIntent().getStringExtra("table")).child("cache").child(getIntent().getStringExtra("id"));
                reference.removeValue();
                if (!count.getText().toString().equals("0")) {
                    MealsCart mealsCart = new MealsCart(getIntent().getStringExtra("name"),
                            getIntent().getStringExtra("price"),
                            count.getText().toString(),
                            mealNote.getText().toString(),
                            Integer.parseInt(getIntent().getStringExtra("price")) * Integer.parseInt((String) count.getText()) + "",
                            true,
                            getIntent().getStringExtra("id"));
                    reference = FirebaseDatabase.getInstance().getReference().child("TABLE").child(getIntent().getStringExtra("table")).child("cache").child(getIntent().getStringExtra("id"));
                    reference.setValue(mealsCart);
                }
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("position", getIntent().getIntExtra("position", 0));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private  void setCount() {
        if (count.getText().toString().equals("0")) {
            update.setText(getResources().getString(R.string.remove_item));
            update.setBackgroundColor(Color.RED);
        } else {
            update.setText(getResources().getString(R.string.update_item));
            update.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}