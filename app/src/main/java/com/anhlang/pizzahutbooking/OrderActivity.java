package com.anhlang.pizzahutbooking;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class OrderActivity extends AppCompatActivity {

    ViewPager viewPager;

    TabLayout tabLayout;

    ViewPagerAdapter viewPagerAdapter;

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

        Intent intent = getIntent();

        toolbar.setTitle("order for " +intent.getStringExtra("table name"));

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
}