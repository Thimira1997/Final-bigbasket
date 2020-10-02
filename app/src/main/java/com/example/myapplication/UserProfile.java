package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class UserProfile extends AppCompatActivity {


    TextView a,b,c,d;
    Button show,edit;
    DatabaseReference dbRef;
    RegisterDB std;
    long maxid;
    String UN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        a=(TextView)findViewById(R.id.t1);
        b=(TextView)findViewById(R.id.t2);
        c=(TextView)findViewById(R.id.t3);
        d=(TextView)findViewById(R.id.t4);

        show=(Button)findViewById(R.id.show);
        edit=(Button)findViewById(R.id.edit);

        Intent intent = getIntent();
        UN = intent.getStringExtra("UN");


        std =new RegisterDB();


        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference();
                Query showQuery = dbRef.child("RegisterDB").orderByChild("email").equalTo(UN);
                showQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot showSnapshot : dataSnapshot.getChildren()) {
                            if (showSnapshot.exists()) {
                                String fname = showSnapshot.child("fname").getValue().toString();
                                String lname = showSnapshot.child("lname").getValue().toString();
                                String email = showSnapshot.child("email").getValue().toString();
                                String pass = showSnapshot.child("pass").getValue().toString();

                                a.setText(fname);
                                b.setText(lname);
                                c.setText(email);
                                d.setText(pass);
                            }

                        }
                    }

                        @Override
                        public void onCancelled (@NonNull DatabaseError error){
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();

                        }

                });
            }



        });


        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserProfile.this,Edit.class);
                intent.putExtra("UN",UN);
                startActivity(intent);

                a.setText("");
                b.setText("");
                c.setText("");
                d.setText("");

            }


        });



    }
}