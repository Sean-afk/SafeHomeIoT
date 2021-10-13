package com.example.firedetectionapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    Button on,off;
    FirebaseDatabase database;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        on = findViewById(R.id.on);
        off = findViewById(R.id.off);

        database = FirebaseDatabase.getInstance();


        reference = database.getReference("fire_sensor_status");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int fire = snapshot.getValue(Integer.class);
                if(fire == 0) {
                    notification();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error has occured", Toast.LENGTH_SHORT).show();

            }
        });

        on.setOnClickListener(v -> {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("LED_STATUS");
            reference.setValue(1);
        });

        off.setOnClickListener(v -> {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("LED_STATUS");
            reference.setValue(0);
        });



    }
    private void notification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                    .setContentTitle("Fire Detected")
                    .setSmallIcon(R.drawable.fire)
                    .setAutoCancel(true)
                    .setContentText("A fire has been detected");

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(999,builder.build());

        }
    }



}