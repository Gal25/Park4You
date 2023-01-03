package com.example.park4you.Order;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.park4you.Location.Location;
import com.example.park4you.Menu.Menu;
import com.example.park4you.R;
import com.example.park4you.User.UserDB;
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
public class PresentOrders extends Menu {
    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<Order> list;
    FirebaseUser firebaseUser;
    private FirebaseAuth auto;
    UserOrdersAdapter OrdersUsersAdapter;
    OwnerOrdersAdapter OrdersOwnersAdapter;
    String owner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        list = new ArrayList<>();
        owner = (getIntent().getStringExtra("owner"));

        if(owner.equals("user")) {
            setContentView(R.layout.activity_user_parking_list);
            recyclerView = findViewById(R.id.userParkingList);
            database = FirebaseDatabase.getInstance().getReference("Customers Parking");
            OrdersUsersAdapter = new UserOrdersAdapter(this, list);
            OrdersOwnersAdapter = new OwnerOrdersAdapter(this, list);
            recyclerView.setAdapter(OrdersUsersAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ShowOrders();

        }else {
            setContentView(R.layout.activity_user_parking_list);
            recyclerView = findViewById(R.id.userParkingList);
            database = FirebaseDatabase.getInstance().getReference("Owners Parking");
            OrdersOwnersAdapter = new OwnerOrdersAdapter(this, list);
            recyclerView.setAdapter(OrdersOwnersAdapter);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ShowOrders();
        }


    }
    public void ShowOrders(){
        database.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //take the snapshot and change it to order object
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    assert newPost != null;
                    Collection<Object> arr = newPost.values();
                    String email_owner = (String) arr.toArray()[0];
                    int houseNum = Integer.parseInt(arr.toArray()[1].toString());
                    String city = (String) arr.toArray()[2];
                    double price = Double.parseDouble(arr.toArray()[3].toString());
                    String emailCustomer = (String) arr.toArray()[5];
                    String avHours = (String) arr.toArray()[7];
                    String street = (String) arr.toArray()[10];
                    boolean parking_now = Boolean.parseBoolean(arr.toArray()[8].toString());
                    String ownerID = (String) arr.toArray()[9];
                    String key_order = (String) arr.toArray()[4];
                    String parking_id = (String) arr.toArray()[6];
                    Order order = new Order(email_owner, houseNum, city, price, emailCustomer, avHours, street, parking_now, ownerID, key_order, parking_id);
                    list.add(order);
                }
                if (owner.equals("user") && !list.isEmpty()) {
                    OrdersUsersAdapter.notifyDataSetChanged();
                } else if(owner.equals("owner") && !list.isEmpty()){
                    OrdersOwnersAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(PresentOrders.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PresentOrders.this, Location.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PresentOrders.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}