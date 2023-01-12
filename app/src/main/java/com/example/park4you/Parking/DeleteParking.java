package com.example.park4you.Parking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteParking extends AppCompatActivity {

    private EditText textViewCity, textViewStreet, textViewHouseNum;

    private DatabaseReference database;
    private String email;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auto;
    @SuppressLint("MissingInflatedId")
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
        database.child(city).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found= false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parking parking = dataSnapshot.getValue(Parking.class);
                    assert parking != null;
                    System.out.println("parking.getEmail()" + parking.getEmail());
                    if (email.equals(parking.getEmail())) {
                        if (parking.getStreet().equals(street) && parking.getHouseNum() == houseNum) {
                            found = true;
                            dataSnapshot.getRef().removeValue();
                            Toast.makeText(DeleteParking.this, "Parking Deleted!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                }

                if(!found){
                    Toast.makeText(DeleteParking.this, "not your Parking!", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
