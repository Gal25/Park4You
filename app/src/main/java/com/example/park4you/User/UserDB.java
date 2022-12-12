package com.example.park4you.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.LoginUser.Login;
import com.example.park4you.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserDB extends AppCompatActivity {

    private EditText passwordEditText,textName, textEmail, textPhone;
    private DatabaseReference mDatabase;
    private static final String USERS = "Users";
    private String TAG = "Init user";
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_user);
        textName = findViewById(R.id.NameNewUser);
        textEmail = findViewById(R.id.EmailNewUser);
        textPhone = findViewById(R.id.PhoneNumberuUser);
        passwordEditText = findViewById(R.id.passwordUser);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();

    }
    public void registerButton(View view){
        String username = textName.getText().toString();
        String phone = textPhone.getText().toString();
        email = textEmail.getText().toString();
        password = passwordEditText.getText().toString();
        user = new User(email, username,password, phone);
        registerUser();

    }

    public void registerUser() {
        System.out.println(password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(UserDB.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void updateUI() {
        String userid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase.child(userid).setValue(user);
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }



}
