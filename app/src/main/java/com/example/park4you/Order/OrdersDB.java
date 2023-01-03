package com.example.park4you.Order;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.Location.Location;
import com.example.park4you.Parking.Parking;
import com.example.park4you.Parking.PresenterAvailableParking;
import com.example.park4you.Parking.Time;
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

public class OrdersDB extends AppCompatActivity {

    DatabaseReference database_user;
    FirebaseUser firebaseUser;
    FirebaseAuth auto;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String release = getIntent().getStringExtra("func");
        if (release.equals("release_parking")){
            release_parking();
        }
    }



    //create a data document in database that represents the orders of the users
    public void create_order_customer(Parking p) {
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference_cus = FirebaseDatabase.getInstance().getReference("Customers Parking").child(firebaseUser.getUid());
        database_user = FirebaseDatabase.getInstance().getReference("Users");
        key = reference_cus.push().getKey();
        HashMap<String, Object> hashMap_customer = new HashMap<>();
        hashMap_customer.put("emailOwner ", p.getEmail());
        hashMap_customer.put("houseNum ", p.getHouseNum());
        hashMap_customer.put("city ", p.getCity());
        hashMap_customer.put("price ", p.getPrice());
        hashMap_customer.put("emailCustomer ", firebaseUser.getEmail());
        hashMap_customer.put("avHours ", p.getAvHours());
        hashMap_customer.put("street ", p.getStreet());
        hashMap_customer.put("parking_now ", true);
        hashMap_customer.put("OwnerId ", p.getOwnerID());
        hashMap_customer.put("id ", key);
        hashMap_customer.put("parkingId ", p.getid());

        assert key != null;
        reference_cus.child(key).setValue(hashMap_customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    //create a data document in database that represents the orders of the owners
    public void create_order_owner(Parking p){
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        database_user = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference reference_owner= FirebaseDatabase.getInstance().getReference("Owners Parking").child(p.getOwnerID());
        HashMap<String, Object> hashMap_customer = new HashMap<>();
        hashMap_customer.put("emailOwner ", p.getEmail());
        hashMap_customer.put("houseNum ", p.getHouseNum());
        hashMap_customer.put("city ", p.getCity());
        hashMap_customer.put("price ", p.getPrice());
        hashMap_customer.put("emailCustomer ", firebaseUser.getEmail());
        hashMap_customer.put("avHours ", p.getAvHours());
        hashMap_customer.put("street ", p.getStreet());
        hashMap_customer.put("parking_now ", true);
        hashMap_customer.put("OwnerId ", p.getOwnerID());
        hashMap_customer.put("id ", key);
        hashMap_customer.put("parkingId ", p.getid());

        assert key != null;
        reference_owner.child(key).setValue(hashMap_customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }
    public void release_parking(){
        FirebaseUser firebaseUser;
        FirebaseAuth auto;
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        assert firebaseUser != null;
        boolean[] first_time = {true};
        DatabaseReference owner = FirebaseDatabase.getInstance().getReference("Owners Parking");
        DatabaseReference reference_cus = FirebaseDatabase.getInstance().getReference("Customers Parking").child(firebaseUser.getUid());
        reference_cus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(first_time[0]) {
//                        Order order = dataSnapshot.getValue(Order.class);
                        Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                        assert newPost != null;
                        Collection<Object> arr = newPost.values();
                        String email_owner = (String) arr.toArray()[0];
                        int houseNum = Integer.parseInt(arr.toArray()[1].toString());
                        String city = (String) arr.toArray()[2];
                        double price = Double.parseDouble(arr.toArray()[3].toString());
                        String emailCustomer = (String) arr.toArray()[5];
                        HashMap<String, String> map = (HashMap<String, String>) arr.toArray()[7];
                        Time avHours = new Time(map.get("start"), map.get("end"));
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
                        if (parking_now) {
                            first_time[0] = false;

                            reference_cus.child(key_order).setValue(order);
                            owner.child(ownerID).child(key_order).setValue(order);
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
                Intent intent = new Intent(OrdersDB.this, Location.class);
                startActivity(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
