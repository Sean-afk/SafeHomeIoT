package com.example.firedetectionapp.Common.LogInSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firedetectionapp.R;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    public void callOptionScreen(View view){
        Intent intent = new Intent(getApplicationContext(), FpSelect.class);
        startActivity(intent);

    }
}