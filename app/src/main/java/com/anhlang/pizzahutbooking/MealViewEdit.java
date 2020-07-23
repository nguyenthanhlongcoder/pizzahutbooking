package com.anhlang.pizzahutbooking;

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

public class MealViewEdit extends AppCompatActivity {

    TextView mealName;
    TextView mealPrice, count;

    EditText mealNote;
    Button plus, minus, update;

    int position = 0;
    SharedPreferences preferencesRemove;
    SharedPreferences preferencesUpdate;

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

        position = intent.getIntExtra("position", 0);

        preferencesRemove = getSharedPreferences("remove", MODE_PRIVATE);
        preferencesUpdate = getSharedPreferences("update", MODE_PRIVATE);

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
                if (count.getText().toString().equals("0")){
                    SharedPreferences.Editor editor = preferencesRemove.edit();

                    editor.putInt("position", position);

                    editor.apply();
                    finish();
                }else{
                    SharedPreferences.Editor editor = preferencesUpdate.edit();

                    editor.putInt("position", position);
                    editor.putString("note", mealNote.getText().toString());
                    editor.putString("count", count.getText().toString());

                    editor.apply();
                    finish();
                }
            }
        });
    }
    private  void setCount() {
        if (count.getText().toString().equals("0")) {
            update.setText("Remove Item");
            update.setBackgroundColor(Color.RED);
        } else {
            update.setText("Update Item");
            update.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}