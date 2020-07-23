package com.anhlang.pizzahutbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MealsCartActivity extends AppCompatActivity {

    ListView ls_meals_cart;
    ArrayList<MealsCart> arrayList;
    TextView addTiems;
    TextView subTotal;
    ImageView btn_back;
    Button btn_confirm;
    MealsCartAdapter adapter;
    DatabaseReference reference;
    DatabaseReference referenceGetData;
    private DatabaseReference referenceGetMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_cart);

        Bundle bundle = getIntent().getExtras();

        arrayList = (ArrayList<MealsCart>)bundle.getSerializable("list meals cart");
        ls_meals_cart = findViewById(R.id.ls_order);
        addTiems = findViewById(R.id.btn_add_items);
        subTotal = findViewById(R.id.txt_subtotal);
        btn_back = findViewById(R.id.btn_back);
        btn_confirm = findViewById(R.id.btn_confirm_order);
        adapter = new MealsCartAdapter(MealsCartActivity.this, R.layout.list_order, arrayList);

        int total = 0;

        for(MealsCart mealsCart : arrayList){
            total += Integer.parseInt(mealsCart.getPrice())*Integer.parseInt(mealsCart.getCount());
        }

        subTotal.setText(total + " đ");

        ls_meals_cart.setAdapter(adapter);

        SharedPreferences preferences = getSharedPreferences("remove", MODE_PRIVATE);

        if (preferences != null) {
            preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals("position")) {
                        arrayList.remove(sharedPreferences.getInt("position", 0));
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        referenceGetData = FirebaseDatabase.getInstance().getReference().child("TABLE").child(getIntent().getStringExtra("table"));
        referenceGetMeals = referenceGetData.child("meals");

        referenceGetData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = snapshot.child("status").getValue(String.class);
                if (status.equals("ordered")){
                    referenceGetMeals.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                MealsCart mealsCart = dataSnapshot.getValue(MealsCart.class);
                                arrayList.add(mealsCart);
                            }
                            MealsCartAdapter adapter = new MealsCartAdapter(MealsCartActivity.this, R.layout.list_order, arrayList);

                            ls_meals_cart.setAdapter(adapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SharedPreferences preferencesUpdate = getSharedPreferences("update", MODE_PRIVATE);

        if (preferencesUpdate != null) {
            preferencesUpdate.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals("note")|| key.equals("count")) {
                        int position = sharedPreferences.getInt("position", 0);
                        arrayList.set(position,
                                new MealsCart(arrayList.get(position).getName(),
                                            arrayList.get(position).getPrice(),
                                            sharedPreferences.getString("count",""),
                                            sharedPreferences.getString("note", "")));
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        addTiems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("TABLE").child(getIntent().getStringExtra("table"));

                reference.child("meals").setValue(arrayList);
                reference.child("status").setValue("ordered");

                Intent intent = new Intent();
                intent.putExtra("result", "OK");

                int resultCode = 2;

                setResult(resultCode, intent);
                finish();
            }
        });
    }
}