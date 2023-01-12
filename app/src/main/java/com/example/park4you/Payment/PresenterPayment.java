package com.example.park4you.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.park4you.Menu.Menu;
import com.example.park4you.Parking.PresenterAvailableParking;
import com.example.park4you.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class PresenterPayment extends Menu {

    private EditText creditNumber;
    private EditText expirationDate;
    private EditText cvv;
    private EditText email;
    private String CreditNumber, ExpirationDate, CVV, Email;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private DatabaseReference reference2;
    private PresenterAvailableParking presenterAvailableParking;
    private ModelPaymentDB paymentDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        paymentDB = new ModelPaymentDB(this);
    }

    //This will get all the payment info from the user and saves it to the database
    public void SaveButton(View view) {
        presenterAvailableParking = new PresenterAvailableParking();
        creditNumber = findViewById(R.id.CreditNumber);
        expirationDate = findViewById(R.id.ExpirationDate);
        cvv = findViewById(R.id.CVV);
        email = findViewById(R.id.Email_payment);
        CreditNumber = creditNumber.getText().toString();
        ExpirationDate = expirationDate.getText().toString();
        CVV = cvv.getText().toString();
        Email = email.getText().toString();

        paymentDB.addPamymentDetails(CreditNumber, ExpirationDate, CVV, Email);
        Toast.makeText(PresenterPayment.this, "Details Saved!", Toast.LENGTH_SHORT).show();


        String cityName = getIntent().getStringExtra("City Name");
        String streetName = getIntent().getStringExtra("Street Name");
        String AvHours = getIntent().getStringExtra("time");
        Intent intent = new Intent(this, PresenterAvailableParking.class); //order Confirmishiadadms
        intent.putExtra("City Name", cityName);
        intent.putExtra("Street Name", streetName);
        intent.putExtra("time", AvHours);
        startActivity(intent);
    }
}
