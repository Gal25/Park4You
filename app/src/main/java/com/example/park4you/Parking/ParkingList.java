package com.example.park4you.Parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.park4you.Location.Location;
import com.example.park4you.LoginUser.Login;
import com.example.park4you.Order.OwnerParkingList;
import com.example.park4you.Order.UserParkingList;
import com.example.park4you.Order.ordersDB;
import com.example.park4you.Order.owners_orders;
import com.example.park4you.R;
import com.example.park4you.RentUser.RentUser;
import com.example.park4you.User.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParkingList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Parking> list;
    TextView textView;
    String city;
    ordersDB ordersDB;
    owners_orders owners_orders;

    //Add the parking into to the parking list and update the parking display screen using the adapter
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
        ordersDB = new ordersDB();
        owners_orders = new owners_orders();
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        String cityName = getIntent().getStringExtra("City Name");
        String streetName = getIntent().getStringExtra("Street Name");
        String AvHours = getIntent().getStringExtra("AvHours");
        database.child(cityName).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parking parking = dataSnapshot.getValue(Parking.class);
                    assert parking != null;
                    if (parking.street.equals(streetName)) {
                        String hours = parking.avHours;
                        if (checkTime(AvHours, hours)) {
                            list.add(parking);
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public boolean checkTime(String hours1, String hours2){
        char[] hou1 = hours1.toCharArray();
        char[] hou2 = hours2.toCharArray();

        if (hou1[0] > hou2[0] && hou1[1] >= hou2[1] || hou1[0] >= hou2[0] && hou1[1] > hou2[1]){
            if (hou1[6] < hou2[6] && hou1[7] <= hou2[7] || hou1[6] <= hou2[6] && hou1[7] < hou2[7]){
                return true;
            }
            else if(hou1[9] <= hou2[9] && hou1[10] <= hou2[10]){
                return true;
            }
        }
        else{
            if(hou1[3] > hou2[3] || hou1[3] >= hou2[3] && hou1[4] >= hou2[4]){
                if (hou1[6] < hou2[6] && hou1[7] <= hou2[7] || hou1[6] <= hou2[6] && hou1[7] < hou2[7]){
                    return true;
                }else{
                    if (hou1[9] < hou2[9]){
                        return true;
                    }else if (hou1[9] <= hou2[9] && hou1[10] <= hou2[10]){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //Choose and delete the parking from the parking list and update the parking display screen using the adapter
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
                            assert park != null;
                            int pos=0;
                            if (park.getid().equals(id)) {
                                ordersDB.create_order_customer(park);
                                ordersDB.create_order_owner(park);
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



    //menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //user profile
            case R.id.item2:
                Intent intent = new Intent(this, UserProfile.class);
                startActivity(intent);
                return true;

            //choose parking
            case R.id.item3:
                Intent intent1 = new Intent(this, Location.class);
                startActivity(intent1);
                return true;

            //add parking
            case R.id.item4:
                Intent intent2 = new Intent(this, RentUser.class);
                startActivity(intent2);
                return true;

            //show the user's orders
            case R.id.item5:
                Intent intent3 = new Intent(this, UserParkingList.class);
                startActivity(intent3);
                return true;

             //Shows the orders ordered from the owner
            case R.id.item6:
                Intent intent4 = new Intent(this, OwnerParkingList.class);
                startActivity(intent4);
                return true;

            //log out
            case R.id.item7:
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(this, Login.class);
                startActivity(intent5);
                return true;

            default: return super.onOptionsItemSelected(item);
        }

    }
}