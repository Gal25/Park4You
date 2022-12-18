package com.example.park4you.Order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.park4you.R;
import android.content.Context;

import java.util.ArrayList;

// this class is adapter to the screen -> to create a list of owner parking that that he posted and users ordered them
public class OwnerParkingsAdapter extends RecyclerView.Adapter<OwnerParkingsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Order> list;

    public OwnerParkingsAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.owner_parkings, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = list.get(position);
        holder.cityName.setText(order.getCity());
        holder.streetName.setText(order.getStreet());
        holder.houseNumber.setText(Integer.toString(order.getHouseNum()));
        holder.avHours.setText(order.getAvHours());
        holder.pricePerHour.setText(Double.toString(order.getPrice()));
        holder.ownerEmail.setText(order.getEmailOwner());
        holder.customerEmail.setText(order.getEmailCustomer());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cityName, streetName, houseNumber, avHours, pricePerHour, ownerEmail, customerEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.textCityO);
            streetName = itemView.findViewById(R.id.textStreetO);
            houseNumber = itemView.findViewById(R.id.textHouseNumO);
            avHours = itemView.findViewById(R.id.textAvailableHoursO);
            pricePerHour = itemView.findViewById(R.id.textPriceO);
            ownerEmail = itemView.findViewById(R.id.textEmailO);
            customerEmail = itemView.findViewById(R.id.textCustomerEmailO);
        }
    }
}