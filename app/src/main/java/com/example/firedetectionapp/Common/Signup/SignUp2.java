package com.example.firedetectionapp.Common.Signup;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firedetectionapp.R;

import java.util.Calendar;

public class SignUp2 extends AppCompatActivity {
    ImageView backBtn;
    Button next, login;
    TextView titleText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DatePicker datePicker;
    String nameS,emailS,passwordS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        backBtn = findViewById(R.id.signUp_back);
        next = findViewById(R.id.sign_up_next2);
        login = findViewById(R.id.sign_up_logIn);
        titleText = findViewById(R.id.sign_up_title);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

        nameS = getIntent().getStringExtra("name");
        emailS = getIntent().getStringExtra("email");
        passwordS = getIntent().getStringExtra("password");


    }

    public void callNextScreen(View view) {

        if (!validateGender() | !validateAge()) {
            return;
        }

        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String _gender = radioButton.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day + "/" + month + "/" + year;


        Intent intent = new Intent(getApplicationContext(), SignUp3.class);
        intent.putExtra("gender", _gender);
        intent.putExtra("date", date);
        intent.putExtra("name",nameS);
        intent.putExtra("email",emailS);
        intent.putExtra("password",passwordS);


        //transition
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_btn");
        pairs[1] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[2] = new Pair<View, String>(next, "transition_next_btn");
        pairs[3] = new Pair<View, String>(login, "transition_login_btn");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2.this, pairs);
        startActivity(intent, options.toBundle());

    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;

        } else
            return true;


    }
}