package com.example.park4you.LoginUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.park4you.R;
import com.example.park4you.Location.Location;
import com.example.park4you.User.User;
import com.example.park4you.User.UserDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText textEmail;
    DatabaseReference ref_user;
    private String password, email;
    private FirebaseAuth mAuth;
    Button forgetpass;
    public ProgressDialog loginprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
//        getSupportActionBar().setTitle("Login");
        ref_user = FirebaseDatabase.getInstance().getReference("Users");
        mAuth= FirebaseAuth.getInstance();
        forgetpass=findViewById(R.id.forgetpass);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginprogress=new ProgressDialog(this);
        passwordEditText = findViewById(R.id.PasswordLogIn);
        textEmail = findViewById(R.id.EmailNewUser);
    }
    //login with email and password and check if the user put a input
    public void regButton(View view){
        password = passwordEditText.getText().toString();
        email = textEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userID = firebaseUser.getUid();
                            Map<String, Object> m = new HashMap<>();
                            m.put("password", password);
                            ref_user.child(userID).updateChildren(m);
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, Location.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }


    //if there are a new user go to add the user to DB with UserDB class
    public void newUserButton(View view){
        Intent in = new Intent(Login.this, UserDB.class);
        startActivity(in);
    }

    ProgressDialog loadingBar;

    public void showRecoverPasswordDialog(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailet= new EditText(this);

        // write the email using which you registered
        emailet.setHint("Email");//.setText("Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=emailet.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // calling sendPasswordResetEmail
        // open your email and write the new
        // password and then you can login
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if (task.isSuccessful()) {
                    // if isSuccessful then done message will be shown
                    // and you can change the password
                    Toast.makeText(Login.this, "Done sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Login.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(Login.this, "Error Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
