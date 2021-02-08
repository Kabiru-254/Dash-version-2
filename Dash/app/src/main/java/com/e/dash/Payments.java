package com.e.dash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.interfaces.MpesaListener;
import com.bdhobare.mpesa.models.STKPush;
import com.bdhobare.mpesa.utils.Pair;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Payments extends AppCompatActivity implements AuthListener, MpesaListener {

    FirebaseAuth mAuth;
    DatabaseReference rideCommentsDBRef, phoneNumberDBRef, NameRef;
    String UserID, userComment, phoneNumber, Fare_S;
    ImageView backArrow;
    TextView PassengerName, PhoneNumber, Fare, payBill;
    Button makePayment, endRide;
    RatingBar ratingBar;
    EditText passengerComment;
    String rating, firstName;
    EditText UserPhoneNumber;

    ProgressDialog dialog;



    public static final String BUSINESS_SHORT_CODE = "174379";
    public static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String CONSUMER_KEY = "VdzWY56BdUhnhw1kV0RLxBxgVV9n0itU";
    public static final String CONSUMER_SECRET = "xENrbm0D3GwVPrgI";
    public static final String CALLBACK_URL = "https://requestbin.herokuapp.com/rl7frfrl";

    public static final String  NOTIFICATION = "PushNotification";
    public static final String SHARED_PREFERENCES = "com.bdhobare.mpesa_android_sdk";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        UserPhoneNumber = findViewById(R.id.etPassengerPhoneNumberPay);


        init();

        Mpesa.with(this, CONSUMER_KEY, CONSUMER_SECRET);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing");
        dialog.setIndeterminate(true);




        NameRef = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);
        NameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Map<String, Object> map = (Map <String, Object>) snapshot.getValue();
                    if (map.get("firstName")!=null)
                    {
                        firstName = map.get("firstName").toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Fare_S = getIntent().getStringExtra("Fare");
        Fare.setText(Fare_S);



        endRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRideDetails();
                Intent intent = new Intent(Payments.this, MainActivity.class);
                startActivity(intent);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payments.this, MainActivity.class);
                startActivity(intent);

            }
        });

        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = UserPhoneNumber.getText().toString().trim();

                if (phone.isEmpty())
                {
                    UserPhoneNumber.setError("Enter your phone number");
                    UserPhoneNumber.requestFocus();

                }
                else {
                    pay(phone, Integer.parseInt(Fare_S));
                }



            }
        });

        rating = String.valueOf(ratingBar.getRating());
        passengerComment = findViewById(R.id.etPassComment);


    }

    private void pay(String phone, int amount){
        dialog.show();
        STKPush.Builder builder = new STKPush.Builder(BUSINESS_SHORT_CODE, PASSKEY, amount,BUSINESS_SHORT_CODE, phone);
        STKPush push = builder.build();
        Mpesa.getInstance().pay(this, push);

    }

    /*private void showDialog(String title, String message,int code){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.success_dialog, true)
                .positiveText("OK")
                .cancelable(false)
                .widgetColorRes(R.color.colorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        finish();
                    }
                })
                .build();
        View view=dialog.getCustomView();
        TextView messageText = (TextView)view.findViewById(R.id.message);
        ImageView imageView = (ImageView)view.findViewById(R.id.success);
        if (code != 0){
            imageView.setVisibility(View.GONE);
        }
        messageText.setText(message);
        dialog.show();
    }*/

    private void saveRideDetails() {

        //save passenger comments
        rideCommentsDBRef = FirebaseDatabase.getInstance().getReference().child("Ride Comments").child(UserID);
        rideCommentsDBRef.keepSynced(true);
        HashMap map = new HashMap();
        map.put("Ride Comments", userComment);
        map.put("RideRating", rating);
        rideCommentsDBRef.updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(Payments.this, "Ride details saved!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Payments.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void init()
    {
        mAuth = FirebaseAuth.getInstance();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        backArrow = findViewById(R.id.ivBackArrow);
        Fare = findViewById(R.id.tvFare);
        payBill = findViewById(R.id.tvPayBill);
        makePayment = findViewById(R.id.btnMakePayment);
        endRide = findViewById(R.id.btnEndRide);
        ratingBar = findViewById(R.id.ratingBar);
        passengerComment = findViewById(R.id.etPassComment);

    }

    @Override
    public void onAuthError(Pair<Integer, String> result) {
        Log.e("Error", result.message);
    }

    @Override
    public void onAuthSuccess() {
        makePayment.setEnabled(true);
    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {

        dialog.hide();
        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {
        dialog.hide();
        Toast.makeText(this, CustomerMessage, Toast.LENGTH_SHORT).show();

    }
}
