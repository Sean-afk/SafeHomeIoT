package com.example.firedetectionapp.Common.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firedetectionapp.Common.Login.LogIn;
import com.example.firedetectionapp.MainActivity;
import com.example.firedetectionapp.R;

public class ResetSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_success);
    }

    public void goToLogin(View view){
        Intent intent = new Intent(ResetSuccess.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}