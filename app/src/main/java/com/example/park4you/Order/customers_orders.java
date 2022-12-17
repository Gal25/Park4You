package com.example.park4you.Order;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.Parking.Parking;
import com.example.park4you.Parking.ParkingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class customers_orders extends AppCompatActivity {

    DatabaseReference database_user;
    FirebaseUser firebaseUser;
    FirebaseAuth auto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    public void create_order(Parking p) {
        auto = FirebaseAuth.getInstance();
        firebaseUser = auto.getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference_cus = FirebaseDatabase.getInstance().getReference("Customers Parking").child(firebaseUser.getUid());
        database_user = FirebaseDatabase.getInstance().getReference("Users");
        String key = database_user.push().getKey();
        String key_customer  = reference_cus.push().getKey();
        HashMap<String, Object> hashMap_customer = new HashMap<>();
        hashMap_customer.put("emailOwner ", p.getEmail());
        hashMap_customer.put("emailCustomer ", firebaseUser.getEmail());
        hashMap_customer.put("id ", key_customer);
        hashMap_customer.put("city ", p.getCity());
        hashMap_customer.put("street ", p.getStreet());
        hashMap_customer.put("parkingId ", p.getid());
        hashMap_customer.put("houseNum ", p.getHouseNum());
        hashMap_customer.put("price ", p.getPrice());
        hashMap_customer.put("avHours ", p.getAvHours());

        assert key != null;
        assert key_customer != null;
        reference_cus.child(key_customer).setValue(hashMap_customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
