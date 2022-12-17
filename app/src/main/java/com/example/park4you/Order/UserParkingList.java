package com.example.park4you.Order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;


import com.example.park4you.R;
import com.example.park4you.User.UserParkingsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class UserParkingList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<Order> list;
    FirebaseUser firebaseUser;
    private FirebaseAuth auto;
    UserParkingsAdapter userParkingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parking_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.userParkingList);
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("Customers Parking");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        userParkingsAdapter = new UserParkingsAdapter(this, list);
        recyclerView.setAdapter(userParkingsAdapter);
        System.out.println("uid:"+auto.getUid());

        database.child(Objects.requireNonNull(auto.getUid())).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    System.out.println(dataSnapshot);
                    Order o = dataSnapshot.getValue(Order.class);
                    assert o != null;
                    String emailOwner = o.getEmailOwner();
                    int houseNum = o.getHouseNum();
                    String city = o.getCity();
                    double price = o.getPrice();
                    String id = o.getId();
                    String emailCustomer = o.getEmailCustomer();
                    String parkingId = o.getParkingId();
                    String avHours = o.getAvHours();
                    String street = o.getStreet();

                    Order order = new Order(emailOwner,houseNum,city,price,id,emailCustomer,parkingId,avHours,street);
                    System.out.println(order);
                    list.add(order);
                }
                userParkingsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}