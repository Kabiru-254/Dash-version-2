package com.e.dash.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dash.R;
import com.e.dash.ui.home.myAdapter;

import java.util.List;

public class myHistoryAdapter extends RecyclerView.Adapter<myHistoryAdapter.myViewHolder> {
    Context context;
    List<Ride_History> historyList;

    public myHistoryAdapter(Context context, List<Ride_History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }



    @NonNull
    @Override
    public myHistoryAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycler, parent, false);

        return new myHistoryAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHistoryAdapter.myViewHolder holder, int position) {

        final Ride_History ride_history = historyList.get(position);
        final String PassengerFirstName = String.valueOf(ride_history.getPassengerFirstName());
        final String PassengerSecondName = String.valueOf(ride_history.getPassengerSecondName());
        final String DriverFirstName = String.valueOf(ride_history.getDriverFirstName());
        final String DriverSecondName = String.valueOf(ride_history.getDriverSecondName());
        final String Destination = String.valueOf(ride_history.getDestination());
        final String fare = String.valueOf(ride_history.getFare());
        final String Rating = String.valueOf(ride_history.getRating());
        final String Comment = String.valueOf(ride_history.getComment());

        final float ratingFloat = Float.parseFloat(Rating);

        holder.driverFirstName.setText(DriverFirstName);
        holder.driverSecondName.setText(DriverSecondName);
        holder.passengerFirstName.setText(PassengerFirstName);
        holder.passengerSecondName.setText(PassengerSecondName);
        holder.destination.setText(Destination);
        holder.fare.setText(fare);
        holder.comment.setText(Comment);
        holder.ratingBar.setRating(ratingFloat);



    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView driverFirstName, driverSecondName, passengerFirstName, passengerSecondName, destination, fare, comment;
        RatingBar ratingBar;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            driverFirstName = itemView.findViewById(R.id.tvDriverFirstName);
            driverSecondName = itemView.findViewById(R.id.tvDriverSecondName);
            passengerFirstName = itemView.findViewById(R.id.tvPassFirstName);
            passengerSecondName = itemView.findViewById(R.id.tvPassSecondName);
            destination = itemView.findViewById(R.id.tvDestinationHistory);
            fare = itemView.findViewById(R.id.tvAmountOfFare);
            comment = itemView.findViewById(R.id.tvPassComment);
            ratingBar = itemView.findViewById(R.id.ratingBar);


        }
    }

}
