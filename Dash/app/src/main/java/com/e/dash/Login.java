package com.e.dash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etMail, etPassword;
    Button Submit;
    TextView tvRegister, forgotPassword;
    ProgressBar progressBar;
    String Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPasswordLogin);
        Submit = findViewById(R.id.btnSubmitCode);
        tvRegister = findViewById(R.id.tvRegister);
        progressBar = findViewById(R.id.progressBarLogin);
        forgotPassword = findViewById(R.id.tvForgotPassword);


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Registration.class);
                startActivity(intent);

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.sendPasswordResetEmail(Email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "A reset code has been sent to your Email address", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Email = etMail.getText().toString().toLowerCase().trim();
            Password = etPassword.getText().toString().trim();

            if (Email.isEmpty() )
            {
                etMail.setError("Enter Email");
                etMail.requestFocus();
            }else if (Password.isEmpty())
            {
                etPassword.setError("Enter Password");
                etPassword.requestFocus();
            }else
            {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);

                    }
                });


            }

            }
        });

    }


}

