package com.example.myapplication;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ViewDeliveryDetails extends AppCompatActivity {

    EditText txtName,txtMobile, txtEmail, txtAddress1,txtAddress2,txtAddress3,txtDate;
    Button  update,delete,payment,show;
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    Delivery details;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_delivery_details);

        final String TAG = "ViewDeliveryDetails";

        txtName = findViewById(R.id.etName);
        txtMobile = findViewById(R.id.etConNo);
        txtEmail = findViewById(R.id.etEmail);
        txtAddress1 = findViewById(R.id.etAd1);
        txtAddress2 = findViewById(R.id.etAd2);
        txtAddress3 = findViewById(R.id.etAd3);
        txtDate = findViewById(R.id.etDate);

        update = findViewById(R.id.button3);
        delete = findViewById(R.id.button4);
        payment = findViewById(R.id.button5);
        show = findViewById(R.id.button2);

        details = new Delivery();

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ViewDeliveryDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListner, year, month, day);
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
                txtDate.setText(Date);

            }
        };

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference  ReadRef = FirebaseDatabase.getInstance().getReference().child("Delivery").child("details1");
                ReadRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtMobile.setText(dataSnapshot.child("mobile").getValue().toString());
                            txtEmail.setText(dataSnapshot.child("email").getValue().toString());
                            txtAddress1.setText(dataSnapshot.child("address1").getValue().toString());
                            txtAddress2.setText(dataSnapshot.child("address2").getValue().toString());
                            txtAddress3.setText(dataSnapshot.child("city").getValue().toString());
                            txtDate.setText(dataSnapshot.child("date").getValue().toString());

                        }
                        else
                            Toast.makeText(getApplicationContext(), "No source to display!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("Delivery");
                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("details1"))
                        {
                            try{

                                String emailInput = txtEmail.getText().toString();

                                if (TextUtils.isEmpty(txtName.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(txtMobile.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                                else if(txtMobile.length()<10)
                                    txtMobile.setError("Your contact number is invalid");
                                else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                                else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
                                    txtEmail.setError("Please enter valid email address");
                                else if (TextUtils.isEmpty(txtAddress1.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please enter your address1", Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(txtAddress2.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please enter your address2", Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(txtAddress3.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please enter your address3", Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(txtDate.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please enter your date", Toast.LENGTH_SHORT).show();
                                else{

                                    details.setName(txtName.getText().toString().trim());
                                    details.setMobile(txtMobile.getText().toString().trim());
                                    details.setEmail(txtEmail.getText().toString().trim());
                                    details.setAddress1(txtAddress1.getText().toString().trim());
                                    details.setAddress2(txtAddress2.getText().toString().trim());
                                    details.setCity(txtAddress3.getText().toString().trim());
                                    details.setDate(txtDate.getText().toString().trim());

                                    ref = FirebaseDatabase.getInstance().getReference().child("Delivery").child("details1");
                                    ref.setValue(details);

                                    Toast.makeText(getApplicationContext(), "Data Updated Successfully!!!", Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){

                                Toast.makeText(getApplicationContext(), "Invalid!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{

                            Toast.makeText(getApplicationContext(), "No Source to Update!!!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference deleteRef = FirebaseDatabase.getInstance().getReference().child("Delivery");
                deleteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild("details1"))
                        {
                            ref = FirebaseDatabase.getInstance().getReference().child("Delivery").child("details1");
                            ref.removeValue();


                            Toast.makeText(getApplicationContext(), "Your Data Deleted Successfully!!!", Toast.LENGTH_SHORT).show();
                        }
                        else

                            Toast.makeText(getApplicationContext(), "No Source to Delete!!!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    String emailInput = txtEmail.getText().toString();

                    if (TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtMobile.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                    else if(txtMobile.length()<10)
                        txtMobile.setError("Your contact number is invalid");
                    else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
                        txtEmail.setError("Please enter valid email address");
                    else if (TextUtils.isEmpty(txtAddress1.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter your address1", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtAddress2.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter your address2", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtAddress3.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter your address3", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtAddress3.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter your date", Toast.LENGTH_SHORT).show();
                    else{

                        Intent intent = new Intent(ViewDeliveryDetails.this,DeliveryPayment.class);
                        startActivity(intent);
                        Toast.makeText(ViewDeliveryDetails.this, "Now You can see payment info!!", Toast.LENGTH_SHORT).show();

                    }

                }catch(Exception e){

                    Toast.makeText(getApplicationContext(), "Invalid!!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}