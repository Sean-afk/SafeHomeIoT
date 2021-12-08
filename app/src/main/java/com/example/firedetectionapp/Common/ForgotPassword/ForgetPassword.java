package com.example.firedetectionapp.Common.ForgotPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firedetectionapp.Common.Login.VerifyOTP;
import com.example.firedetectionapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {


    private TextInputLayout fpPhoneNumber;
    private CountryCodePicker fpCodePicker;
    private Button fpNext;
    //private ImageView screenIcon;
    //private TextView fpName,fpDes;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        fpPhoneNumber = findViewById(R.id.fp_phone_verify);
        fpCodePicker = findViewById(R.id.fp_code_picker);
        fpNext = findViewById(R.id.fp_next);
        //screenIcon = findViewById(R.id.fp_icon);
        //fpName = findViewById(R.id.fp_name);
        //fpDes = findViewById(R.id.fp_des);

        //Animation hook
        //animation = AnimationUtils.loadAnimation(this,R.anim.slide)
        //screenIcon.setAnimation();

    }

    public void callFpVerifyScreen(View view) {

        if (!validateFields()) {
            return;

        }

        String number = fpPhoneNumber.getEditText().getText().toString().trim();
        final String completePhoneNo = "+" + fpCodePicker.getFullNumber() + number;

        //Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNumber").equalTo(completePhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    fpPhoneNumber.setError(null);
                    fpPhoneNumber.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("number",completePhoneNo);
                    intent.putExtra("whatToDo","updateData");
                    startActivity(intent);
                    finish();


                } else {
                    fpPhoneNumber.setError("No such user exists");
                    fpPhoneNumber.requestFocus();
                }



                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    private boolean validateFields() {
        String number = fpPhoneNumber.getEditText().getText().toString().trim();

        if (number.isEmpty()) {
            fpPhoneNumber.setError("Phone number cannot be empty");
            fpPhoneNumber.requestFocus();
            return false;
        } else {
            return true;
        }


    }
}