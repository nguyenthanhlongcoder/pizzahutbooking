package com.anhlang.pizzahutbooking;

import android.os.Bundle;
import android.transition.PathMotion;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    GridView gridView;
    int[] arrayNews = {R.drawable.news, R.drawable.news2, R.drawable.news3, R.drawable.news4, R.drawable.news,
                        R.drawable.news2, R.drawable.news3, R.drawable.news4
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gridView = view.findViewById(R.id.grid_view_order);

        HomeAdapter adapter = new HomeAdapter(getActivity(), arrayNews);

        gridView.setAdapter(adapter);


        return view;
    }
}
