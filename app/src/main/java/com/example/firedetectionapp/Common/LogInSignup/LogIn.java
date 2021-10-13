package com.example.firedetectionapp.Common.LogInSignup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firedetectionapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class LogIn extends AppCompatActivity {

    CountryCodePicker codePicker;
    TextInputLayout phoneNo, password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        codePicker = findViewById(R.id.login_code_picker);
        phoneNo = findViewById(R.id.login_phone_verify);
        password = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.login_progress);

    }

    public void callResetScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }

    public void loginUser(View view) {

        if (!isConnected(this)) {
            showCustomDialogue();
        }


        if (!validateFields()) {
            return;

        }

        progressBar.setVisibility(view.VISIBLE);

        String number = phoneNo.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();


        String completePhoneNo = "+" + codePicker.getFullNumber() + number;

        //Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNumber").equalTo(completePhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNo.setError(null);
                    phoneNo.setErrorEnabled(false);

                    String systemPassword = snapshot.child(completePhoneNo).child("password").getValue(String.class);
                    if (systemPassword.equals(pass)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String name = snapshot.child(completePhoneNo).child("fullName").getValue(String.class);
                        String email = snapshot.child(completePhoneNo).child("email").getValue(String.class);
                        String number = snapshot.child(completePhoneNo).child("phoneNumber").getValue(String.class);
                        String dateBirth = snapshot.child(completePhoneNo).child("dob").getValue(String.class);

                        Toast.makeText(LogIn.this, "Welcome" + name, Toast.LENGTH_SHORT).show();

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LogIn.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LogIn.this, "No such user exists", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LogIn.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void showCustomDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
        builder.setMessage("Please connect to the internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),StartScreen.class));
                finish();

            }
        });
    }

    private boolean isConnected(LogIn logIn) {
        ConnectivityManager connectivityManager = (ConnectivityManager) logIn.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiCon != null && wifiCon.isConnected()) || (dataCon != null && dataCon.isConnected())){
            return true;

        }else {
            return false;
        }


    }



    private boolean validateFields() {
        String number = phoneNo.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        if (number.isEmpty()) {
            phoneNo.setError("Phone number cannot be empty");
            phoneNo.requestFocus();
            return false;
        } else if (pass.isEmpty()) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        } else {
            return true;
        }


    }

}