package com.anhlang.pizzahutbooking.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.anhlang.pizzahutbooking.Adapter.MainAdapter;
import com.anhlang.pizzahutbooking.Activity.OrderActivity;
import com.anhlang.pizzahutbooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderFragment extends Fragment {
    DatabaseReference reference;
    GridView gridViewOrder;
    MainAdapter adapter;

    int[] orderButtonImage = {R.drawable.table_a1, R.drawable.table_a2, R.drawable.table_a3,
            R.drawable.table_b1, R.drawable.table_b2, R.drawable.table_b3,
            R.drawable.table_c1, R.drawable.table_c2, R.drawable.table_c3,
            R.drawable.table_d1, R.drawable.table_d2, R.drawable.table_d3};

    int[] orderButtonImageActive = {R.drawable.table_a1_active, R.drawable.table_a2_active, R.drawable.table_a3_active,
                                        R.drawable.table_b1_active, R.drawable.table_b2_active, R.drawable.table_b3_active,
                                        R.drawable.table_c1_active, R.drawable.table_c2_active, R.drawable.table_c3_active,
                                        R.drawable.table_d1_active, R.drawable.table_d2_active, R.drawable.table_d3_active};

    int[] orderButtonImageInUse = {R.drawable.table_a1_inuse, R.drawable.table_a2_inuse, R.drawable.table_a3_inuse,
            R.drawable.table_b1_inuse, R.drawable.table_b2_inuse, R.drawable.table_b3_inuse,
            R.drawable.table_c1_inuse, R.drawable.table_c2_inuse, R.drawable.table_c3_inuse,
            R.drawable.table_d1_inuse, R.drawable.table_d2_inuse, R.drawable.table_d3_inuse};

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
                    if(dataSnapshot.child("inuse").getValue().equals(false)) {
                        if(dataSnapshot.child("available").getValue().equals(true)){
                            orderButton[i] = orderButtonImage[i];
                        }else{
                            orderButton[i] = orderButtonImageActive[i];
                        }
                    } else{
                        orderButton[i] = orderButtonImageInUse[i];
                    }
                    i++;
                }
                adapter = new MainAdapter(getActivity(), orderButton);

                gridViewOrder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final Bundle intentBundle = getActivity().getIntent().getBundleExtra("account");
        gridViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final String tableName = positionName[i];
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TABLE").child(tableName);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (snapshot.child("inuse").getValue().equals(true)) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();

                                View dialogView = inflater.inflate(R.layout.custom_alert_dialog_2, null);

                                dialogBuilder.setView(dialogView);

                                TextView title = dialogView.findViewById(R.id.txt_dialog_title_2);
                                TextView content = dialogView.findViewById(R.id.txt_dialog_content_2);
                                Button positive = dialogView.findViewById(R.id.btn_positive_2);

                                title.setText(getResources().getString(R.string.cant_access_table));
                                content.setText(getResources().getString(R.string.cant_access_table_content));
                                final AlertDialog alertDialog = dialogBuilder.create();

                                alertDialog.show();
                                positive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                            } else {
                                Intent intent = new Intent(getContext(), OrderActivity.class);
                                intent.putExtra("table", tableName);
                                intent.putExtra("account", intentBundle);

                                startActivity(intent);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("TABLE").child(tableName);
                                ref.child("inuse").setValue(true);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return view;
    }
}