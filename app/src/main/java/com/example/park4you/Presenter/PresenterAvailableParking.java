package com.example.park4you.Presenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.park4you.Model.ModelOrdersDB;
import com.example.park4you.Model.ModelParkingDB;
import com.example.park4you.Object.Parking;
import com.example.park4you.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PresenterAvailableParking extends PresenterMenu {
    /**
     * This class is responsible of showing available parking as the user chooses.
     */

    private RecyclerView recyclerView;
    private DatabaseReference database;
    private ParkingAdapter myAdapter;
    private ArrayList<Parking> list;
    private TextView textView;
    private String city;
    private  String id;
    private String streetName;
    private ModelOrdersDB ordersDB;
    View v2;
    private ModelParkingDB ParkingDB;
    private String AvHours;
    private boolean payment;
    private final boolean[] receive = new boolean[1];

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
        ordersDB = new ModelOrdersDB();
        ParkingDB = new ModelParkingDB(this);
        list = new ArrayList<>();
        myAdapter = new ParkingAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        Show_Parking();
    }

    /**
     * This will get all the information a user puts for a parking he searches for and will display all the park spots available.
     */
    public void Show_Parking(){
        String cityName = getIntent().getStringExtra("City Name");
        streetName = getIntent().getStringExtra("Street Name");
        AvHours = getIntent().getStringExtra("time");
        database.child(cityName).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) { //in this for loop we find by city street and hour the all the park spots the user matched with his search
                    Parking parking = dataSnapshot.getValue(Parking.class);
                    assert parking != null;
                    if (parking.getStreet().equals(streetName)) {
                        String hours = parking.getAvHours().toString();
                        if (AvHours.length() != 11){
                            Toast.makeText(PresenterAvailableParking.this, "You search a wrong hour please try again", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else if (checkTime(AvHours, hours)) {
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


    /**
     * Choose and delete the parking from the parking list and update the parking display screen using the adapter.
     */
    public void ChooseParking(View view){
        textView = findViewById(R.id.textCity);
        city = textView.getText().toString();
        boolean[] check = {false};
        v2 =recyclerView.findContainingItemView(view);
        assert v2 != null;
        textView = v2.findViewById(R.id.park_id);
        id = textView.getText().toString();

        SearchDetails();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rent Confirmation");
        builder.setMessage("Are you sure you want to rent this parking?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (payment) {
                    DeleteAfterPayment();
                    Intent intent = new Intent(PresenterAvailableParking.this, PresenterOrderConfirmation.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(PresenterAvailableParking.this, "fill in your payment details.",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PresenterAvailableParking.this, PresenterPayment.class);
                    intent.putExtra("City Name", city);
                    intent.putExtra("Street Name", streetName);
                    intent.putExtra("time", AvHours);
                    startActivity(intent);

                }
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * This will add a new parking to owner and customer database after the customer had paid. Then removes the parking from
     * the addresses database.
     */
    public void DeleteAfterPayment(){
        ParkingDB = new ModelParkingDB(this);
        int pos=ParkingDB.UpdateParking(city, id, list);
        list.remove(pos); //remove it from the list after rent
        myAdapter.notifyItemRemoved(pos);
    }

    /**
     * Checks if the customer has payment details
     */
    public void SearchDetails() {
        final Object[] value = new Object[1];
        DatabaseReference reference2;
        reference2 = FirebaseDatabase.getInstance().getReference("PaymentDetails");
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        reference2.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                value[0] = snapshot.getValue();
                if (value[0] != null){
                    receive[0] = true;
                    payment = true;
                    return;
                }else{
                    receive[0] = false;
                    payment = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
        System.out.println("payment " + payment);
        System.out.println("recieve " + receive[0]);
    }

    /**
     * This checks if the start hour and the end hour of the parking the user wishes to rent correspond to the actual time
     * the owner of the parking wishes to rent it
     * @param hours1 - Start time
     * @param hours2 - End time
     * @return
     */

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
