package com.example.park4you.Parking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.park4you.Location.MyAdapter;
import com.example.park4you.R;
import com.example.park4you.RentUser.RentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParkingList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Parking> list;
    TextView textView;
    Button rentButt;
    String name;
    String city;
    int pos = 0;
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
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
//        TextView textV = findViewById(R.id.id);
        String cityName = getIntent().getStringExtra("City Name");
        String email = getIntent().getStringExtra("Email");
//        String Key = textV.getText().toString();
        database.child(cityName).addValueEventListener(new ValueEventListener() {
            //            @SuppressLint("NotifyDataSetChanged")
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    if (dataSnapshot.exists()) {
                    Parking parking = dataSnapshot.getValue(Parking.class);
                    list.add(parking);
//                    }
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
        TextView textID = findViewById(R.id.id);
        String id = textID.getText().toString();
        System.out.println("id " + id);
//        String avHours = findViewById(R.id.textAvailableHours).toString();
//        int houseNum = Integer.parseInt(findViewById(R.id.textHouseNum).toString());
//        int houseNum = Integer.parseInt(findViewById(R.id.textHouseNum).toString());
//        String phoneNum = findViewById(R.id.textPhoneNum).toString();
//        int price = Integer.parseInt(findViewById(R.id.textPrice).toString());
//        Parking p = new Parking(city, street, houseNum, price, avHours, phoneNum);
//        list.setOnItemLongClickListener()
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rent Confirmation");
        builder.setMessage("Are you sure you want to rent this parking?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                database.child(city).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            System.out.println(dataSnapshot.getKey());
                            Parking park = dataSnapshot.getValue(Parking.class);
                            assert park != null;
                            if (park.getid().equals(id)) {
                                pos = list.indexOf(park);
                                dataSnapshot.getRef().removeValue();
                                list.remove(pos);
                                myAdapter.notifyItemRemoved(pos);
                                }
                            }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}