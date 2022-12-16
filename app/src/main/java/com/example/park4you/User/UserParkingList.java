package com.example.park4you.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.park4you.Location.MyAdapter;
import com.example.park4you.Parking.Parking;
import com.example.park4you.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserParkingList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Parking> list;
    TextView textView;
    FirebaseUser firebaseUser;
    FirebaseAuth auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parking_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        recyclerView = findViewById(R.id.userParkingList);
        database = FirebaseDatabase.getInstance().getReference().child("Customer`s Parking");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        UserParkingsAdapter userParkingsAdapter = new UserParkingsAdapter(this, list);
        recyclerView.setAdapter(userParkingsAdapter);
        database.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    System.out.println(dataSnapshot);
                    Parking parking = dataSnapshot.getValue(Parking.class);
                    list.add(parking);
                }
                userParkingsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}