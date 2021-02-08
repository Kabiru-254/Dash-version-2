package com.e.dashadmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dashadmin.ui.home.currentRides;
import com.e.dashadmin.ui.users.myAdapter;

import java.util.List;

public class currentRidesAdapter extends RecyclerView.Adapter<currentRidesAdapter.myViewHolder> {

    Context context;
    List<currentRides> currentRidesList;
    String Destination;

    public currentRidesAdapter(Context context, List<currentRides> currentRidesList, String destination) {
        this.context = context;
        this.currentRidesList = currentRidesList;
        Destination = destination;
    }

    @NonNull
    @Override
    public currentRidesAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_rides, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull currentRidesAdapter.myViewHolder holder, int position) {

        final currentRides current_rides = currentRidesList.get(position);
        final String Driver1Name = String.valueOf(current_rides.getDriverFirstName());
        final String Driver2Name = String.valueOf(current_rides.getDriverSecondName());
        final String Passenger1Name = String.valueOf(current_rides.getPassengerFirstName());
        final String Passenger2Name = String.valueOf(current_rides.getPassengerSecondName());
        final String Destination = String.valueOf(current_rides.getDestination());

        holder.destination.setText(Destination);
        holder.passengerSecondName.setText(Passenger2Name);
        holder.passengerFirstName.setText(Passenger1Name);
        holder.driverSecondName.setText(Driver2Name);
        holder.driverFirstName.setText(Driver1Name);



    }

    @Override
    public int getItemCount() {
        return currentRidesList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView driverFirstName, driverSecondName, passengerFirstName, passengerSecondName, destination;
        Button viewOnMap;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            driverFirstName = itemView.findViewById(R.id.tvDriverFirstName);
            driverSecondName = itemView.findViewById(R.id.tvDriverSecondName);
            passengerFirstName = itemView.findViewById(R.id.tvPassengerFirstName);
            passengerSecondName = itemView.findViewById(R.id.tvPassengerSecondName);
            destination = itemView.findViewById(R.id.tvDestination);

        }
    }

}
