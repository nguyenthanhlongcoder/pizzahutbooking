package com.anhlang.pizzahutbooking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anhlang.pizzahutbooking.Adapter.HomeAdapter;
import com.anhlang.pizzahutbooking.R;

public class HomeFragment extends Fragment {
    GridView gridView;
    int[] arrayNews = {R.drawable.news, R.drawable.news, R.drawable.news, R.drawable.news, R.drawable.news,
                        R.drawable.news, R.drawable.news, R.drawable.news, R.drawable.news, R.drawable.news
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
