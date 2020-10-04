package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ViewDeliveryPayment extends AppCompatActivity {

    EditText pin, destination, km, etEnterPin;
    Button btnEdit, btnNext, btnShow;
    DatabaseReference dbRef;
    String PinNumber;
    String userPin, userDestination, userKm;
    Payment payment;

    // Map<String, Object> updateMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_delivery_payment);

        pin = findViewById(R.id.etPin);
        destination = findViewById(R.id.etDest);
        km = findViewById(R.id.etKM);
        //etEnterPin = findViewById(R.id.etEnter);

        btnShow = findViewById(R.id.button9);
        btnNext = findViewById(R.id.button8);
        btnEdit = findViewById(R.id.button7);

        payment = new Payment();

        dbRef = FirebaseDatabase.getInstance().getReference("Payment");





        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ReadRef = FirebaseDatabase.getInstance().getReference().child("Payment").child("payment1");

                ReadRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChildren()) {
                            pin.setText(dataSnapshot.child("pin").getValue().toString());
                            destination.setText(dataSnapshot.child("destination").getValue().toString());
                            km.setText(dataSnapshot.child("km").getValue().toString());
                        } else
                            Toast.makeText(getApplicationContext(), "No source to display!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("Payment");

                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild("payment1")) {

                            try {

                                if (TextUtils.isEmpty(pin.getText().toString())) {
                                    pin.setError("Please Enter your PIN number!!");
                                    pin.requestFocus();
                                } else if (TextUtils.isEmpty(destination.getText().toString())) {
                                    destination.setError("Please Enter Your Destination!!");
                                    destination.requestFocus();
                                } else if (TextUtils.isEmpty(km.getText().toString())) {
                                    km.setError("Please Enter How many KM you have!!");
                                    km.requestFocus();
                                } else {

                                    payment.setPin(pin.getText().toString().trim());
                                    payment.setDestination(destination.getText().toString().trim());
                                    payment.setKm(km.getText().toString().trim());

                                    dbRef = FirebaseDatabase.getInstance().getReference().child("Payment").child("payment1");
                                    dbRef.setValue(payment);

                                    Toast.makeText(getApplicationContext(), "Data Updated Successfully!!!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {

                                Toast.makeText(getApplicationContext(), "Invalid!!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "No Source to Update!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String st = km.getText().toString();

                if (TextUtils.isEmpty(pin.getText().toString()))
                    pin.setError("Please Enter your PIN number!!");
                else if (TextUtils.isEmpty(destination.getText().toString()))
                    destination.setError("Please Enter Your Destination!!");
                else if (TextUtils.isEmpty(km.getText().toString()))
                    km.setError("Please Enter How many KM you have!!");
                else {

                    Toast.makeText(ViewDeliveryPayment.this, "Now You Can See Your Total Charge !!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ViewDeliveryPayment.this, TotalDeliveryCharge.class);
                    intent.putExtra("st", st);
                    startActivity(intent);

                }

            }
        });

    }
}

