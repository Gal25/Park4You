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
//    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference database_user ;
//    = db.getReference("Users");
    FirebaseUser firebaseUser;
    FirebaseAuth auto;
    String ownerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void create_order(Parking p){
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        database_user = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference reference_owner= FirebaseDatabase.getInstance().getReference("Owner`s Parking").child(p.getOwnerID());
        String key = database_user.push().getKey();
        String key_customer  = reference_owner.push().getKey();
        HashMap<String, Object> hashMap_customer = new HashMap<>();
        hashMap_customer.put("emailOwner ", p.getEmail());
        hashMap_customer.put("emailCustomer ", firebaseUser.getEmail());
        hashMap_customer.put("id ", key_customer);
        hashMap_customer.put("parkingCity ", p.getCity());
        hashMap_customer.put("street ", p.getStreet());
        hashMap_customer.put("parkingId ", p.getid());
        hashMap_customer.put("houseNum ", p.getHouseNum());
        hashMap_customer.put("price ", p.getPrice());
        hashMap_customer.put("avHours ", p.getAvHours());

        assert key != null;
        assert key_customer != null;
        reference_owner.child(key_customer).setValue(hashMap_customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void find_user(String mail){
        System.out.println("------- 69 -------");
        System.out.println("email   76"+mail);
        database_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    System.out.println(dataSnapshot);
                    User o = dataSnapshot.getValue(User.class);
                    if(o.getEmail().equals(mail)){
                        ownerId = o.getId();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                User user = task.getResult().getValue(User.class);
//                assert user != null;
//                ownerId = user.getId();
//                System.out.println("------- 82 -------" + user);
//            }
//        });

 }


}
