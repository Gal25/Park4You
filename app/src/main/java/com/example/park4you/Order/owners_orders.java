package com.example.park4you.Order;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.Parking.Parking;
import com.example.park4you.User.User;
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
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference database_user = db.getReference("Users");
    FirebaseUser firebaseUser;
    FirebaseAuth auto;
    String ownerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void create_order(Parking p){

        auto = FirebaseAuth.getInstance();
//        UserRecord user = auto1.getUserByEmail(p.getEmail());
        firebaseUser = auto.getCurrentUser();
        String id = find_user(p.getEmail());
        System.out.println("41 - id " + id);
        DatabaseReference reference_owner= FirebaseDatabase.getInstance().getReference("Owner`s Parking").child(id);
        database_user = FirebaseDatabase.getInstance().getReference("Users");
        String key = database_user.push().getKey();
        String key_customer  = reference_owner.push().getKey();
        HashMap<String, Object> hashMap_customer = new HashMap<>();
        hashMap_customer.put("email owner ", p.getEmail());
        hashMap_customer.put("email customer ", firebaseUser.getEmail());
        hashMap_customer.put("id ", key_customer);
        hashMap_customer.put("parking city ", p.getCity());
        hashMap_customer.put("street ", p.getStreet());
        hashMap_customer.put("parking id ", p.getid());

        assert key != null;
        assert key_customer != null;
        reference_owner.child(key_customer).setValue(hashMap_customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String find_user(String mail){
        System.out.println("------- 69 -------");
        database_user.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("------ 73 ------");
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    if (user.getEmail().equals(mail)){
                        ownerId =  user.getId();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return ownerId;
 }


}
