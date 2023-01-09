package com.example.park4you.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.park4you.Location.Location;
import com.example.park4you.LoginUser.Login;
import com.example.park4you.Order.OrdersDB;
import com.example.park4you.Order.PresentOrders;
import com.example.park4you.Parking.DeleteParking;
import com.example.park4you.Parking.ParkingDB;
import com.example.park4you.Payment.PaymentDB;
import com.example.park4you.R;
import com.example.park4you.User.UserProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.A;

public class Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    @SuppressLint("InflateParams")
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
//        FrameLayout container = drawerLayout.findViewById(R.id.container);
//        container.addView(view);
        super.setContentView(drawerLayout);
        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String owner;
        String func;
        switch (item.getItemId()){
            case R.id.item2:
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(intent);
                return true;
            case R.id.item3:
                Intent intent1 = new Intent(getApplicationContext(), Location.class);
                startActivity(intent1);
                return true;
            case R.id.item4:
                Intent intent2 = new Intent(getApplicationContext(), ParkingDB.class);
                startActivity(intent2);
                return true;
            case R.id.item5:
                Intent intent3 = new Intent(getApplicationContext(), PresentOrders.class);
                owner = "user";
                intent3.putExtra("owner", owner);
                startActivity(intent3);

                return true;
            case R.id.item6:
                Intent intent4 = new Intent(getApplicationContext(), PresentOrders.class);
                owner = "owner";
                intent4.putExtra("owner", owner);
                startActivity(intent4);
                return true;
            case R.id.item7:
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent5);
                return true;
            case R.id.item8:
                Intent intent6 = new Intent(getApplicationContext(), OrdersDB.class);
                func = "release_parking";
                intent6.putExtra("func", func);
                startActivity(intent6);
                return true;
            case R.id.item9:
                Intent intent7 = new Intent(getApplicationContext(), DeleteParking.class);
                startActivity(intent7);
                return true;
            case R.id.item10:
                Intent intent8 = new Intent(getApplicationContext(), PaymentDB.class);
                startActivity(intent8);
                return true;
            default: return false;
        }
    }
}