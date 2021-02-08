package com.e.dash.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dash.R;
import com.e.dash.Ride_Details;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.media.CamcorderProfile.get;
import static com.e.dash.R.id.text_home;


public class HomeFragment extends Fragment{

    RecyclerView RidesRecyclerView;

    String UserID;

    private DatabaseReference ridesDB, rideStatusRef;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();



        final List<Ride_Details> list = new ArrayList<>();

        RidesRecyclerView = root.findViewById(R.id.ridesRecycler);
        RidesRecyclerView.setHasFixedSize(true);
        RidesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (!list.isEmpty())
        {
            list.clear();
        }

        //RecyclerView.Adapter mAdapter;
        final RecyclerView.Adapter[] mAdapter = new RecyclerView.Adapter[1];
        ridesDB = FirebaseDatabase.getInstance().getReference().child("Available Rides");
        ridesDB.keepSynced(true);
        ridesDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        Ride_Details classItem = dataSnapshot.getValue(Ride_Details.class);
                        list.add(classItem);
                    }

                    /*mAdapter = new myAdapter(getContext(), list);
                    RidesRecyclerView.setAdapter(mAdapter);*/

                  mAdapter[0] = new myAdapter(getContext(), list, UserID);
                    RidesRecyclerView.setAdapter(mAdapter[0]);
                }else {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        return root;

    }




    private int getText_home() {
        return text_home;
    }



}



