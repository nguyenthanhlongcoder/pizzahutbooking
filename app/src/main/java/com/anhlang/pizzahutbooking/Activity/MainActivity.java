package com.anhlang.pizzahutbooking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.anhlang.pizzahutbooking.Fragment.AccountFragment;
import com.anhlang.pizzahutbooking.Fragment.HomeFragment;
import com.anhlang.pizzahutbooking.Fragment.NotificationFragment;
import com.anhlang.pizzahutbooking.Fragment.OrderFragment;
import com.anhlang.pizzahutbooking.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        final BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.nav_notification);
        badgeDrawable.setVisible(true);

        reference = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int count = 0;
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if (dataSnapshot.child("read").getValue(Boolean.class).equals(false)){
                            count++;
                        }
                    }
                    badgeDrawable.setNumber(count);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_order:
                    selectedFragment = new OrderFragment();
                    break;
                case R.id.nav_notification:
                    selectedFragment = new NotificationFragment();
                    break;
                case R.id.nav_account:
                    selectedFragment = new AccountFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }

    };

}
