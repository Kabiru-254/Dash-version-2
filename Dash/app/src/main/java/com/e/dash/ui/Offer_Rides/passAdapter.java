package com.e.dash.ui.Offer_Rides;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dash.PassengerDetails;
import com.e.dash.Payments;
import com.e.dash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class passAdapter extends RecyclerView.Adapter<passAdapter.mySecondViewHolder> {
    Context context;
    String UserID, firstName, secondName, email, phoneNumber;
    List<PassengerDetails> passengerDetailsList;
    DatabaseReference passengerDetailsDBRef, updateRideStatusDBRef, UserReferencePass;

    public passAdapter(Context context, String userID, List<PassengerDetails> passengerDetailsList) {
        this.context = context;
        this.UserID = userID;
        this.passengerDetailsList = passengerDetailsList;
    }

    @NonNull
    @Override
    public passAdapter.mySecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rides_cardview, parent, false);
        return  new mySecondViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final passAdapter.mySecondViewHolder holder, int position) {

        final PassengerDetails passengerDetails = passengerDetailsList.get(position);
        final String PassengerId = passengerDetails.getPassengerID();

            passengerDetailsDBRef  = FirebaseDatabase.getInstance().getReference().child("Users").child(PassengerId);
            passengerDetailsDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        Map<String, Object> map = (Map <String, Object>) snapshot.getValue();

                        if (map.get("firstName")!=null)
                        {
                            firstName = map.get("firstName").toString();
                        }
                        if (map.get("secondName")!=null)
                        {
                            secondName = map.get("secondName").toString();
                        }
                        if (map.get("email")!=null)
                        {
                            email = map.get("email").toString();
                        }
                        if (map.get("phoneNumber")!=null)
                        {
                            phoneNumber = map.get("phoneNumber").toString();
                        }

                        String passenger = firstName+ " " +secondName+ " is requesting your ride. "+ "Phone Number: "+phoneNumber +" email: "+email;
                        holder.rideDetails.setText(passenger);
                        holder.requestRide.setText("Offer ride");


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });






        holder.requestRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.requestRide.setText("Approved");

                UserReferencePass = FirebaseDatabase.getInstance().getReference().child("Users").child(PassengerId);
                HashMap map = new HashMap();
                map.put("DriverID", UserID);
                UserReferencePass.updateChildren(map);

            }
        });


    }

    @Override
    public int getItemCount() {
        return passengerDetailsList.size();
    }

    public static class mySecondViewHolder extends RecyclerView.ViewHolder
    {
        TextView rideDetails;
        Button requestRide;

        public mySecondViewHolder(@NonNull View itemView) {
            super(itemView);

            rideDetails = itemView.findViewById(R.id.tvRideDetails);
            requestRide = itemView.findViewById(R.id.btnRequestRide);
        }
    }

}
