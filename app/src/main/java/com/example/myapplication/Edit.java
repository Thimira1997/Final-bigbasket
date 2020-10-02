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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Edit extends AppCompatActivity {


    EditText a,b,c,d,e;
    Button show1,update,delete;
    DatabaseReference dbRef,readRef;
    RegisterDB std;
    long maxid;
    String UN;
    Map<String,Object> updateMap;

    public void clearControls(){

        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");
        e.setText("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        a=(EditText) findViewById(R.id.t1);
        b=(EditText) findViewById(R.id.t2);
        c=(EditText) findViewById(R.id.t3);
        d=(EditText) findViewById(R.id.t4);
        e=(EditText) findViewById(R.id.t5);


        show1=(Button)findViewById(R.id.show);
        update=(Button)findViewById(R.id.show2);
        delete=(Button)findViewById(R.id.show4);

        Intent intent = getIntent();
        UN = intent.getStringExtra("UN");


        std =new RegisterDB();
        dbRef = FirebaseDatabase.getInstance().getReference().child("RegisterDB");


        show1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                    Query showQuery = dbRef.child("RegisterDB").orderByChild("email").equalTo(UN);

                showQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot showSnapshot : dataSnapshot.getChildren()) {
                                String fname = showSnapshot.child("fname").getValue().toString();
                                String lname = showSnapshot.child("lname").getValue().toString();
                                String email = showSnapshot.child("email").getValue().toString();
                                String pass = showSnapshot.child("pass").getValue().toString();
                                String compass = showSnapshot.child("compass").getValue().toString();

                                a.setText(fname);
                                b.setText(lname);
                                c.setText(email);
                                d.setText(pass);
                                e.setText(compass);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();


                        }
                    });

            }



        });

        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (d.getText().toString().equals(e.getText().toString())) {

                    if (d.getText().toString().trim().length() == 6) {


                        std.setEmail(c.getText().toString().trim());
                        std.setFname(a.getText().toString().trim());
                        std.setLname(b.getText().toString().trim());
                        std.setPass(d.getText().toString().trim());
                        std.setCompass(e.getText().toString().trim());


                        //dbRef.push().setValue(std);
                        readRef = FirebaseDatabase.getInstance().getReference();
                        Query showQuery2 = readRef.child("RegisterDB").orderByChild("email").equalTo(UN);

                        updateMap = new HashMap<String,Object>();
                        updateMap.put("email",c.getText().toString().trim());
                        updateMap.put("fname",a.getText().toString().trim());
                        updateMap.put("lname",b.getText().toString().trim());
                        updateMap.put("pass",d.getText().toString().trim());
                        updateMap.put("compass",e.getText().toString().trim());




                        showQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot updateSnapshot : dataSnapshot.getChildren()) {

                                    updateSnapshot.getRef().updateChildren(updateMap);
                                    Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    clearControls();

                                    

                                }


                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {


                                Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();


                            }


                        });


                    }


                } else {
                    d.setError("Password must be >= 6 characters");
                    d.requestFocus();

                }
            }







    });



        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference();
                Query deleteQuery = delRef.child("RegisterDB").orderByChild("email").equalTo(UN);

                deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                            deleteSnapshot.getRef().removeValue();

                            Toast.makeText(getApplicationContext(),"Suessfully Deleted",Toast.LENGTH_SHORT).show();
                            clearControls();

                            Intent intent = new Intent(Edit.this,MainActivity.class);
                            startActivity(intent);

                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                        Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();


                    }


                });




            }

        });
}


}