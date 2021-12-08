package com.example.firedetectionapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.firedetectionapp.Common.Login.StartScreen;
import com.example.firedetectionapp.Database.SessionManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference reference;
    TextView textView, statusText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.main_textView);
        statusText = findViewById(R.id.status_view);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();

        String fullName = userDetails.get(SessionManager.KEY_FULLNAME);

        textView.setText("Welcome\n" + fullName);

        database = FirebaseDatabase.getInstance();


        reference = database.getReference("fire_sensor_status");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int fire = snapshot.getValue(Integer.class);
                if (fire == 0) {
                    notification();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error has occured", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                    .setContentTitle("Fire Detected")
                    .setSmallIcon(R.drawable.fire)
                    .setAutoCancel(true)
                    .setContentText("A fire has been detected");

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(999, builder.build());

        }
    }

    public void showStatus(View view){
        reference = database.getReference("fire_sensor_status");
        reference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int status = dataSnapshot.getValue(Integer.class);
                if(status == 1){
                    statusText.setText(R.string.status_off);
                }else if(status == 0){
                    statusText.setText(R.string.status_on);
                }
            }
        });



    }

    public void logOutUser(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), StartScreen.class));
        finish();
    }


}