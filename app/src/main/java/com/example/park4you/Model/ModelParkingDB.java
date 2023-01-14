package com.example.park4you.Model;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.park4you.Presenter.PresenterMenu;
//import com.example.park4you.RentUser.RentUser;
import com.example.park4you.Object.Parking;
import com.example.park4you.Presenter.PresenterAvailableParking;
import com.example.park4you.Presenter.PresenterDeleteParking;
import com.example.park4you.Presenter.PresenterNewParking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

//This class saves a new parking entered by a user to the database
public class ModelParkingDB extends PresenterMenu {
    /**
     This class has methods which is responsible for deleting a parking, adding a new parking and updating it in both customer and owner
     */
    private ModelOrdersDB ordersDB;
    private PresenterNewParking presenterNewParking;
    private PresenterAvailableParking presenterAvailableParking;
    private PresenterDeleteParking deleteParking;


    public ModelParkingDB(PresenterNewParking presenterNewParking) {
        this.presenterNewParking = presenterNewParking;
    }

    public ModelParkingDB(PresenterAvailableParking presenterAvailableParking) {
        this.presenterAvailableParking = presenterAvailableParking;
    }

    public ModelParkingDB(PresenterDeleteParking deleteParking) {
        this.deleteParking = deleteParking;
    }

    /**
     * This will add a new parking to the database
     * @param key
     * @param city
     * @param hashMap
     */
    public void newParking(String key,String city,HashMap<String, Object> hashMap) {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Addresses");
        assert key != null;
        reference.child(city).child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    /**
     * This function adds a new parking to the owner's rented from parking and the customer's rented parking
     * @param city - The city where the parking is located
     * @param id - The id of the customer
     * @param list - The list of all the park spots available
     */
    public int UpdateParking(String city, String id, ArrayList<Parking> list){

        ordersDB = new ModelOrdersDB();
        final int[] pos = {0};
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Addresses");
        database.child(city).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parking park = dataSnapshot.getValue(Parking.class);
                    assert park != null;
                    if (park.getid().equals(id)) {
                        System.out.println("list  "+list);
                        ordersDB.create_order_customer(park); //add the parking to the user's parking database
                        ordersDB.create_order_owner(park); //add the parking to the owner's parking database
                        for (int i = 0; i < list.size(); i++){
                            if (list.get(i).equals(park)){
                                pos[0] = i;
                            }
                        }
                        dataSnapshot.getRef().removeValue();

                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return pos[0];
    }

    /**
     * This will delete a parking from the database if an owner wishes to stop renting a park spot.
     * @param city - City where the parking is located
     * @param email - His email
     * @param street - Street of the parking
     * @param houseNum - it's number
     */
    public void DeleteParking(String city, String email, String street, int houseNum){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Addresses");
        FirebaseAuth auto = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auto.getCurrentUser();
        database.child(city).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found;
                found = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parking parking = dataSnapshot.getValue(Parking.class);
                    assert parking != null;
                    if (email.equals(parking.getEmail())) {
                        if (parking.getStreet().equals(street) && parking.getHouseNum() == houseNum) {
                            found=true;
                            dataSnapshot.getRef().removeValue();
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
