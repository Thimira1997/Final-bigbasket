package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class Feedback_Details extends AppCompatActivity {
    private Button Delete, Update;
    private EditText ename, efeed;

    private String name;
    private String feedback;
    private String key;

    DatabaseReference db;
    Map<String,Object> updateMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback__details);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        feedback = getIntent().getStringExtra("feedback");

        //btn
        Update = (Button) findViewById(R.id.updatebtn);
        Delete = (Button) findViewById(R.id.deletebtn);

        ename = findViewById(R.id.editname);
        efeed = findViewById(R.id.editfeed);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseDatabase.getInstance().getReference();
                final Query updateQuery = db.child("Feedback").orderByChild("name").equalTo(ename.getText().toString().trim());

                String name = ename.getText().toString();
                String feedback = efeed.getText().toString();

                updateMap = new HashMap<String,Object>();
                updateMap.put("name",ename.getText().toString().trim());
                updateMap.put("feedback",efeed.getText().toString().trim());
                updateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot UpdateSnapshot : snapshot.getChildren()) {
                            UpdateSnapshot.getRef().updateChildren(updateMap);
                            Toast.makeText(getApplicationContext(), "Your feedback is Updated...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db = FirebaseDatabase.getInstance().getReference();
                        Query deleteQuery = db.child("Feedback").orderByChild("name").equalTo(ename.getText().toString().trim());

                        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                                    deleteSnapshot.getRef().removeValue();
                                    Toast.makeText(getApplicationContext(), "Successfully Removed Your Feedback...", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                });


            }

        });
    }
}