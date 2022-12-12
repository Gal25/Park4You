package com.example.park4you.Parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.park4you.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AvailableParking extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_parking);
        TextView textViewCityName = findViewById(R.id.cityName);
        String cityName = getIntent().getExtras().getString("City Name");
        textViewCityName.setText("Available Parking In " + cityName);
        TextView textViewDetails = findViewById(R.id.parkingDetails);

    }

//    public Parking getParkingFromFireBase(QueryDocumentSnapshot doc){
//        String city = doc.getString("city");
//        String street = doc.getString("street");
//        int houseNum = Integer.parseInt(doc.getString("houseNum"));
//        String avHours = doc.getString("avHours");
//        double price = doc.getDouble("price");
//        long phoneNum = Long.parseLong(doc.getString("price"));
//        return new Parking(city, street, houseNum, price, avHours, phoneNum);
//    }
}