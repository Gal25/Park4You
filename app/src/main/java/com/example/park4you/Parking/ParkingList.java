package com.example.park4you.Parking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.park4you.Location.Location;
import com.example.park4you.Order.customers_orders;
import com.example.park4you.Order.owners_orders;
import com.example.park4you.Location.MyAdapter;
import com.example.park4you.R;
import com.example.park4you.RentUser.RentUser;
import com.example.park4you.User.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ParkingList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Parking> list;
    TextView textView;
    String city;
    customers_orders customers_orders;
    owners_orders owners_orders;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.parkingList);
        database = FirebaseDatabase.getInstance().getReference().child("Addresses");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customers_orders = new customers_orders();
        owners_orders = new owners_orders();
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        String cityName = getIntent().getStringExtra("City Name");
        database.child(cityName).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parking parking = dataSnapshot.getValue(Parking.class);
                    list.add(parking);
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void rentButton(View view){
        textView = findViewById(R.id.textCity);
        city = textView.getText().toString();
        View v2 =recyclerView.findContainingItemView(view);
        assert v2 != null;
        textView = v2.findViewById(R.id.park_id);
        String id = textView.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rent Confirmation");
        builder.setMessage("Are you sure you want to rent this parking?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                database.child(city).addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Parking park = dataSnapshot.getValue(Parking.class);
                            int pos=0;
                            assert park != null;
                            if (park.getid().equals(id)) {
                                customers_orders.create_order(park);
//                                owners_orders.create_order(park);
                                for (int i = 0; i < list.size(); i++){
                                    if (list.get(i).equals(park)){
                                        pos = i;
                                    }
                                }
                                dataSnapshot.getRef().removeValue();
                                list.remove(pos);
                                myAdapter.notifyItemRemoved(pos);
                                break;
                            }
                            Intent intent = new Intent(ParkingList.this, Location.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ParkingList.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}