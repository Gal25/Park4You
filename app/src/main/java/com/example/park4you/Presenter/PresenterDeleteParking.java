package com.example.park4you.Presenter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.Model.ModelParkingDB;
import com.example.park4you.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PresenterDeleteParking extends AppCompatActivity {

    private EditText textViewCity, textViewStreet, textViewHouseNum;

    private DatabaseReference database;
    private String email;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auto;
    private ModelParkingDB parkingDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_parking);
        textViewCity = findViewById(R.id.CityName);
        textViewStreet = findViewById(R.id.StreetName);
        textViewHouseNum = findViewById(R.id.HouseNumber_);
        database = FirebaseDatabase.getInstance().getReference("Addresses");
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        parkingDB = new ModelParkingDB(this);

    }
    //This functon will delete a published parking by the owner from the database in case the owner does not want to rent it to others anymore
    public void delete_parking(View view){
        String city, street;
        int houseNum;
        city = textViewCity.getText().toString();
        street = textViewStreet.getText().toString();
        houseNum = Integer.parseInt(textViewHouseNum.getText().toString());
        email = firebaseUser.getEmail();
        System.out.println("email"+email);

        parkingDB.DeleteParking(city,email,street,houseNum);
        Toast.makeText(PresenterDeleteParking.this, "Parking Deleted!", Toast.LENGTH_SHORT).show();
    }
}
