package com.example.park4you.Order;

import android.os.Bundle;

import com.example.park4you.Menu.Menu;
import com.example.park4you.R;

public class PresenterOrderConfirmation extends Menu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
