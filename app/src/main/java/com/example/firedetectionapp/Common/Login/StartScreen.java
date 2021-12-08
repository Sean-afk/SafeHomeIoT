package com.example.firedetectionapp.Common.Login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firedetectionapp.Common.Signup.SignUp;
import com.example.firedetectionapp.MainActivity;
import com.example.firedetectionapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class StartScreen extends AppCompatActivity {

    Button signUp;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


        //Transparent Action bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        signUp = findViewById(R.id.sign_up);

        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }

    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), LogIn.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.log_in), "transition_login");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartScreen.this, pairs);
        startActivity(intent,options.toBundle());
    }
    public void callSignupScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.sign_up), "transition_signup");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartScreen.this, pairs);
        startActivity(intent,options.toBundle());
    }
}