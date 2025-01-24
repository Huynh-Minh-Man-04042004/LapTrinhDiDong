package com.example.baitap01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
public class Login_OTP extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);
    }
}
