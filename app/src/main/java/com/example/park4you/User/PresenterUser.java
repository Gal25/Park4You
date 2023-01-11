package com.example.park4you.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.park4you.LoginUser.Login;
import com.example.park4you.Menu.Menu;
import com.example.park4you.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PresenterUser extends Menu {

    private EditText passwordEditText,textName, textEmail, textPhone;
    private String TAG = "Init user";
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String email;
    private String password;
    private  String username;
    private String phone;
    private User user;
    public String id;
    private UserDB userDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_user);
        textName = findViewById(R.id.NameNewUser);
        textEmail = findViewById(R.id.EmailNewUser);
        textPhone = findViewById(R.id.PhoneNumberuUser);
        passwordEditText = findViewById(R.id.passwordUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userDB = new UserDB();

    }

//    public void registerButton(View view){
////        mAuth = FirebaseAuth.getInstance();
////        firebaseUser = mAuth.getCurrentUser();
//        username = textName.getText().toString();
//        phone = textPhone.getText().toString();
//        email = textEmail.getText().toString();
//        password = passwordEditText.getText().toString();
//        userDB.registerUser(email, password, phone, username);
//
//    }
    //Viewer
    //update the UI -> go to login page and set value in our database
    public void updateUI() {
        Intent loginIntent = new Intent(PresenterUser.this, Login.class);
        startActivity(loginIntent);
    }
}
