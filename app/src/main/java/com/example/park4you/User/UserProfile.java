package com.example.park4you.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.park4you.Location.Location;
import com.example.park4you.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        textViewemail = findViewById(R.id.textViewEmail);
        textViewUserName = findViewById(R.id.textViewName);
        textViewphoneNum = findViewById(R.id.textViewPhoneNum);
        database = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseAuth auto = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auto.getCurrentUser();

        if( firebaseUser == null){
            Toast.makeText(UserProfile.this, "Somthing went wrong! User's details are not available at this moment",
                    Toast.LENGTH_LONG).show();

        }
        else {
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        System.out.println(userID);

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
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Somthing went wrong!",
                        Toast.LENGTH_LONG).show();

            }
        });



    }


    public void returnButton(View view){
        Intent loginIntent = new Intent(this, Location.class);
        startActivity(loginIntent);

    }
}