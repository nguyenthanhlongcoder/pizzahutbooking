package com.anhlang.pizzahutbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class OrderFragment extends Fragment {

    GridView gridViewOrder;

    int[] orderButtonImage = {R.drawable.a1_table, R.drawable.a2_table, R.drawable.a3_table,
            R.drawable.b1_table, R.drawable.b2_table, R.drawable.b3_table,
            R.drawable.c1_table, R.drawable.c2_table, R.drawable.c3_table,
            R.drawable.d1_table, R.drawable.d2_table, R.drawable.d3_table};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        gridViewOrder = view.findViewById(R.id.grid_view_order);

        MainAdapter adapter = new MainAdapter(getActivity(), orderButtonImage);

        gridViewOrder.setAdapter(adapter);

        gridViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), OrderActivity.class);

                startActivity(intent);
            }
        });

        return view;

    }
}
