package com.e.dash.ui.home;

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

import com.e.dash.MainActivity;
import com.e.dash.Payments;
import com.e.dash.R;
import com.e.dash.Ride_Details;
import com.e.dash.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder>
{

    DatabaseReference rideStatusRef, driverDBRef, driverIDDBRef;
    Context context;
    List<Ride_Details> classItem;
    String UserID, DriverID;

    public myAdapter(Context context, List<Ride_Details> TempList, String UserID)
    {
        this.classItem = TempList;
        this.context = context;
        this.UserID = UserID;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rides_cardview, parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {

        final Ride_Details ride_details = classItem.get(position);
        final String CarNumberPlate = String.valueOf(ride_details.getCarNumberPlate());
        final String Departure_Time = String.valueOf(ride_details.getDeparture_Time());
        final String Fare = String.valueOf(ride_details.getFare());
        final String Destination = String.valueOf(ride_details.getDestination());
        final String Number_of_Seats = String.valueOf(ride_details.getNumber_of_Seats());
        final String DriverID = String.valueOf(ride_details.getDriverID());

        driverDBRef = FirebaseDatabase.getInstance().getReference().child("Users").child(DriverID);
        driverDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Map <String, Object> map = (Map <String, Object>) snapshot.getValue();
                    if (map.get("firstName")!=null)
                    {
                        String DriverFirstName = map.get("firstName").toString();

                        String Ride = DriverFirstName + " of car "+CarNumberPlate +" is leaving for "+Destination + " at "
                                +Departure_Time + ". The fare is Ksh " +Fare +" with " +Number_of_Seats +" seats remaining.";
                        holder.rideDetails.setText(Ride);
                        holder.requestRide.setText("Request this ride");

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });





            driverIDDBRef = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);
            driverIDDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        Map<String, Object> map = (Map <String, Object>) snapshot.getValue();
                        if(map.get("DriverID")!=null)
                        {
                            String DriverID = map.get("DriverID").toString();

                            rideStatusRef = FirebaseDatabase.getInstance().getReference().child("Available Rides").child(DriverID)
                                    .child("Interested Passengers");
                            rideStatusRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists())
                                    {
                                        Map <String, Object> map = (Map <String, Object>) snapshot.getValue();
                                        if (map.get("Ride Status")!=null)
                                        {
                                            String RideStatus = map.get("Ride Status").toString();
                                            holder.requestRide.setText(RideStatus);

                                        }
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }

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

              holder.requestRide.setText("Waiting for approval");

              rideStatusRef = FirebaseDatabase.getInstance().getReference().child("Available Rides").child(DriverID).child("Interested Passengers");
              HashMap map = new HashMap();
              map.put("PassengerID", UserID);
             // map.put("Ride Status", "Pending Approval");
              rideStatusRef.updateChildren(map);

              Intent intent = new Intent (context, Payments.class);
              intent.putExtra("Fare", Fare);
              holder.requestRide.getContext().startActivity(intent);


          }
      });

    }

    @Override
    public int getItemCount() {
        return classItem.size();
    }



    public static class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView rideDetails;
        Button requestRide;

        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);

            rideDetails = itemView.findViewById(R.id.tvRideDetails);
            requestRide = itemView.findViewById(R.id.btnRequestRide);

        }

    }

}
