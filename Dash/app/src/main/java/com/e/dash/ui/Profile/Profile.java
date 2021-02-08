package com.e.dash.ui.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.e.dash.Login;
import com.e.dash.MainActivity;
import com.e.dash.Payments;
import com.e.dash.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Profile extends Fragment implements View.OnClickListener{

    EditText fName, oName, Email, phoneNumber;
    Button updateProfile, logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.profile, container, false);

        fName = root.findViewById(R.id.profFnameET);
        oName = root.findViewById(R.id.profOnamesET);
        Email = root.findViewById(R.id.profEmailET);
        phoneNumber = root.findViewById(R.id.profPhoneET);
        updateProfile = root.findViewById(R.id.profileUpdateBT);
        logout = root.findViewById(R.id.profileLogout);

        updateProfile.setOnClickListener(this);
        logout.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        int ID = v.getId();

        if (ID == R.id.profileUpdateBT)
        {
            Toast.makeText(getContext(),"Updating profile", Toast.LENGTH_LONG).show();

            requireActivity().finish();

        }else if (ID == R.id.profileLogout)
        {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(),"Logging out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent (getContext(), Login.class);
            startActivity(intent);
            requireActivity().finish();
        }

    }
}