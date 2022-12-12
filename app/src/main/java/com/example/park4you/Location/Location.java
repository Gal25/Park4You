package com.example.park4you.Location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.park4you.Parking.ParkingList;
import com.example.park4you.R;
import com.example.park4you.RentUser.RentUser;
import com.example.park4you.User.UserProfile;

public class Location extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
    }

    public void rentButton(View view){
        Intent intent = new Intent(Location.this, RentUser.class);
        startActivity(intent);
    }

    public void proceedButton(View view){
        Intent intent = new Intent(Location.this, ParkingList.class);
        EditText editLocationName = findViewById(R.id.location);
        String cityName = editLocationName.getText().toString();
        intent.putExtra("City Name", cityName);
        startActivity(intent);
    }

    public void ProfileUser(View view){
        Intent intent = new Intent(Location.this, UserProfile.class);
        startActivity(intent);
    }
}