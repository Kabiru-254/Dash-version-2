package com.e.dashadmin.ui.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dashadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class manageUsers extends Fragment {

    private GalleryViewModel galleryViewModel;

    RecyclerView rideRecycler;
    final List<Users> list = new ArrayList<>();
    DatabaseReference usersDBRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.manage_users, container, false);

        rideRecycler = root.findViewById(R.id.ridesRecycler);
        rideRecycler.setHasFixedSize(true);
        rideRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerView.Adapter[] userAdapter = new RecyclerView.Adapter[1];

        if (!list.isEmpty())
        {
            list.clear();
        }

        usersDBRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersDBRef.keepSynced(true);
        usersDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        Users users = dataSnapshot.getValue(Users.class);
                        list.add(users);
                    }

                    userAdapter[0] =new myAdapter(getContext(), list);
                    rideRecycler.setAdapter(userAdapter[0]);
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