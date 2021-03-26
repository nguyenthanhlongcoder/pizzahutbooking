package com.anhlang.pizzahutbooking.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anhlang.pizzahutbooking.Activity.LoginScreen;
import com.anhlang.pizzahutbooking.R;
import com.bumptech.glide.Glide;

public class AccountFragment extends Fragment {
    TextView txt_name;
    TextView txt_email;
    ImageView img_avatar;
    LinearLayout btn_logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        txt_name = view.findViewById(R.id.txt_account_name);
        txt_email = view.findViewById(R.id.txt_email);
        img_avatar = view.findViewById(R.id.img_avatar);
        btn_logout = view.findViewById(R.id.btn_logout);

        txt_name.setText(getActivity().getIntent().getBundleExtra("account").getString("name"));
        txt_email.setText(getActivity().getIntent().getBundleExtra("account").getString("email"));

        Glide.with(getActivity()).load(getActivity().getIntent().getBundleExtra("account").getString("avatar")).into(img_avatar);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;
    }
}
