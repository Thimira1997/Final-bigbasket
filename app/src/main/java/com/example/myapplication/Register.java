package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {

    EditText edtfname,edtlname,edtemail,edtpass,edtcompass;
    Button btnRegister;
    DatabaseReference dbRef;
    RegisterDB std;
    public Button button;
    long maxid=0;

    public void clearControls(){
        edtfname.setText("");
        edtlname.setText("");
        edtemail.setText("");
        edtpass.setText("");
        edtcompass.setText("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView txtVMsg2 = findViewById(R.id.textView2);
        txtVMsg2.setText(R.string.title1);

        edtfname=(EditText)findViewById(R.id.editTextTextPersonName);
        edtlname=(EditText)findViewById(R.id.editTextTextPersonName2);
        edtemail=(EditText)findViewById(R.id.editTextTextPersonName3);
        edtpass=(EditText)findViewById(R.id.editTextTextPassword);
        edtcompass=(EditText)findViewById(R.id.editTextTextPassword2);
        btnRegister=(Button)findViewById(R.id.editOK);

        std =new RegisterDB();

        dbRef = FirebaseDatabase.getInstance().getReference().child("RegisterDB");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid=(snapshot.getChildrenCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View arg0) {



                if(edtfname.getText().toString().trim().length()==0){
                    edtfname.setError("First Name is not entered");
                    edtfname.requestFocus();
                }

                if(edtlname.getText().toString().trim().length()==0){
                    edtlname.setError("Last Name is not entered");
                    edtlname.requestFocus();
                }
                if(edtemail.getText().toString().trim().length()==0){
                    edtemail.setError("Email is not entered");
                    edtemail.requestFocus();

                }


                if(edtpass.getText().toString().trim().length()==0){
                    edtpass.setError("Password is not entered");
                    edtpass.requestFocus();
                }
                if(edtcompass.getText().toString().trim().length()==0){
                    edtcompass.setError("Confirm Password is not entered");
                    edtcompass.requestFocus();
                }

                if(edtpass.getText().toString().trim().length() < 6){
                    edtpass.setError("Password must be >= 6 characters");
                    edtpass.requestFocus();
                }


                else if(edtfname.getText().toString().trim().length()!=0)if(edtlname.getText().toString().trim().length()!=0)
                    if(edtemail.getText().toString().trim().length()!=0) if(edtpass.getText().toString().trim().length()!=0)
                       if(edtcompass.getText().toString().trim().length()!=0) {

                           if(edtpass.getText().toString().equals(edtcompass.getText().toString())) {


                               std.setEmail(edtemail.getText().toString().trim());
                               std.setFname(edtfname.getText().toString().trim());
                               std.setLname(edtlname.getText().toString().trim());
                               std.setPass(edtpass.getText().toString().trim());
                               std.setCompass(edtcompass.getText().toString().trim());

                               dbRef.push().setValue(std);

                               //dbRef.child(String.valueOf(maxid)).setValue(std);


                               Toast.makeText(getApplicationContext(),"Data Saved", Toast.LENGTH_SHORT).show();

                               clearControls();

                               Intent intent = new Intent(Register.this,MainActivity.class);
                               startActivity(intent);

                           }
                           else{
                               edtcompass.setError("Not Matched With Password");
                               edtcompass.requestFocus();
                           }


                       }

            }

        });

    }



}