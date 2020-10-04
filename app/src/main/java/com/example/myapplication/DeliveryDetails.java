package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DeliveryDetails extends AppCompatActivity {

    EditText name, conNo, email, ad1, ad2, ad3, date;

    private DatePickerDialog.OnDateSetListener mDateSetListner;

    Button add;
    DatabaseReference ref;
    Delivery details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);


        final String TAG = "DeliveryDetails";


        name = findViewById(R.id.txtName);
        conNo = findViewById(R.id.txtMobile);
        email = findViewById(R.id.txtEmail);
        ad1 = findViewById(R.id.txtAddress1);
        ad2 = findViewById(R.id.txtAddress2);
        ad3 = findViewById(R.id.txtAddress3);
        date = findViewById(R.id.txtDate);

        add = findViewById(R.id.button);
        details = new Delivery();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DeliveryDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListner, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String Date = month + "/" + day + "/" + year;
                date.setText(Date);

            }
        };


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saveData();

                //  Intent intent = new Intent(MainActivity.this,ViewDeliveryDetails.class);
                //  startActivity(intent);

            }
        });
    }



    private void saveData() {

        ref = FirebaseDatabase.getInstance().getReference().child("Delivery");

        try {

            String emailInput = email.getText().toString();

            if (TextUtils.isEmpty(name.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(conNo.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            else if(conNo.length()<10)
                conNo.setError("Your contact number is invalid");
            else if (TextUtils.isEmpty(email.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
            else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
                email.setError("Please enter valid email address");
            else if (TextUtils.isEmpty(ad1.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter your address1", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(ad2.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter your address2", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(ad3.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter your address3", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(date.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter your date", Toast.LENGTH_SHORT).show();
            else {

                //  String details1 = ref.push().getKey();
                details.setName(name.getText().toString().trim());
                details.setMobile(conNo.getText().toString().trim());
                details.setEmail(email.getText().toString().trim());
                details.setAddress1(ad1.getText().toString().trim());
                details.setAddress2(ad2.getText().toString().trim());
                details.setCity(ad3.getText().toString().trim());
                details.setDate(date.getText().toString().trim());

                //ref.push().setValue(details);
                // ref.child(details1).setValue(details);
                ref.child("details1").setValue(details);

                Intent intent = new Intent(DeliveryDetails.this, ViewDeliveryDetails.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Data Entered Successfully !!! Please click on SHOW MY DETAILS button to continue...", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Invalid!!!", Toast.LENGTH_LONG).show();
        }

    }


}