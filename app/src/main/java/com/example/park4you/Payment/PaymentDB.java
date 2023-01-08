package com.example.park4you.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.park4you.Location.Location;
import com.example.park4you.Menu.Menu;
import com.example.park4you.Order.PresenterOrderConfirmation;
import com.example.park4you.Parking.PresenterAvailableParking;
import com.example.park4you.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class PaymentDB extends Menu {

    private EditText creditNumber;
    private EditText expirationDate;
    private EditText cvv;
    private EditText email;
    private String CreditNumber, ExpirationDate, CVV, Email;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private DatabaseReference reference2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
//        reference = FirebaseDatabase.getInstance().getReference("PaymentDetails");
    }
    //This will get all the payment info from the user and saves it to the database
    public void SaveButton(View view) {

        creditNumber = findViewById(R.id.CreditNumber);
        expirationDate = findViewById(R.id.ExpirationDate);
        cvv = findViewById(R.id.CVV);
        email = findViewById(R.id.Email_payment);
        CreditNumber = creditNumber.getText().toString();
        ExpirationDate = expirationDate.getText().toString();
        CVV = cvv.getText().toString();
        Email = email.getText().toString();
        Payment payment = new Payment(CreditNumber, ExpirationDate, CVV, Email);

        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("PaymentDetails");
        reference.child(auth.getUid()).setValue(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PaymentDB.this, "Details Saved!", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent(this, PresenterOrderConfirmation.class); //order Confirmishiadadms
        startActivity(intent);
    }

}