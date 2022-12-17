package com.example.park4you.Order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.park4you.R;
import com.example.park4you.User.OwnerParkingsAdapter;
import com.example.park4you.User.UserParkingsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class OwnerParkingList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<Order> list;
    FirebaseUser firebaseUser;
    private FirebaseAuth auto;
    OwnerParkingsAdapter ownerParkingsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_parking_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.ownerParkingList);
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("Owners Parking");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        ownerParkingsAdapter = new OwnerParkingsAdapter(this, list);
        recyclerView.setAdapter(ownerParkingsAdapter);
        System.out.println("ID: " + Objects.requireNonNull(auto.getUid()));
        database.child(Objects.requireNonNull(auto.getUid())).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("---- 53 ----");
                System.out.println(snapshot);
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    System.out.println("---- 54 ----");
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    System.out.println(newPost.values());
                    assert newPost != null;
                    Collection<Object> arr = newPost.values();
                    String email_owner = (String) arr.toArray()[0];
                    int houseNum = Integer.parseInt(arr.toArray()[1].toString());
                    String city = (String) arr.toArray()[2];
                    double price = Double.parseDouble(arr.toArray()[3].toString());
                    String emailCustomer = (String)arr.toArray()[5];
                    String avHours = (String)arr.toArray()[7];
                    String street = (String)arr.toArray()[8];

                    Order order = new Order(email_owner ,houseNum ,city,price ,emailCustomer ,avHours ,street);
                    System.out.println(order);
                    list.add(order);
                }
                ownerParkingsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}