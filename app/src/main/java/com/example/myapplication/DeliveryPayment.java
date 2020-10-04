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
import com.google.firebase.database.ValueEventListener;

public class DeliveryPayment extends AppCompatActivity {

    EditText txtPin,txtDest,txtKm;
    Button save;
    DatabaseReference ref;
    Payment payment;
    String userPin,userDestination,userKm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_payment);

        txtPin = findViewById(R.id.pinNo);
        txtDest = findViewById(R.id.destination);
        txtKm = findViewById(R.id.km);

        save = findViewById(R.id.button6);

        payment = new Payment();

        ref = FirebaseDatabase.getInstance().getReference("Payment");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData(){

        ref = FirebaseDatabase.getInstance().getReference().child("Payment");

        try {


            if(TextUtils.isEmpty(txtPin.getText().toString())) {
                txtPin.setError("Please Enter your PIN number!!");
                txtPin.requestFocus();
            }else if(TextUtils.isEmpty(txtDest.getText().toString())) {
                txtDest.setError("Please Enter Your Destination!!");
                txtDest.requestFocus();
            } else if(TextUtils.isEmpty(txtKm.getText().toString())) {
                txtKm.setError("Please Enter How many KM you have!!");
                txtKm.requestFocus();
            }else{
                payment.setPin(txtPin.getText().toString().trim());
                payment.setDestination(txtDest.getText().toString().trim());
                payment.setKm(txtKm.getText().toString().trim());

                //ref.push().setValue(details);
                ref.child("payment1").setValue(payment);

                Intent intent = new Intent(DeliveryPayment.this,ViewDeliveryPayment.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Data Entered Successfully !!! Please click on SHOW MY DATA button to continue...", Toast.LENGTH_LONG).show();
            }

        }catch(Exception e){

            Toast.makeText(getApplicationContext(), "Invalid!!!", Toast.LENGTH_LONG).show();
        }

    }
}