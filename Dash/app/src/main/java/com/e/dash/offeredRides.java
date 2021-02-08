package com.e.dash;

import android.Manifest;
import  android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dash.ui.Offer_Rides.passAdapter;
import com.e.dash.ui.Ride_History;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class offeredRides extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference usersRef, ridesDBRef, PassengerUserIDDBRef, passengerDetailsDBRef, driverDetailsDBRef, userCommentsDBRef, historyDBRef, rideIDDBRef, rideID;
    String UserID, passengerID, Passenger_FirstName, Passenger_SecondName, Passenger_PhoneNumber, Passenger_Email, Driver_FirstName, Driver_SecondName, Driver_PhoneNumber, Driver_Email, RideDestination, RideFare, user_comments;
    Button updateRide, startRide;
    TextView title;
    RecyclerView requestedRideRecycler;
    String ride_rating, RideID, truePassengerID;
    ImageView backArrow;


    final List<PassengerDetails> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offered_rides);

        mAuth = FirebaseAuth.getInstance();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ridesDBRef = FirebaseDatabase.getInstance().getReference().child("Available Rides").child(UserID);


        startRide = findViewById(R.id.btnStartRide);
        updateRide = findViewById(R.id.btnUpdateRide);
        title = findViewById(R.id.tvTitle);
        backArrow = findViewById(R.id.ivBackArrowOR);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(offeredRides.this, MainActivity.class);
                startActivity(intent);
            }
        });



        requestedRideRecycler = findViewById(R.id.requestedRideRecycler);
        requestedRideRecycler.setHasFixedSize(true);
        requestedRideRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        if (!list.isEmpty())
        {
            list.clear();
        }


        updateRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ridesDBRef.removeValue();
                Toast.makeText(offeredRides.this, "Please navigate to Offer Rides in the navigation drawer to offer rides.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        startRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startRide.setText("End Ride");

                startRide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //saveRideDetails();
                        ridesDBRef.removeValue();
                        Intent intent = new Intent(offeredRides.this, MainActivity.class);
                        startActivity(intent);

                    }
                });

            }
        });


        showRecycler();

    }

    private void saveRideDetails() {
            //Getting Passenger ID
        PassengerUserIDDBRef = FirebaseDatabase.getInstance().getReference().child("Available Rides").child(UserID).child("Interested Passengers");
        PassengerUserIDDBRef.keepSynced(true);
        PassengerUserIDDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Map<String, Object> map = (Map <String, Object>) snapshot.getValue();
                    if (map.get("PassengerID")!=null)
                    {
                        passengerID = map.get("PassengerID").toString();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(offeredRides.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Getting Passenger Details
            passengerDetailsDBRef = FirebaseDatabase.getInstance().getReference().child("Users").child(passengerID);
            passengerDetailsDBRef.keepSynced(true);
            passengerDetailsDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        Map<String, Object> map = (Map <String, Object>) snapshot.getValue();
                        if (map.get("firstName")!=null)
                        {
                            Passenger_FirstName = map.get("firstName").toString();
                        }
                        if (map.get("secondName")!=null)
                        {
                            Passenger_SecondName = map.get("secondName").toString();
                        }
                        if (map.get("phoneNumber")!=null)
                        {
                            Passenger_PhoneNumber = map.get("phoneNumber").toString();
                        }
                        if (map.get("email")!=null)
                        {
                            Passenger_Email = map.get("email").toString();
                        }



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(offeredRides.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

           //Getting Driver Details
        driverDetailsDBRef = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);
        driverDetailsDBRef.keepSynced(true);
        driverDetailsDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Map<String, Object> map = (Map <String, Object>) snapshot.getValue();
                    if (map.get("firstName")!=null)
                    {
                        Driver_FirstName = map.get("firstName").toString();
                    }
                    if (map.get("secondName")!=null)
                    {
                        Driver_SecondName = map.get("secondName").toString();
                    }
                    if (map.get("phoneNumber")!=null)
                    {
                        Driver_PhoneNumber = map.get("phoneNumber").toString();
                    }
                    if (map.get("email")!=null)
                    {
                        Driver_Email = map.get("email").toString();
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(offeredRides.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

            //Getting Ride details

        ridesDBRef.keepSynced(true);
        ridesDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Map<String, Object> map = (Map <String, Object>) snapshot.getValue();
                    if (map.get("Destination")!=null)
                    {
                        RideDestination = map.get("Destination").toString();
                    }
                    if (map.get("Fare")!=null)
                    {
                        RideFare = map.get("Fare").toString();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(offeredRides.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //getting passenger comments
        userCommentsDBRef =  FirebaseDatabase.getInstance().getReference().child("Ride Comments").child(passengerID);
        userCommentsDBRef.keepSynced(true);
        userCommentsDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Map<String, Object> map = (Map <String, Object>) snapshot.getValue();
                    if (map.get("Ride Comments")!=null)
                    {
                        user_comments = map.get("Ride Comments").toString();
                    }
                    if (map.get("RideRating")!=null)
                    {
                        ride_rating = String.valueOf((Float) map.get("RideRating"));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(offeredRides.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //saving to history
         rideIDDBRef = FirebaseDatabase.getInstance().getReference().child("History");
         historyDBRef = rideIDDBRef.push();
         RideID = rideIDDBRef.getKey();
         final Ride_History rideHistory = new Ride_History(Passenger_FirstName, Passenger_SecondName, Driver_FirstName, Driver_SecondName, RideDestination, RideFare, ride_rating, user_comments);
         historyDBRef.child(RideID).child(UserID).setValue(rideHistory)
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(offeredRides.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                     }
                 }).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {

                 Toast.makeText(offeredRides.this, "Ride saved to history!", Toast.LENGTH_SHORT).show();
                 rideID = FirebaseDatabase.getInstance().getReference().child("Rides").child(UserID);


             }
         });



    }

    public void showRecycler()
    {
        final RecyclerView.Adapter[] userAdapter = new RecyclerView.Adapter[1];

        usersRef = FirebaseDatabase.getInstance().getReference().child("Available Rides").child(UserID).child("Interested Passengers");
        usersRef.keepSynced(true);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    for (int i=0; i<1; i++)
                    {
                        title.setText("Passengers requesting your ride");
                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                        if (map.get("PassengerID")!=null)
                        {
                           truePassengerID = map.get("PassengerID").toString();
                        }

                        PassengerDetails passengerDetails = new PassengerDetails(truePassengerID);
                        list.add(passengerDetails);
                    }

                    userAdapter[0] = new passAdapter(getApplicationContext(),UserID, list);
                    requestedRideRecycler.setAdapter(userAdapter[0]);
                }else
                {
                    title.setText("No one has requested your ride yet");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(offeredRides.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}