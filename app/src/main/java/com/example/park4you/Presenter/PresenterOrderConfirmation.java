package com.example.park4you.Presenter;

import android.os.Bundle;

import com.example.park4you.Presenter.PresenterMenu;
import com.example.park4you.R;

public class PresenterOrderConfirmation extends PresenterMenu {
    /**
     * simple class to show page of an order confirmation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
