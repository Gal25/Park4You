package com.example.park4you.Presenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.park4you.Model.ModelOrdersDB;
import com.example.park4you.Model.ModelParkingDB;
import com.example.park4you.Object.Parking;
import com.example.park4you.Object.Time;
import com.example.park4you.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class PresenterNewParking extends PresenterMenu {
    /**
     * This class handles a new parking added by a user.
     */
    private FirebaseAuth auto;
    private EditText EmailText, cityText, streetText, houseNumText, AvStartHoursText, AvEndHoursText, priceText;
    private ModelParkingDB parkingDB;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_user);
        auto = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EmailText = findViewById(R.id.Email);
        cityText = findViewById(R.id.City);
        streetText = findViewById(R.id.Street);
        houseNumText = findViewById(R.id.HouseNumber);
        AvStartHoursText = findViewById(R.id.StartTime);
        AvEndHoursText = findViewById(R.id.EndTime);
        priceText = findViewById(R.id.Price);
        parkingDB = new ModelParkingDB(this);



    }
    /**
     * This will get all the data from the edit texts and save it to a Parking object to the database
     */
    public void addParking(View view) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Addresses");
        String city = cityText.getText().toString();
        String key = reference.child(city).push().getKey();
        String street = streetText.getText().toString();
        int houseNum = Integer.parseInt(houseNumText.getText().toString());
        double price = Double.parseDouble(priceText.getText().toString());
        String start = AvStartHoursText.getText().toString();
        String end = AvEndHoursText.getText().toString();
        Time avHours = new Time(start, end);
        String email = EmailText.getText().toString();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", key);
        hashMap.put("avHours", avHours);
        hashMap.put("city", city);
        hashMap.put("houseNum", houseNum);
        hashMap.put("price", price);
        hashMap.put("street", street);
        hashMap.put("email", email);
        hashMap.put("ownerID", Objects.requireNonNull(auto.getCurrentUser()).getUid());

        parkingDB.newParking(key,city,hashMap);
        Toast.makeText(PresenterNewParking.this, "Data Saved", Toast.LENGTH_SHORT).show();
    }
}
