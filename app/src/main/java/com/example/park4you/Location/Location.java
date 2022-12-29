package com.example.park4you.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.park4you.LoginUser.Login;
import com.example.park4you.Order.Order;
import com.example.park4you.Order.PresenterOwnerOrders;
import com.example.park4you.Parking.ParkingDB;
import com.example.park4you.Parking.ParkingList;
import com.example.park4you.Parking.PresenterAvailableParking;
import com.example.park4you.R;
import com.example.park4you.RentUser.RentUser;
import com.example.park4you.Order.PresentOrders;
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

public class Location extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
    }


    public void proceedButton(View view){
        Intent intent = new Intent(Location.this, PresenterAvailableParking.class);
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
        String owner;
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
                Intent intent2 = new Intent(this, ParkingDB.class);
                startActivity(intent2);
                return true;
            case R.id.item5:
                Intent intent3 = new Intent(this, PresentOrders.class);
                owner = "user";
                intent3.putExtra("owner", owner);
                startActivity(intent3);

                return true;
            case R.id.item6:
                Intent intent4 = new Intent(this, PresentOrders.class);
                owner = "owner";
                intent4.putExtra("owner", owner);
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
        FirebaseUser firebaseUser;
        FirebaseAuth auto;
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        assert firebaseUser != null;
        boolean[] first_time = {true};
        System.out.println("****************************************************************************************************");
        System.out.println("firebaseUser.getUid() " + firebaseUser.getUid());
        System.out.println("****************************************************************************************************");
        DatabaseReference owner = FirebaseDatabase.getInstance().getReference("Owners Parking");;
        DatabaseReference reference_cus = FirebaseDatabase.getInstance().getReference("Customers Parking").child(firebaseUser.getUid());
        reference_cus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(first_time[0]) {
//                        Order order = dataSnapshot.getValue(Order.class);
                        Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                        assert newPost != null;
                        System.out.println("newPost.values() " + newPost.values());
                        System.out.println("snapshot.getChildrenCount() " + snapshot.getChildrenCount());
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


                        HashMap<String, Object> order = new HashMap<>();
                        order.put("emailOwner ", email_owner);
                        order.put("houseNum ", houseNum);
                        order.put("city ", city);
                        order.put("price ", price);
                        order.put("emailCustomer ", emailCustomer);
                        order.put("avHours ", avHours);
                        order.put("street ", street);
                        order.put("parking_now ", false);
                        order.put("OwnerId ", ownerID);
                        order.put("id ", key_order);
                        order.put("parkingId ", parking_id);
//                        Order order = new Order(email_owner, houseNum, city, price, emailCustomer, avHours, street, parking_now, ownerID, key_order, parking_id);
//                        System.out.println("order.isParking_now() " + order.isParking_now());
//                        if (order.isParking_now()) {
                        if (parking_now) {
                            first_time[0] = false;
//                            order.setParking_now(false);
//                            System.out.println("dataSnapshot.getRef() " + dataSnapshot.child("parking_now").getRef().get());
//                            System.out.println("dataSnapshot.getRef() " + dataSnapshot.child("parking_now").getRef().getKey());
//                            System.out.println("dataSnapshot.getRef() " + dataSnapshot.child("parking_now").getValue());
//                            dataSnapshot.child("parking_now").getRef().setValue(false);

                            reference_cus.child(key_order).setValue(order);
                            owner.child(ownerID).child(key_order).setValue(order);

//                            reference_cus.child(order.getId()).child("parking_now").setValue(false);
//                            owner.child(order.getOwnerID()).child(order.getId()).child("parking_now").setValue(false);
                            System.out.println("1");
                            System.out.println("dataSnapshot " + dataSnapshot);
                            DatabaseReference reference_address = FirebaseDatabase.getInstance().getReference("Addresses");
                            String key = reference_address.child(city).push().getKey();
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", key);
                            hashMap.put("avHours", avHours);
                            hashMap.put("city", city);
                            hashMap.put("houseNum", houseNum);
                            hashMap.put("price", price);
                            hashMap.put("street", street);
                            hashMap.put("email", email_owner);
                            hashMap.put("ownerID", ownerID);
                            assert key != null;
                            reference_address.child(city).child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}