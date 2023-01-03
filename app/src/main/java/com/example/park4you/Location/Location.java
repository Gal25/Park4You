package com.example.park4you.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.park4you.LoginUser.Login;
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

public class Location extends Menu {

//    private Menu menu;
//    private android.view.Menu menu_android;
//    private MenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
//        menu = new Menu();


//        menu.onCreateOptionsMenu(menu_android);
//        menu.onOptionsItemSelected(item);
    }

    //create menu
//    @Override
//    public boolean onCreateOptionsMenu(android.view.Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
////        this.menu.onOptionsItemSelected(item);
//        return true;
//    }
    public void proceedButton(View view){
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

//        intent.putExtra("AvHoursStart", AvHoursStart);
//        intent.putExtra("AvHoursEnd", AvHoursEnd);
        startActivity(intent);
    }
}