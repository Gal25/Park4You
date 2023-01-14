package com.example.park4you.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.park4you.Model.ModelPaymentDB;
import com.example.park4you.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class PresenterPayment extends PresenterMenu {
    /**
     * This class will get all the information from the user about his payment info and call the payment model to handle it.
     */
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

    /**
     * This will get all the payment info from the user and saves it to the database
     */
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

        paymentDB.addPaymentDetails(CreditNumber, ExpirationDate, CVV, Email);
        Toast.makeText(PresenterPayment.this, "Details Saved!", Toast.LENGTH_SHORT).show();

        String release = getIntent().getStringExtra("details");
        System.out.println("realse " + release);
        if (release == null) {

            String cityName = getIntent().getStringExtra("City Name");
            String streetName = getIntent().getStringExtra("Street Name");
            String AvHours = getIntent().getStringExtra("time");
            Intent intent = new Intent(this, PresenterAvailableParking.class); //order Confirmishiadadms
            intent.putExtra("City Name", cityName);
            intent.putExtra("Street Name", streetName);
            intent.putExtra("time", AvHours);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, PresenterLocation.class); //order Confirmishiadadms
            startActivity(intent);
        }
    }
}
