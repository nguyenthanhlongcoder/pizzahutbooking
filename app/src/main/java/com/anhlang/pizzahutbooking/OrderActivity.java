package com.anhlang.pizzahutbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ru.nikartm.support.ImageBadgeView;

public class OrderActivity extends AppCompatActivity {

    ViewPager viewPager;

    TabLayout tabLayout;

    ViewPagerAdapter viewPagerAdapter;

    ImageBadgeView btn_cart;

    ArrayList<MealsCart> listMealsCart = new ArrayList<>();

    private PizzaFragment pizzaFragment;
    private SpaghettiAndRiceFragment spaghettiAndRiceFragment;
    private DrinkFragment drinkFragment ;
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

        final Intent intent = getIntent();

        SharedPreferences preferences = getSharedPreferences("mealscart", MODE_PRIVATE);

        if (preferences != null) {
            preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key == "name") {
                        listMealsCart.add(new MealsCart(sharedPreferences.getString("name", ""),
                                sharedPreferences.getString("price", ""),
                                sharedPreferences.getString("count", ""),
                                sharedPreferences.getString("note", "")));

                        btn_cart.setBadgeValue(listMealsCart.size());
                    }
                }
            });
        }

        SharedPreferences preferencesRemove = getSharedPreferences("remove", MODE_PRIVATE);

        if (preferencesRemove != null) {
            preferencesRemove.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key == "position") {
                        listMealsCart.remove(sharedPreferences.getInt("position", 0));
                        btn_cart.setBadgeValue(listMealsCart.size());
                    }
                }
            });
        }
        SharedPreferences preferencesUpdate = getSharedPreferences("update", MODE_PRIVATE);

        if (preferencesUpdate != null) {
            preferencesUpdate.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key == "note" || key == "count") {
                        int position = sharedPreferences.getInt("position", 0);
                        listMealsCart.set(position,
                                new MealsCart(listMealsCart.get(position).getName(),
                                        listMealsCart.get(position).getPrice(),
                                        sharedPreferences.getString("count",""),
                                        sharedPreferences.getString("note", "")));
                        btn_cart.setBadgeValue(listMealsCart.size());
                    }
                }
            });
        }

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToMealsCart = new Intent(OrderActivity.this, MealsCartActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("list meals cart", (Serializable)listMealsCart);

                int requestCode = 1;

                intentToMealsCart.putExtra("table", intent.getStringExtra("table name"));
                intentToMealsCart.putExtras(bundle);
                startActivityForResult(intentToMealsCart, requestCode);
            }
        });

        toolbar.setTitle("order for " + intent.getStringExtra("table name"));

        toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pizzaFragment = new PizzaFragment();
        spaghettiAndRiceFragment = new SpaghettiAndRiceFragment();
        drinkFragment = new DrinkFragment();
        appertizersFragment = new AppertizersFragment();

        tabLayout.setupWithViewPager(viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(pizzaFragment, "PIZZA");
        viewPagerAdapter.addFragment(spaghettiAndRiceFragment, "SPAGHETTI & RICE");
        viewPagerAdapter.addFragment(appertizersFragment, "APPERTIZERS");
        viewPagerAdapter.addFragment(drinkFragment, "DRINK");

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 2){
            if (data.getStringExtra("result").equals("OK")){
                listMealsCart.clear();
                btn_cart.setBadgeValue(0);
            }
        }
    }
}