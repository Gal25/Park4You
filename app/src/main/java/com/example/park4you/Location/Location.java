package com.example.park4you.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.park4you.Parking.ParkingList;
import com.example.park4you.R;
import com.example.park4you.RentUser.RentUser;
import com.example.park4you.Order.UserParkingList;
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


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item2:
                Intent intent = new Intent(Location.this, UserProfile.class);
                startActivity(intent);
                return true;
            case R.id.item3:
                Intent intent1 = new Intent(this, Location.class);
                startActivity(intent1);
                return true;
            case R.id.item4:
                Intent intent2 = new Intent(this, RentUser.class);
                startActivity(intent2);
                return true;
            case R.id.item5:
                Intent intent3 = new Intent(this, UserParkingList.class);
                startActivity(intent3);
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
}