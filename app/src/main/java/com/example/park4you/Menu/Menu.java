package com.example.park4you.Menu;

import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.park4you.Location.Location;
import com.example.park4you.LoginUser.Login;
import com.example.park4you.Order.OrdersDB;
import com.example.park4you.Order.PresentOrders;
import com.example.park4you.Parking.DeleteParking;
import com.example.park4you.Parking.ParkingDB;
import com.example.park4you.Payment.PaymentDB;
import com.example.park4you.R;
import com.example.park4you.User.UserProfile;
import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {


    //create menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String owner;
        String func;
        switch (item.getItemId()){
            case R.id.item2:
                Intent intent = new Intent(this, UserProfile.class);
                startActivity(intent);
                return true;
            case R.id.item3:
                Intent intent1 = new Intent(this, Location.class);
                startActivity(intent1);
                return true;
            case R.id.item4:
                Intent intent2 = new Intent(this, ParkingDB.class);
                startActivity(intent2);
                return true;
            case R.id.item5:
                Intent intent3 = new Intent(this, PresentOrders.class);
                owner = "user";
                intent3.putExtra("owner", owner);
                startActivity(intent3);

                return true;
            case R.id.item6:
                Intent intent4 = new Intent(this, PresentOrders.class);
                owner = "owner";
                intent4.putExtra("owner", owner);
                startActivity(intent4);
                return true;
            case R.id.item7:
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(this, Login.class);
                startActivity(intent5);
                return true;
            case R.id.item8:
                Intent intent6 = new Intent(this, OrdersDB.class);
                func = "release_parking";
                intent6.putExtra("func", func);
                startActivity(intent6);
//                release_parking();
                return true;
            case R.id.item9:
                Intent intent7 = new Intent(this, DeleteParking.class);
//                func = "release_parking";
//                intent6.putExtra("func", func);
                startActivity(intent7);
//                release_parking();
                return true;
            case R.id.item10:
                Intent intent8 = new Intent(this, PaymentDB.class);
//                func = "release_parking";
//                intent6.putExtra("func", func);
                startActivity(intent8);
//                release_parking();
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
}
