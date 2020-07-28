package com.anhlang.pizzahutbooking.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.anhlang.pizzahutbooking.Object.Meals;
import com.anhlang.pizzahutbooking.Object.MealsCart;
import com.anhlang.pizzahutbooking.Adapter.MealsCartAdapter;
import com.anhlang.pizzahutbooking.Object.Notification;
import com.anhlang.pizzahutbooking.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MealsCartActivity extends AppCompatActivity {

    ListView ls_meals_cart;
    ArrayList<MealsCart> arrayList = new ArrayList<>();
    TextView addItems;
    TextView subTotal;
    ImageView btn_back;
    Button btn_confirm;
    MealsCartAdapter adapter;
    DatabaseReference reference;
    DatabaseReference referenceGetCacheData;
    DatabaseReference referenceGetData;
    DatabaseReference referenceSetNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_cart);

        ls_meals_cart = findViewById(R.id.ls_order);
        addItems = findViewById(R.id.btn_add_items);
        subTotal = findViewById(R.id.txt_subtotal);
        btn_back = findViewById(R.id.btn_back);
        btn_confirm = findViewById(R.id.btn_confirm_order);
        final String table = getIntent().getStringExtra("table");

        referenceGetCacheData = FirebaseDatabase.getInstance().getReference().child("TABLE").child(table).child("cache");
        referenceGetData = FirebaseDatabase.getInstance().getReference().child("TABLE").child(table).child("meals");

        if(referenceGetCacheData != null){
            referenceGetCacheData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            MealsCart mealsCart = dataSnapshot.getValue(MealsCart.class);
                            arrayList.add(0, mealsCart);
                        }
                        adapter.notifyDataSetChanged();
                        subTotal.setText(adapter.getSubTotal() + " đ");
                    }else{
                        adapter.notifyDataSetChanged();
                        subTotal.setText(adapter.getSubTotal() + " đ");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        
        if(referenceGetData != null){
            referenceGetData.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.exists()) {
                        MealsCart mealsCart = snapshot.getValue(MealsCart.class);
                        arrayList.add(mealsCart);

                        adapter.notifyDataSetChanged();
                        subTotal.setText(adapter.getSubTotal() + " đ");
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        int position = 0;
                        for(int i = 0; i < arrayList.size(); i++){
                            if(arrayList.get(i).getId().equals(snapshot.child("id").getValue(String.class))){
                                position = i;
                            }
                        }

                        arrayList.remove(position);
                        adapter.notifyDataSetChanged();
                        subTotal.setText(adapter.getSubTotal() + " đ");

                        if(arrayList.isEmpty()){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("TABLE").child(table).child("ordered");
                        ref.setValue(false);

                        ref = FirebaseDatabase.getInstance().getReference().child("TABLE").child(table).child("available");
                        ref.setValue(true);
                        }
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        adapter = new MealsCartAdapter(MealsCartActivity.this, R.layout.list_order, arrayList);
        ls_meals_cart.setAdapter(adapter);

        addItems.setOnClickListener(new View.OnClickListener() {
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
                finish();

                reference = FirebaseDatabase.getInstance().getReference("TABLE").child(table);
                referenceSetNotification = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION");

                for(int i = 0; i < arrayList.size(); i++){
                    if(arrayList.get(i).getCache() == true) {
                        arrayList.get(i).setCache(false);
                        reference.child("meals").child(arrayList.get(i).getId()).setValue(arrayList.get(i));

                        String name = getIntent().getBundleExtra("account").getString("name");
                        String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                        String meal = arrayList.get(i).getName() + "x" + arrayList.get(i).getCount();
                        String id = referenceSetNotification.push().getKey();
                        referenceSetNotification.child(id).setValue(new Notification(name, meal, table, currentTime, false, id, "order"));
                    }
                }
                reference.child("ordered").setValue(true);
                reference.child("available").setValue(false);

                referenceGetCacheData.removeValue();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK){
            adapter.notifyDataSetChanged();
        }
    }
}