package com.e.dash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Objects;


public class Registration extends AppCompatActivity {


Button Submit;
EditText Email, Number, password, firstName, secondName;
CountryCodePicker countryCodePicker;
String Email_s, Number_s, ccp, PhoneNumber, password_s, FirstName_s, SecondName_s, UserID;
ProgressBar progressBar;
TextView tvLogin;
private FirebaseAuth mfirebaseAuth;
private DatabaseReference usersDBRef, UserIDdbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Submit = findViewById(R.id.btnSubmitReg);
        Email = findViewById(R.id.etEmail);
        Number = findViewById(R.id.etPhoneNumber);
        countryCodePicker = findViewById(R.id.ccp);
        progressBar = findViewById(R.id.progressBarReg);
        password = findViewById(R.id.etPassword);
        tvLogin = findViewById(R.id.tvLogin);
        firstName = findViewById(R.id.etFName);
        secondName = findViewById(R.id.etSecondName);



        mfirebaseAuth = FirebaseAuth.getInstance();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyDetails();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });

    }

    private void verifyDetails()
    {

       Email_s = Email.getText().toString().trim();
       Number_s = Number.getText().toString().trim();
       password_s =password.getText().toString().trim();
       FirstName_s = firstName.getText().toString().trim();
       SecondName_s = secondName.getText().toString().trim();
       ccp = countryCodePicker.getSelectedCountryCode();


        if (Number_s.length()==10)
       { Number_s = Number_s.substring(1); }

       PhoneNumber = "+" +ccp +Number_s;

       if (Email_s.isEmpty())
       {
           Email.setError("Enter Email");
           Email.requestFocus();
       }
      /* else if (Number_s.isEmpty())
       {
           Number.setError("Enter Valid Number");
           Number.requestFocus();

       }*/else if (FirstName_s.isEmpty())
       {
           firstName.setError("Enter your First Name");
           firstName.requestFocus();
       }else if (SecondName_s.isEmpty())
       {
           secondName.setError("Enter your second Name");
           secondName.requestFocus();
       }else if (password_s.length()<8)
       {
           password.setError("Enter at least 8 characters");
           password.requestFocus();
       }
       else
       {
           progressBar.setVisibility(View.VISIBLE);

           usersDBRef = FirebaseDatabase.getInstance().getReference().child("Users");
          final User user = new User(FirstName_s, SecondName_s, Email_s, PhoneNumber, UserID );
           mfirebaseAuth.createUserWithEmailAndPassword(Email_s, password_s)
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {

                           progressBar.setVisibility(View.GONE);
                           Toast.makeText(Registration.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           UserID = mfirebaseAuth.getCurrentUser().getUid();
                          String userID = Objects.requireNonNull(mfirebaseAuth.getCurrentUser()).getUid();
                          usersDBRef.child(userID).setValue(user)
                                  .addOnFailureListener(new OnFailureListener() {
                                      @Override
                                      public void onFailure(@NonNull Exception e) {
                                          progressBar.setVisibility(View.GONE);
                                          Toast.makeText(Registration.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                      }
                                  }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                              public void onSuccess(Void aVoid) {
                                          FirebaseUser user = mfirebaseAuth.getCurrentUser();
                                          user.sendEmailVerification()
                                                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                      @Override
                                                      public void onSuccess(Void aVoid) {
                                                          Toast.makeText(Registration.this, "Email verification sent", Toast.LENGTH_SHORT).show();
                                                      }
                                                  }).addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                                  Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                              }
                                          });

                                          progressBar.setVisibility(View.GONE);
                                  Intent intent = new Intent(Registration.this, MainActivity.class);
                                  startActivity(intent);
                                  finish();
                              }
                          });

                       }
                   });
       }


    }


}
