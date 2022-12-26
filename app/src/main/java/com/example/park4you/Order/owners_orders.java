package com.example.park4you.Order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.Parking.Parking;
import com.example.park4you.User.User;
import com.example.park4you.User.UserDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class owners_orders extends AppCompatActivity {
    DatabaseReference database_user ;
    FirebaseUser firebaseUser;
    FirebaseAuth auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //create a data document in database that represents the orders of the owners
    public void create_order(Parking p){
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        database_user = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference reference_owner= FirebaseDatabase.getInstance().getReference("Owners Parking").child(p.getOwnerID());
        String key = database_user.push().getKey();
        String key_owner  = reference_owner.push().getKey();
        HashMap<String, Object> hashMap_customer = new HashMap<>();
        hashMap_customer.put("emailOwner ", p.getEmail());
        hashMap_customer.put("emailCustomer ", firebaseUser.getEmail());
        hashMap_customer.put("id ", key_owner);
        hashMap_customer.put("parkingCity ", p.getCity());
        hashMap_customer.put("street ", p.getStreet());
        hashMap_customer.put("parkingId ", p.getid());
        hashMap_customer.put("houseNum ", p.getHouseNum());
        hashMap_customer.put("price ", p.getPrice());
        hashMap_customer.put("avHours ", p.getAvHours());
        hashMap_customer.put("parking_now ", true);
        assert key != null;
        assert key_owner != null;
        reference_owner.child(key_owner).setValue(hashMap_customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }






    }