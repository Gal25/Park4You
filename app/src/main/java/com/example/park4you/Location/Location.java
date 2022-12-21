package com.example.park4you.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.park4you.LoginUser.Login;
import com.example.park4you.Order.Order;
import com.example.park4you.Order.OwnerParkingList;
import com.example.park4you.Parking.MyAdapter;
import com.example.park4you.Parking.Parking;
import com.example.park4you.Parking.ParkingList;
import com.example.park4you.R;
import com.example.park4you.RentUser.RentUser;
import com.example.park4you.Order.UserParkingList;
import com.example.park4you.User.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Location extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
    }


    public void proceedButton(View view){
        Intent intent = new Intent(Location.this, ParkingList.class);
        EditText editCityName = findViewById(R.id.city);
        EditText editStreetName = findViewById(R.id.street);
        EditText editHoursName = findViewById(R.id.hours);
        String cityName = editCityName.getText().toString();
        intent.putExtra("City Name", cityName);

        String streetName = editStreetName.getText().toString();
        intent.putExtra("Street Name", streetName);

        String AvHours = editHoursName.getText().toString();
        intent.putExtra("AvHours", AvHours);
        startActivity(intent);
    }



    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item2:
                Intent intent = new Intent(Location.this, UserProfile.class);
                startActivity(intent);
                return true;
            case R.id.item3:
                Intent intent1 = new Intent(this, Location.class);
                startActivity(intent1);
                return true;
            case R.id.item4:
                Intent intent2 = new Intent(this, RentUser.class);
                startActivity(intent2);
                return true;
            case R.id.item5:
                Intent intent3 = new Intent(this, UserParkingList.class);
                startActivity(intent3);
                return true;
            case R.id.item6:
                Intent intent4 = new Intent(this, OwnerParkingList.class);
                startActivity(intent4);
                return true;
            case R.id.item7:
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(this, Login.class);
                startActivity(intent5);
                return true;
            case R.id.item8:
                release_parking();
//                Intent intent6 = new Intent(this, Login.class);
//                startActivity(intent6);
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
    public void release_parking(){
        DatabaseReference database_user;
        FirebaseUser firebaseUser;
        FirebaseAuth auto;
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        assert firebaseUser != null;
        System.out.println("****************************************************************************************************");
        System.out.println("firebaseUser.getUid() " + firebaseUser.getUid());
        System.out.println("****************************************************************************************************");
        DatabaseReference reference_cus = FirebaseDatabase.getInstance().getReference("Customers Parking").child(firebaseUser.getUid());
        reference_cus.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    System.out.println("newPost.values() " + newPost.values());
                    assert newPost != null;
                    Collection<Object> arr = newPost.values();
                    String email_owner = (String) arr.toArray()[7];
                    int houseNum = Integer.parseInt(arr.toArray()[8].toString());
                    String city = (String) arr.toArray()[0];
                    double price = Double.parseDouble(arr.toArray()[1].toString());
                    String emailCustomer = (String)arr.toArray()[5];
                    String avHours = (String)arr.toArray()[3];
                    String street = (String)arr.toArray()[2];
                    boolean parking_now = Boolean.parseBoolean(arr.toArray()[4].toString());
                    String ownerID = (String)arr.toArray()[6];
                    Order order = new Order(email_owner ,houseNum ,city,price ,emailCustomer ,avHours ,street, parking_now, ownerID);
                    assert order != null;
                    System.out.println("order.isParking_now() " + order.isParking_now());
                    if (order.isParking_now()) {
                        dataSnapshot.getRef().child("parking_now").setValue(false);
                        System.out.println("dataSnapshot " + dataSnapshot);
//                        Parking p = new Parking(order.city, order.price, order.street, order.avHours, order.parkingId, order.emailOwner, order.houseNum, order.ownerID);
                        DatabaseReference reference_address = FirebaseDatabase.getInstance().getReference("Addresses");
                        String key = reference_address.child(order.city).push().getKey();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", key);
                        hashMap.put("avHours", order.avHours);
                        hashMap.put("city", order.city);
                        hashMap.put("houseNum", order.houseNum);
                        hashMap.put("price", order.price);
                        hashMap.put("street", order.street);
                        hashMap.put("email", order.emailOwner);
                        hashMap.put("ownerID", order.ownerID);
                        assert key != null;
                        reference_address.child(order.city).child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(RentUser.this, "Data Saved", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}