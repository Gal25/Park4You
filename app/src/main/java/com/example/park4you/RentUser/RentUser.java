package com.example.park4you.RentUser;

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

import com.example.park4you.Location.Location;
import com.example.park4you.LoginUser.Login;
import com.example.park4you.Order.OwnerParkingList;
import com.example.park4you.R;
import com.example.park4you.Order.UserParkingList;
import com.example.park4you.User.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RentUser extends AppCompatActivity {
    FirebaseAuth auto;
    private DatabaseReference databaseReference;
    private EditText EmailText, cityText, streetText, houseNumText, avHoursText,priceText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_user);
        auto = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        EmailText = findViewById(R.id.Email);
        cityText = findViewById(R.id.City);
        streetText = findViewById(R.id.Street);
        houseNumText = findViewById(R.id.HouseNumber);
        avHoursText = findViewById(R.id.AvailableHours);
        priceText = findViewById(R.id.Price);

    }

    //When the user clicks the add button, the parking he entered is updated in the database
    // with all the details
    public void publishButton(View view){
        newParking();
    }
    public void newParking() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Addresses");
        String city = cityText.getText().toString();
        String key = reference.child(city).push().getKey();
        String street = streetText.getText().toString();
        int houseNum = Integer.parseInt(houseNumText.getText().toString());
        double price = Double.parseDouble(priceText.getText().toString());
        String avHours = avHoursText.getText().toString();
        String email = EmailText.getText().toString();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", key);
        hashMap.put("avHours", avHours);
        hashMap.put("city", city);
        hashMap.put("houseNum", houseNum);
        hashMap.put("price", price);
        hashMap.put("street", street);
        hashMap.put("email", email);
        hashMap.put("ownerID", Objects.requireNonNull(auto.getCurrentUser()).getUid());
        assert key != null;
        reference.child(city).child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RentUser.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
}


    //menu
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
                Intent intent = new Intent(this, UserProfile.class);
                startActivity(intent);
                return true;

            //choose parking
            case R.id.item3:
                Intent intent1 = new Intent(this, Location.class);
                startActivity(intent1);
                return true;

            //add parking
            case R.id.item4:
                Intent intent2 = new Intent(this, RentUser.class);
                startActivity(intent2);
                return true;

            //show the user's orders
            case R.id.item5:
                Intent intent3 = new Intent(this, UserParkingList.class);
                startActivity(intent3);
                return true;

            //Shows the orders ordered from the owner
            case R.id.item6:
                Intent intent4 = new Intent(this, OwnerParkingList.class);
                startActivity(intent4);
                return true;

            //log out
            case R.id.item7:
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(this, Login.class);
                startActivity(intent5);
                return true;

            default: return super.onOptionsItemSelected(item);
        }

    }
}