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

// this class is adapter to the screen -> to create a list of User parking that he order
public class UserOrdersAdapter extends RecyclerView.Adapter<UserOrdersAdapter.MyViewHolder>{
    Context context;
    ArrayList<Order> list;

    public UserOrdersAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_parkings, parent, false);
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

        TextView cityName, streetName, houseNumber, avHours, pricePerHour, ownerEmail, customerEmail, OrderId, ParkingId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.textCityU);
            streetName = itemView.findViewById(R.id.textStreetU);
            houseNumber = itemView.findViewById(R.id.textHouseNumU);
            avHours = itemView.findViewById(R.id.textAvailableHoursU);
            pricePerHour = itemView.findViewById(R.id.textPriceU);
            ownerEmail = itemView.findViewById(R.id.textEmailU);
            customerEmail = itemView.findViewById(R.id.textCustomerEmailU);
        }
    }
}