package com.example.park4you.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.park4you.Model.ModelUserDB;
import com.example.park4you.R;

public class PresenterNewUser extends PresenterMenu {
    /***
     * This class handles the input from a new user
     */
    private EditText passwordEditText,textName, textEmail, textPhone;
    public String id;
    private ModelUserDB userDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_user);
        textName = findViewById(R.id.NameNewUser);
        textEmail = findViewById(R.id.EmailNewUser);
        textPhone = findViewById(R.id.PhoneNumberuUser);
        passwordEditText = findViewById(R.id.passwordUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userDB = new ModelUserDB(this);

    }

    /**
     * Will get all the data from the edit texts and call the registerUser function to add this new user tp the database
     */
    public void registerButton(View view){
        String username = textName.getText().toString();
        String phone = textPhone.getText().toString();
        String email = textEmail.getText().toString();
        String password = passwordEditText.getText().toString();
        userDB.registerUser(email, password, phone, username);
        Intent loginIntent = new Intent(PresenterNewUser.this, PresenterLogin.class);
        startActivity(loginIntent);

    }
}
