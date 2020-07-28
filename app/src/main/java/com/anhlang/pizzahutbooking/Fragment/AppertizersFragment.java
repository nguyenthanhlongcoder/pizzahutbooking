package com.anhlang.pizzahutbooking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anhlang.pizzahutbooking.Object.Meals;
import com.anhlang.pizzahutbooking.Adapter.MealsAdapter;
import com.anhlang.pizzahutbooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppertizersFragment extends Fragment {

    DatabaseReference reference;

    ArrayList<Meals> mealsArrayList;

    MealsAdapter mealsAdapter;

    ListView ls_appertizers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appertizers, container, false);

        ls_appertizers = view.findViewById(R.id.ls_appertizers);

        mealsArrayList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("MEALS").child("APPERTIZERS");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Meals meal = dataSnapshot.getValue(Meals.class);

                    mealsArrayList.add(meal);
                }

                mealsAdapter = new MealsAdapter(
                        getActivity(),
                        R.layout.meals_listview,
                        mealsArrayList
                );

                ls_appertizers.setAdapter(mealsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ls_appertizers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mealsAdapter.viewMeal(mealsArrayList.get(position).getName(),
                        mealsArrayList.get(position).getPrice(),
                        mealsArrayList.get(position).getDescription(),
                        mealsArrayList.get(position).getPicture(),
                        getActivity().getIntent().getStringExtra("table")
                );
            }
        });
        return  view;
    }
}

