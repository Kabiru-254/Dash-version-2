package com.e.dash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.e.dash.R.layout.activity_splash_screen;

public class SplashScreen extends AppCompatActivity {

    DatabaseReference checkRide;
    String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_splash_screen);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        UserID = FirebaseAuth.getInstance().getUid();


        final FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                Thread background;
                if (firebaseUser == null) {
                    background = new Thread() {
                        public void run() {
                            try {
                                sleep(5 * 1000);

                                Intent goToLogin = new Intent(getBaseContext(), Location.class);
                                startActivity(goToLogin);
                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    };

                } else {
                    background = new Thread() {
                        public void run() {
                            try {
                                sleep(5 * 1000);

                                checkIfOfferedRide();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    };

                }
                background.start();
            }
        };

        authStateListener.onAuthStateChanged(mAuth);



    }

    public void checkIfOfferedRide()
    {
        checkRide = FirebaseDatabase.getInstance().getReference().child("Available Rides").child(UserID).child("DriverID");
        checkRide.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    Intent intent = new Intent(SplashScreen.this, offeredRides.class);
                    startActivity(intent);
                    finish();

                }else
                {
                    Intent goToLogin = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(goToLogin);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(SplashScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
