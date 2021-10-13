package com.example.firedetectionapp.Common.LogInSignup;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firedetectionapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3 extends AppCompatActivity {


    ScrollView scrollView;

    TextInputLayout phoneNumber;
    CountryCodePicker codePicker;
    String nameS,emailS,passwordS,dateS,genderS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);


        scrollView = findViewById(R.id.scroll);
        codePicker = findViewById(R.id.code_picker);
        phoneNumber = findViewById(R.id.phone_verify);

        nameS = getIntent().getStringExtra("name");
        emailS = getIntent().getStringExtra("email");
        passwordS = getIntent().getStringExtra("password");
        genderS = getIntent().getStringExtra("gender");
        dateS = getIntent().getStringExtra("date");
    }

    public void callVerifyScreen(View view) {

        //
        if (!validatePhone()) {
            return;
        }


        String userEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String phoneNo = "+" + codePicker.getFullNumber() + userEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        intent.putExtra("number", phoneNo);
        intent.putExtra("gender", genderS);
        intent.putExtra("date", dateS);
        intent.putExtra("name",nameS);
        intent.putExtra("email",emailS);
        intent.putExtra("password",passwordS);

        //transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_otp_screen");


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3.this, pairs);
        startActivity(intent, options.toBundle());

    }

    public boolean validatePhone() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phoneNumber number");
            return false;
        } /*else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        }*/ else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;

        }
    }
}