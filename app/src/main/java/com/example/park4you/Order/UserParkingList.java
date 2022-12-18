package com.example.park4you.Order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.park4you.R;
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

//The purpose of this class is to add the orders that the customer ordered into a list
//and show on the screen with the adapter
//the orders list show if the user choose that
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        database.child(Objects.requireNonNull(auto.getUid())).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //take the snapshot and change it to order object
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
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
                userParkingsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserParkingList.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

    }
}