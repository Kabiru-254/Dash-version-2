package com.e.dashadmin.ui.home;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dashadmin.R;
import com.e.dashadmin.currentRidesAdapter;
import com.e.dashadmin.ui.users.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    RecyclerView currentRidesRecycler;
    FirebaseAuth mAuth;
    String Destination;
    DatabaseReference currentRides, destinationDBRef;
    final List<currentRides> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        currentRidesRecycler = root.findViewById(R.id.ridesRecycler);
        currentRidesRecycler.setHasFixedSize(true);
        currentRidesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth = FirebaseAuth.getInstance();

        destinationDBRef = FirebaseDatabase.getInstance().getReference().child("Available Rides");
        destinationDBRef.keepSynced(true);
        destinationDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    Map<String, Object> map = (Map <String, Object>) snapshot.getValue();
                    if (map.get("Destination")!=null)
                    {
                      Destination =  map.get("Destination").toString();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        final RecyclerView.Adapter[] currentAdapter = new RecyclerView.Adapter[1];

        if (!list.isEmpty())
        {
            list.clear();
        }

        currentRides = FirebaseDatabase.getInstance().getReference().child("Users");
        currentRides.keepSynced(true);
        currentRides.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        currentRides currentRides = dataSnapshot.getValue(com.e.dashadmin.ui.home.currentRides.class);
                        list.add(currentRides);
                    }

                    currentAdapter[0] = new currentRidesAdapter(getContext(), list, Destination);
                    currentRidesRecycler.setAdapter(currentAdapter[0]);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        return root;
    }
}