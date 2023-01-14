package com.example.park4you.Presenter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.park4you.APIClient;
import com.example.park4you.R;
import com.example.park4you.ServerStrings;
//import com.example.park4you.User.PresenterUser;
import com.example.park4you.Object.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.google.gson.Gson;

public class PresenterLogin extends AppCompatActivity {
    /**
     * This class handles the login page. All the user functionalities such as login, forgot password and new user.
     *
     */
    private EditText passwordEditText;
    private EditText textEmail;
    DatabaseReference ref_user;
    private String password, email;
    private FirebaseAuth mAuth;
    TextView forgetpass;
    public ProgressDialog loginprogress;
    private final APIClient client = new APIClient();

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

    /**
     * This function takes email and password, checks if they exist in the database and if they do the user is logged in.
     */
    public void signIN(String email,String password){
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
                            Intent intent = new Intent(PresenterLogin.this, PresenterLocation.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    /**
     * This function takes the input from the user and calls the server to handle the log in.
     * Then if the client is logged in it will take him to the nect page.
     */
    public void regButton(View view){
        password = passwordEditText.getText().toString();
        email = textEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
        }

        client.sendGetRequest(ServerStrings.AUTH + email + "/:" + password, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                System.out.println(responseBody);
                Log.d(TAG, "Server response: " + responseBody);
                if (responseBody.contains("password") && responseBody.contains("code")) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PresenterLogin.this, "Wrong password please try again", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else if(responseBody.contains("code")){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PresenterLogin.this, "Wrong email please try again", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Gson gson = new Gson();
                    User user = gson.fromJson(responseBody, User.class);
//                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    if(mAuth.getCurrentUser() == null ) {
                        signIN(email,password);
                    }

                    Map<String, Object> m = new HashMap<>();
                    m.put("password", password);
                    ref_user.child(user.getId()).updateChildren(m);
                    Intent intent = new Intent(getApplicationContext(), PresenterLocation.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG,e.getMessage());
            }
        });
    }


    /**
     * If its a new user send it to the new user page.
     */
    public void newUserButton(View view){
        Intent in = new Intent(PresenterLogin.this, PresenterNewUser.class);
        startActivity(in);
    }

    ProgressDialog loadingBar;

    /***
     * This handles the forgot password button. It opens a dialog text and asks for email.
     */
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

    /***
     * After the user has inserted his email to the forgot password box it will begin password recovery here.
     * It will send the user email asking him to change his password for a new one.
     * @param email - The user email
     */
    private void beginRecovery(String email) {
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // calling sendPasswordResetEmail open your email and write the new password and then you can login
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if (task.isSuccessful()) {
                    // if isSuccessful then done message will be shown
                    // and you can change the password
                    Toast.makeText(PresenterLogin.this, "Done sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PresenterLogin.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(PresenterLogin.this, "Error Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
