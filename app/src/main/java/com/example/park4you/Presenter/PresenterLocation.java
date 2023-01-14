package com.example.park4you.Presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//import com.example.park4you.Order.PresenterOwnerOrders;
//import com.example.park4you.Parking.ParkingList;
import com.example.park4you.Object.Time;
import com.example.park4you.R;
//import com.example.park4you.RentUser.RentUser;


public class PresenterLocation extends PresenterMenu {
    /**
     * This class handles a park spot search
     * @param savedInstanceState
     */
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
    }

    /**
     * Gets all the information a user wants about available parking in a city, street and hours
     */
    public void proceedButton(View view){
        //This function will get the data of a parking the user wants to search for and will direct him using the intent to the parking.
        Intent intent = new Intent(PresenterLocation.this, PresenterAvailableParking.class);
        EditText editCityName = findViewById(R.id.city);
        EditText editStreetName = findViewById(R.id.street);
        EditText editHoursStartName = findViewById(R.id.StartHours);
        EditText editHoursEndName = findViewById(R.id.EndHours);
        String cityName = editCityName.getText().toString();
        intent.putExtra("City Name", cityName);

        String streetName = editStreetName.getText().toString();
        intent.putExtra("Street Name", streetName);

        String AvHoursStart = editHoursStartName.getText().toString();
        String AvHoursEnd = editHoursEndName.getText().toString();
        Time time = new Time(AvHoursStart, AvHoursEnd);
        intent.putExtra("time", time.toString());
        startActivity(intent);
    }
}