package com.anhlang.pizzahutbooking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.anhlang.pizzahutbooking.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    Button signIn;
    TextInputLayout username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.btn_signin);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    public void loginUser(){
        if (!validateUsername() || !validatePassword()){
            return;
        }else{
            isUser();
        }
    }

    public void isUser() {

        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("USERS");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFormDB = snapshot.child(userEnteredUsername).child("password").getValue().toString();

                    if (passwordFormDB.equals(userEnteredPassword)){
                        Intent intent = new Intent(LoginScreen.this, MainActivity.class);

                        Bundle bundle = new Bundle();

                        bundle.putString("name", snapshot.child(userEnteredUsername).child("name").getValue(String.class));
                        bundle.putString("email", snapshot.child(userEnteredUsername).child("email").getValue(String.class));
                        bundle.putString("phone", snapshot.child(userEnteredUsername).child("phone").getValue(String.class));
                        bundle.putString("avatar", snapshot.child(userEnteredUsername).child("avatar").getValue(String.class));
                        bundle.putString("position", snapshot.child(userEnteredUsername).child("position").getValue(String.class));

                        intent.putExtra("account", bundle);
                        startActivity(intent);
                    }else{
                        password.setError(getResources().getString(R.string.wrong_password));
                        password.requestFocus();
                    }
                }else{
                    username.setError(getResources().getString(R.string.no_user_exist));
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private Boolean validateUsername(){
        String val = username.getEditText().getText().toString();

        if (val.isEmpty()){
            username.setError(getResources().getString(R.string.filed_not_empty));
            return false;
        }else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()){
            password.setError(getResources().getString(R.string.filed_not_empty));
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

}