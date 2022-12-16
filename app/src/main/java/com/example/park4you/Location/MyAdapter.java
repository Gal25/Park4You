package com.example.park4you.Location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.park4you.Parking.Parking;
import com.example.park4you.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Parking> list;

    public MyAdapter(Context context, ArrayList<Parking> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.available_parking, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Parking parking = list.get(position);
        holder.cityName.setText(parking.getCity());
        holder.streetName.setText(parking.getStreet());
        holder.houseNumber.setText(Integer.toString(parking.getHouseNum()));
        holder.avHours.setText(parking.getAvHours());
        holder.pricePerHour.setText(Double.toString(parking.getPrice()));
        holder.email.setText(parking.getEmail());
        holder.id.setText(parking.getid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cityName, streetName, houseNumber, avHours, pricePerHour, email, id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.textCity);
            streetName = itemView.findViewById(R.id.textStreet);
            houseNumber = itemView.findViewById(R.id.textHouseNum);
            avHours = itemView.findViewById(R.id.textAvailableHours);
            pricePerHour = itemView.findViewById(R.id.textPrice);
            email = itemView.findViewById(R.id.textEmail);
            id = itemView.findViewById(R.id.park_id);
        }
    }

}
