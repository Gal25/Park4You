package com.example.park4you.Model;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.park4you.Presenter.PresenterMenu;
import com.example.park4you.Object.Payment;
import com.example.park4you.Presenter.PresenterPayment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModelPaymentDB extends PresenterMenu {
    /**
     * This class is responsible of the payment methods in the app
     */
    private EditText creditNumber;
    private EditText expirationDate;
    private EditText cvv;
    private EditText email;
    private String CreditNumber, ExpirationDate, CVV, Email;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private PresenterPayment presenterPayment;

    public ModelPaymentDB(PresenterPayment presenterPayment) {
        this.presenterPayment = presenterPayment;
    }

    /**
     * This will get all the payment info from the user and saves it to the database
     * It gets all of the user payment info
     */
    public void addPamymentDetails(String CreditNumber, String ExpirationDate,String CVV, String Email) {
        Payment payment = new Payment(CreditNumber, ExpirationDate, CVV, Email);

        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("PaymentDetails");
        reference.child(auth.getUid()).setValue(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

}