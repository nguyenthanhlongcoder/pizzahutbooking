package com.anhlang.pizzahutbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class OrderActivity extends AppCompatActivity {

    ViewPager viewPager;

    TabLayout tabLayout;

    ViewPagerAdapter viewPagerAdapter;

    private PizzaFragment pizzaFragment;
    private SpaghettiAndRiceFragment spaghettiAndRiceFragment;
    private DrinkFragment drinkFragment ;
    private AppertizersFragment appertizersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tabLayout = findViewById(R.id.tabLayout);

        viewPager = findViewById(R.id.viewPager);

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