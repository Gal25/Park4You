package com.example.park4you.Parking;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.park4you.Location.Location;
import com.example.park4you.Menu.Menu;
import com.example.park4you.Order.OrdersDB;
import com.example.park4you.Order.PresenterOrderConfirmation;
import com.example.park4you.Payment.PaymentDB;
import com.example.park4you.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PresenterAvailableParking extends Menu {


    private RecyclerView recyclerView;
    private DatabaseReference database;
    private ParkingAdapter myAdapter;
    private ArrayList<Parking> list;
    private TextView textView;
    private String city;
    private OrdersDB ordersDB;
    private PaymentDB paymentDB;

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
        ordersDB = new OrdersDB();
        paymentDB = new PaymentDB();
        list = new ArrayList<>();
        myAdapter = new ParkingAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        Show_Parking();
    }


    public void Show_Parking(){
        String cityName = getIntent().getStringExtra("City Name");
        String streetName = getIntent().getStringExtra("Street Name");
        String AvHours = getIntent().getStringExtra("time");
        database.child(cityName).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parking parking = dataSnapshot.getValue(Parking.class);
                    assert parking != null;
                    if (parking.getStreet().equals(streetName)) {
                        String hours = parking.getAvHours().toString();
                        if (checkTime(AvHours, hours)) {
                            list.add(parking);
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PresenterAvailableParking.this, "show Parking failed", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //Choose and delete the parking from the parking list and update the parking display screen using the adapter
    public void ChooseParking(View view){
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
                        }
                        boolean payment = paymentDB.SearchDetails();
                        if (payment){
                            Intent intent = new Intent(PresenterAvailableParking.this, PresenterOrderConfirmation.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(PresenterAvailableParking.this, "fill in your payment details.",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PresenterAvailableParking.this, PaymentDB.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PresenterAvailableParking.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public boolean checkTime(String hours1, String hours2){
        char[] hou1 = hours1.toCharArray();
        char[] hou2 = hours2.toCharArray();

        if (hou1[0] > hou2[0] || hou1[0] >= hou2[0] && hou1[1] > hou2[1]){
            if (hou1[6] < hou2[6] || hou1[6] <= hou2[6] && hou1[7] < hou2[7]){
                return true;
            }
            else if(hou1[9] < hou2[9] || hou1[9] <= hou2[9] && hou1[10] <= hou2[10]){
                return true;
            }
        }
        else{
            if(hou1[3] > hou2[3] || hou1[3] >= hou2[3] && hou1[4] >= hou2[4]){
                if (hou1[6] < hou2[6] || hou1[6] <= hou2[6] && hou1[7] < hou2[7]){
                    return true;
                }else if (hou1[9] < hou2[9] ||hou1[9] <= hou2[9] && hou1[10] <= hou2[10]){
                    return true;
                }
            }
        }
        return false;
    }
}
