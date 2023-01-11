package com.example.park4you.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.park4you.LoginUser.Login;
import com.example.park4you.Menu.Drawer;
import com.example.park4you.Menu.Menu;
import com.example.park4you.Order.Order;
//import com.example.park4you.Order.PresenterOwnerOrders;
import com.example.park4you.Parking.ParkingDB;
//import com.example.park4you.Parking.ParkingList;
import com.example.park4you.Parking.PresenterAvailableParking;
import com.example.park4you.Parking.Time;
import com.example.park4you.R;
//import com.example.park4you.RentUser.RentUser;
import com.example.park4you.Order.PresentOrders;
import com.example.park4you.User.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import android.widget.FrameLayout;

public class Location extends Menu {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
    }

    public void proceedButton(View view){
        //This function will get the data of a parking the user wants to search for and will direct him using the intent to the parking.
        Intent intent = new Intent(Location.this, PresenterAvailableParking.class);
        EditText editCityName = findViewById(R.id.city);
        EditText editStreetName = findViewById(R.id.street);
        EditText editHoursStartName = findViewById(R.id.StartHours);
        EditText editHoursEndName = findViewById(R.id.EndHours);
        String cityName = editCityName.getText().toString();
        intent.putExtra("City Name", cityName);

        String streetName = editStreetName.getText().toString();
        intent.putExtra("Street Name", streetName);

        String AvHoursStart = editHoursStartName.getText().toString();
        String AvHoursEnd = editHoursEndName.getText().toString();
        Time time = new Time(AvHoursStart, AvHoursEnd);
        intent.putExtra("time", time.toString());
        startActivity(intent);
    }
}