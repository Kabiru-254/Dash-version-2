package com.e.dashadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView dashAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        dashAdmin = findViewById(R.id.tvDashAdmin);

        dashAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
            }
        });


        new Thread() {
            public void run() {
                try {
                    sleep(5 * 1000);

                    Intent goToLogin = new Intent(SplashScreen.this, Login.class);
                    startActivity(goToLogin);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}