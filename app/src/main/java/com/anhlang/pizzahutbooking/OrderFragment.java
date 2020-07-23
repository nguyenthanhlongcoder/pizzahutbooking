package com.anhlang.pizzahutbooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class OrderFragment extends Fragment {
    DatabaseReference reference;
    GridView gridViewOrder;

    int[] orderButtonImage = {R.drawable.a1_table, R.drawable.a2_table, R.drawable.a3_table,
            R.drawable.b1_table, R.drawable.b2_table, R.drawable.b3_table,
            R.drawable.c1_table, R.drawable.c2_table, R.drawable.c3_table,
            R.drawable.d1_table, R.drawable.d2_table, R.drawable.d3_table};

    int[] orderButtonImageActive = {R.drawable.a1_table_active, R.drawable.a2_table_active, R.drawable.a3_table_active,
                                        R.drawable.b1_table_active, R.drawable.b2_table_active, R.drawable.b3_table,
                                        R.drawable.c1_table_active, R.drawable.c2_table_active, R.drawable.c3_table_active,
                                        R.drawable.d1_table_active, R.drawable.d2_table_active, R.drawable.d3_table_active};

    int[] orderButton = new int[12];
    String[] positionName = {"A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D1", "D2", "D3"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        gridViewOrder = view.findViewById(R.id.grid_view_order);

        reference = FirebaseDatabase.getInstance().getReference().child("TABLE");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("status").getValue(String.class).equals("available")) {
                        orderButton[i] = orderButtonImage[i];
                    } else {
                        orderButton[i] = orderButtonImageActive[i];
                    }
                    i++;
                }
                MainAdapter adapter = new MainAdapter(getActivity(), orderButton);

                gridViewOrder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        gridViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("table name", positionName[i]);
                startActivity(intent);
            }
        });

        return view;
    }
}
