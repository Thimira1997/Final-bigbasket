package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addfeedback extends AppCompatActivity {
    private Button Add,viewfeedback,Upd_Dele;
    private EditText txt1,txt3;


    DatabaseReference reff;
    Feedback feedback;

    long maxid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfeedback);

        Add = findViewById(R.id.addbtn);
        viewfeedback = findViewById(R.id.viewbtnfeedback);
        txt1 = findViewById(R.id.fname);
        txt3 = findViewById(R.id.ffeed);

        //Navigate to update and delete page

        Upd_Dele = findViewById(R.id.both);
        Upd_Dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Feedback_Details.class);
                startActivity(intent);
            }
        });


        feedback = new Feedback();
        reff = FirebaseDatabase.getInstance().getReference().child("Feedback");

        //set ID
        reff.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedback.setName(txt1.getText().toString().trim());
                feedback.setFeedback(txt3.getText().toString().trim());



                //set feedback ID
                reff.child(String.valueOf(maxid+1)).setValue(feedback);
                Toast.makeText(addfeedback.this,"Insert successfuly!!",Toast.LENGTH_LONG).show();

            }
        });



        viewfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FeedbackList.class);
                startActivity(intent);
            }
        });






    }

}