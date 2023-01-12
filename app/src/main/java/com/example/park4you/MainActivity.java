package com.example.park4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.park4you.Presenter.PresenterLogin;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    public FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();
        Intent intent = new Intent(MainActivity.this, PresenterLogin.class);
        startActivity(intent);
    }
}