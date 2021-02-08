package com.e.dash.ui.Offer_Rides;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.e.dash.R;
import com.e.dash.offeredRides;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Offer_Rides extends Fragment {

    EditText etDestination, etCarNumberPlate, etDepartureTime, etNumberOfSeats, etFare;
    String Destination, CarNumberPlate, DepartureTime, NumberOfSeats, Fare, UserID;
    FloatingActionButton floatingActionButton;
    RadioButton today, tomorrow;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference ridesDBRef;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.offer_rides, container, false);
        mAuth = FirebaseAuth.getInstance();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();



        etDestination = root.findViewById(R.id.etDestination);
        etCarNumberPlate = root.findViewById(R.id.etNumberPlate);
        etDepartureTime = root.findViewById(R.id.etDepartureTime);
        etNumberOfSeats = root.findViewById(R.id.etNumberOfSeats);
        etFare = root.findViewById(R.id.etFare);
        floatingActionButton = root.findViewById(R.id.fbPostRides);
        today = root.findViewById(R.id.rbToday);
        tomorrow = root.findViewById(R.id.rbTomorrow);
        progressBar = root.findViewById(R.id.progressBar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Destination = etDestination.getText().toString().trim();
                CarNumberPlate = etCarNumberPlate.getText().toString().trim();
                DepartureTime = etDepartureTime.getText().toString().trim();
                NumberOfSeats = etNumberOfSeats.getText().toString().trim();
                Fare = etFare.getText().toString().trim();

                if ( Destination.isEmpty() || CarNumberPlate.isEmpty() || DepartureTime.isEmpty() || NumberOfSeats.isEmpty() || Fare.isEmpty() )
                {
                    Toast.makeText(getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }else
                {
                    addRideToDatabase();
                    Intent intent = new Intent(getContext(), offeredRides.class);
                    startActivity(intent);

                }

            }
        });

        return root;


    }


    private void addRideToDatabase() {

        progressBar.setVisibility(View.VISIBLE);

        ridesDBRef = FirebaseDatabase.getInstance().getReference().child("Available Rides").child(UserID);
        HashMap map = new HashMap();
        map.put("Destination", Destination);
        map.put("CarNumberPlate",CarNumberPlate);
        map.put("Departure_Time", DepartureTime);
        map.put("Number_of_Seats", NumberOfSeats);
        map.put("Fare", Fare);
        map.put("DriverID", UserID);
        ridesDBRef.updateChildren(map)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Update Successful", Toast.LENGTH_SHORT).show();


            }
        });


    }

}