package com.example.park4you.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.Presenter.PresenterNewUser;
import com.example.park4you.Presenter.PresenterUserProfile;
import com.example.park4you.Object.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public class ModelUserDB extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final String USERS = "Users";
    private String TAG = "Init user";
    private static FirebaseAuth mAuth;
    private static FirebaseUser firebaseUser;
    public static User user;
    public String id;
    private PresenterNewUser presenterUser;
    private PresenterUserProfile userProfile;

    public ModelUserDB(PresenterUserProfile userProfile){
        this.userProfile = userProfile;
    }
    public ModelUserDB(PresenterNewUser presenterUser){
        this.presenterUser = presenterUser;
    }

    public void registerUser(String email,String password,String phone,String username) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        presenterUser = new PresenterNewUser();

        //if it is not successful check if there are the same user in auth firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            id = task.getResult().getUser().getUid();
                            user = new User(email, username,password, phone, id);
                            Log.d(TAG, "createUserWithEmail:success");
                            mDatabase.child(id).setValue(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }


    public void getUser() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        //extracting user from database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 User user = snapshot.getValue(User.class);
                assert user != null;
                userProfile.showUserProfile(user.getUserName(),user.getEmail(),user.getPhoneNum());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
