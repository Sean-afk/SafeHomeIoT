package com.example.firedetectionapp.Common.Signup;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firedetectionapp.Common.Login.LogIn;
import com.example.firedetectionapp.Common.Login.StartScreen;
import com.example.firedetectionapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView backBtn;
    Button next, login;
    TextView titleText;

    TextInputLayout username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //hooks for animation
        backBtn = findViewById(R.id.signUp_back);
        next = findViewById(R.id.sign_up_next);
        login = findViewById(R.id.sign_up_logIn);
        titleText = findViewById(R.id.sign_up_title);

        //hooks for getting data
        username = findViewById(R.id.username);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);


    }

    public void callNextScreen(View view) {

        if(!validateUserName() | !validateEmail() | !validatePassword()){
            return;

        }

        String nameS = username.getEditText().getText().toString().trim();
        String emailS = email.getEditText().getText().toString().trim();
        String passwordS = password.getEditText().getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), SignUp2.class);
        intent.putExtra("name",nameS);
        intent.putExtra("email",emailS);
        intent.putExtra("password",passwordS);

        //transition animations
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_btn");
        pairs[1] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[2] = new Pair<View, String>(next, "transition_next_btn");
        pairs[3] = new Pair<View, String>(login, "transition_login_btn");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent,options.toBundle());


    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), LogIn.class);
        startActivity(intent);
        finish();
    }

    private boolean validateUserName() {
        String val = username.getEditText().getText().toString().trim();
        //String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;

        } else if (val.length() > 20) {
            username.setError("Name too large");
            return false;

        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;

        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;

        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid email");
            return false;

        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;

        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }


    }
}