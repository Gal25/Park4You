package com.example.park4you.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.park4you.Location.Location;
import com.example.park4you.LoginUser.Login;
import com.example.park4you.Order.OwnerParkingList;
import com.example.park4you.Order.UserParkingList;
import com.example.park4you.R;
import com.example.park4you.RentUser.RentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private TextView textViewemail, textViewUserName, textViewphoneNum;
    private String email, UserName, phoneNum;
    DatabaseReference database;
    FirebaseAuth auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        textViewemail = findViewById(R.id.textViewEmail);
        textViewUserName = findViewById(R.id.textViewName);
        textViewphoneNum = findViewById(R.id.textViewPhoneNum);
        database = FirebaseDatabase.getInstance().getReference("Users");
        auto = FirebaseAuth.getInstance();
        showUserProfile();

    }

    private void showUserProfile() {
        FirebaseUser firebaseUser = auto.getCurrentUser();
        String userID = firebaseUser.getUid();

        //extracting user from database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        UserName = user.getUserName();
                        email = firebaseUser.getEmail();
                        phoneNum = user.getPhoneNum();
                        textViewemail.setText(email);
                        textViewphoneNum.setText(phoneNum);
                        textViewUserName.setText(UserName);
                    }
                    else{
                        Toast.makeText(UserProfile.this, "user null!",
                                Toast.LENGTH_LONG).show();
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something went wrong!",
                        Toast.LENGTH_LONG).show();

            }
        });



    }


    //if the user want to return to location value
    public void returnButton(View view){
        Intent loginIntent = new Intent(this, Location.class);
        startActivity(loginIntent);

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