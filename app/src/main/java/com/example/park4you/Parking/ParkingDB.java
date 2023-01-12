package com.example.park4you.Parking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.park4you.Menu.Menu;
import com.example.park4you.Order.ModelOrdersDB;
import com.example.park4you.R;
//import com.example.park4you.RentUser.RentUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
//This class saves a new parking entered by a user to the database
public class ParkingDB extends Menu {
    private FirebaseAuth auto;
//    private String city;
    private ModelOrdersDB ordersDB;
    private ParkingAdapter myAdapter;
    private ArrayList<Parking> list;
    private DatabaseReference databaseReference;
    private EditText EmailText, cityText, streetText, houseNumText, AvStartHoursText, AvEndHoursText, priceText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_user);
        auto = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        EmailText = findViewById(R.id.Email);
        cityText = findViewById(R.id.City);
        streetText = findViewById(R.id.Street);
        houseNumText = findViewById(R.id.HouseNumber);
        AvStartHoursText = findViewById(R.id.StartTime);
        AvEndHoursText = findViewById(R.id.EndTime);
        priceText = findViewById(R.id.Price);


    }
    public void addParking(View view){ newParking(); }
    //This will get all the data from the edit texts and save it to a Parking object to the database
    public void newParking() {

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
        System.out.println("hashmap " + hashMap);
        assert key != null;
        reference.child(city).child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ParkingDB.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int DeleteParking(String city, String id, ArrayList<Parking> list){

        ordersDB = new ModelOrdersDB();
        final int[] pos = {0};
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Addresses");
        database.child(city).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parking park = dataSnapshot.getValue(Parking.class);
                    assert park != null;
//                    int pos=0;
                    if (park.getid().equals(id)) {
                        System.out.println("list  "+list);
                        ordersDB.create_order_customer(park); //add the parking to the user's parking database
                        ordersDB.create_order_owner(park); //add the parking to the owner's parking database
//                        check[0] = true;
                        for (int i = 0; i < list.size(); i++){
                            if (list.get(i).equals(park)){
                                pos[0] = i;
                            }
                        }
                        dataSnapshot.getRef().removeValue();

                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ParkingDB.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        return pos[0];
    }


}
