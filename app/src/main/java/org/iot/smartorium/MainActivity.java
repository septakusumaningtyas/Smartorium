package org.iot.smartorium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView light, temp, humid, water;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        light = findViewById(R.id.lightResult);
        temp = findViewById(R.id.tempResult);
        humid = findViewById(R.id.humResult);
        water = findViewById(R.id.waterResult);

        reference = FirebaseDatabase.getInstance().getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lightValue = dataSnapshot.child("light").getValue(String.class);
                String tempValue = dataSnapshot.child("temperature").getValue(int.class).toString();
                String humidValue = dataSnapshot.child("humidity").getValue(int.class).toString();
                String waterValue = dataSnapshot.child("water").getValue(String.class);

                if(Integer.parseInt(lightValue) > 899)
                {
                    light.setText("Bright");
                }
                else
                {
                    light.setText("Dim");
                }

                if(Integer.parseInt(waterValue) <= 5)
                {
                    water.setText("To High");
                }
                else if(Integer.parseInt(waterValue) >= 6 && Integer.parseInt(waterValue) <= 9)
                {
                    water.setText("Standard");
                }
                else if (Integer.parseInt(waterValue) >= 10)
                {
                    water.setText("Too Low");
                }
                temp.setText(tempValue + " °C");
                humid.setText(humidValue + " %");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
