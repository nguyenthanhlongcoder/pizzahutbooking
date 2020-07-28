package com.anhlang.pizzahutbooking.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.anhlang.pizzahutbooking.Fragment.AppertizersFragment;
import com.anhlang.pizzahutbooking.Fragment.DrinkFragment;
import com.anhlang.pizzahutbooking.Fragment.PizzaFragment;
import com.anhlang.pizzahutbooking.Fragment.SpaghettiAndRiceFragment;
import com.anhlang.pizzahutbooking.R;
import com.anhlang.pizzahutbooking.Adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import ru.nikartm.support.ImageBadgeView;

public class OrderActivity extends AppCompatActivity {
    ViewPager viewPager;

    DatabaseReference reference;

    TabLayout tabLayout;

    ViewPagerAdapter viewPagerAdapter;

    ImageBadgeView btn_cart;
    private PizzaFragment pizzaFragment;
    private SpaghettiAndRiceFragment spaghettiAndRiceFragment;
    private DrinkFragment drinkFragment;
    private AppertizersFragment appertizersFragment;

    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tabLayout = findViewById(R.id.tabLayout);

        viewPager = findViewById(R.id.viewPager);

        toolbar = findViewById(R.id.toolbar);

        btn_cart = findViewById(R.id.btn_cart);
        final Intent intentFromOrderFragment = getIntent();
        pizzaFragment = new PizzaFragment();
        spaghettiAndRiceFragment = new SpaghettiAndRiceFragment();
        drinkFragment = new DrinkFragment();
        appertizersFragment = new AppertizersFragment();

        tabLayout.setupWithViewPager(viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(pizzaFragment, "PIZZA");
        viewPagerAdapter.addFragment(spaghettiAndRiceFragment, getResources().getString(R.string.spagetti_title));
        viewPagerAdapter.addFragment(appertizersFragment, getResources().getString(R.string.appertizers_title));
        viewPagerAdapter.addFragment(drinkFragment, getResources().getString(R.string.drink_title));
        viewPager.setAdapter(viewPagerAdapter);

        toolbar.setTitle(getResources().getString(R.string.order_for) + " " + intentFromOrderFragment.getStringExtra("table"));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_cart.getBadgeValue() != 0) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OrderActivity.this);
                    LayoutInflater inflater = OrderActivity.this.getLayoutInflater();

                    View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

                    dialogBuilder.setView(dialogView);

                    TextView title = dialogView.findViewById(R.id.txt_dialog_title);
                    TextView content = dialogView.findViewById(R.id.txt_dialog_content);
                    Button positive = dialogView.findViewById(R.id.btn_positive);
                    Button negative = dialogView.findViewById(R.id.btn_negative);

                    positive.setText(getResources().getString(R.string.go_to_cart));
                    title.setText(getResources().getString(R.string.confirm_cart_alter_title));
                    content.setText(getResources().getString(R.string.confirm_cart_alter_content));
                    final AlertDialog alertDialog = dialogBuilder.create();

                    alertDialog.show();
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openCart();
                            alertDialog.dismiss();
                        }
                    });

                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }else {

                    finish();
                    reference = FirebaseDatabase.getInstance().getReference().child("TABLE").child(intentFromOrderFragment.getStringExtra("table")).child("inuse");
                    reference.setValue(false);
                }
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TABLE").child(intentFromOrderFragment.getStringExtra("table")).child("cache");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                btn_cart.setBadgeValue(Integer.parseInt(String.valueOf(snapshot.getChildrenCount())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCart();
            }
        });
    }

    private void openCart(){
        Intent intentToMealsCart = new Intent(OrderActivity.this, MealsCartActivity.class);

        intentToMealsCart.putExtra("table", getIntent().getStringExtra("table"));
        intentToMealsCart.putExtra("account", getIntent().getBundleExtra("account"));
        startActivity(intentToMealsCart);
    }
}