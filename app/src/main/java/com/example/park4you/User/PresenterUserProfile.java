package com.example.park4you.User;

import android.os.Bundle;
import com.example.park4you.Menu.Menu;

import android.widget.TextView;

//import com.example.park4you.Order.PresenterOwnerOrders;
import com.example.park4you.R;
//import com.example.park4you.RentUser.RentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class PresenterUserProfile extends Menu {

    private TextView textViewemail, textViewUserNameTop, textViewUserNameBot,  textViewphoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        textViewemail = findViewById(R.id.textViewEmail);
        textViewUserNameTop = findViewById(R.id.textViewNameTop);
        textViewUserNameBot = findViewById(R.id.textViewNameBot);
        textViewphoneNum = findViewById(R.id.textViewPhoneNum);
        ModelUserDB userDB = new ModelUserDB(this);
        userDB.getUser();

    }
    //Presenter
    public void showUserProfile(String UserName, String email,String phoneNum) {
        textViewemail.setText(email);
        textViewphoneNum.setText(phoneNum);
        textViewUserNameTop.setText(UserName);
        textViewUserNameBot.setText(UserName);
        }


}