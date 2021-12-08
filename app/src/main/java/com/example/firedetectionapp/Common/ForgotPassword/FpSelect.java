package com.example.firedetectionapp.Common.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firedetectionapp.Common.Login.VerifyOTP;
import com.example.firedetectionapp.R;

public class FpSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_select);
    }
    public void callOtpScreen(View view){
        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        startActivity(intent);

    }

    /*public void callNewPassScreen(View view){
        Intent intent2 = new Intent(getApplicationContext(),SetNewPassword.class);
        startActivity(intent2);

    }*/
}