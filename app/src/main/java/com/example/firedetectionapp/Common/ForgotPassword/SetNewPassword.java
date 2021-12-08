package com.example.firedetectionapp.Common.ForgotPassword;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firedetectionapp.Common.Login.StartScreen;
import com.example.firedetectionapp.Database.CheckInternet;
import com.example.firedetectionapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    private TextInputLayout resetPassword,confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        resetPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.new_password_confirm);
    }

    public void setNewPassword(View view) {
        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)) {
            showCustomDialogue();
            return;
        }

        if(!validatePassword() | !validateConfirmPassword()){
            return;
        }

        String newPassword = resetPassword.getEditText().getText().toString().trim();
        String phoneNo = getIntent().getStringExtra("number");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phoneNo).child("password").setValue(newPassword);

        startActivity(new Intent(getApplicationContext(), ResetSuccess.class));
        finish();




    }

    private void showCustomDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
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
                startActivity(new Intent(getApplicationContext(), StartScreen.class));
                finish();

            }
        });
    }

    private boolean validatePassword() {
        String val = resetPassword.getEditText().getText().toString().trim();
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
            resetPassword.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            resetPassword.setError("Password should contain 4 characters!");
            return false;
        } else {
            resetPassword.setError(null);
            resetPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String val = confirmPassword.getEditText().getText().toString().trim();
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
            confirmPassword.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            confirmPassword.setError("Password should contain 4 characters!");
            return false;
        } else {
            confirmPassword.setError(null);
            confirmPassword.setErrorEnabled(false);
            return true;
        }
    }
}