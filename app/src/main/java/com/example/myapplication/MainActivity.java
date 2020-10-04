package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText edtuser,edtpass,id;
    Button btnLogin,adminbtn;
    public Button button;
    DatabaseReference dbRef;
    RegisterDB std;
    long maxid;


    public void clearControls(){

        edtuser.setText("");
        edtpass.setText("");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


        TextView txtVMsg2 = findViewById(R.id.textView);
        txtVMsg2.setText(R.string.title);



        edtuser=(EditText)findViewById(R.id.Username);
        edtpass=(EditText)findViewById(R.id.Password);
        btnLogin=(Button)findViewById(R.id.login);

        button= (Button) findViewById(R.id.editOK);
        adminbtn = (Button)findViewById(R.id.button);
        std =new RegisterDB();

        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminLoginPage.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("RegisterDB");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot loginSnapshot: dataSnapshot.getChildren()) {

                            String email = loginSnapshot.child("email").getValue().toString();
                        String pass = loginSnapshot.child("pass").getValue().toString();

                        if (edtpass.getText().toString().trim().length() == 6) {


                            if (edtuser.getText().toString().equals(email) &&
                                    edtpass.getText().toString().equals(pass)) {


                                Toast.makeText(getApplicationContext(),
                                        "Login Sucessful", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(MainActivity.this, Home.class);
                                intent.putExtra("UN",email);
                                startActivity(intent);

                                clearControls();
                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();

                            }

                        } else if (edtuser.getText().toString().trim().length() == 0) {
                            edtuser.setError("Username is not entered");
                            edtuser.requestFocus();
                        } else if (edtpass.getText().toString().trim().length() == 0) {
                            edtpass.setError("Password is not entered");
                            edtpass.requestFocus();
                        } else if (edtpass.getText().toString().trim().length() != 6) {
                            edtpass.setError("Password must be >= 6 characters");
                            edtpass.requestFocus();
                        }
                    }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);

                clearControls();
            }
        });

    }



}
