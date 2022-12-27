package com.example.park4you.Order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.Parking.Parking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ordersDB extends AppCompatActivity {

    DatabaseReference database_user;
    FirebaseUser firebaseUser;
    FirebaseAuth auto;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //create a data document in database that represents the orders of the users
    public void create_order_customer(Parking p) {
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference_cus = FirebaseDatabase.getInstance().getReference("Customers Parking").child(firebaseUser.getUid());
        database_user = FirebaseDatabase.getInstance().getReference("Users");
//        String key = database_user.push().getKey();
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
//        String key = database_user.push().getKey();
//        String key_owner  = reference_owner.push().getKey();
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
//        assert key_owner != null;
        reference_owner.child(key).setValue(hashMap_customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

}
