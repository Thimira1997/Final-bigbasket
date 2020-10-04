package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class Home extends AppCompatActivity {

    Button btnLogout;
    ImageButton profile,delivery,store,feedback;
    String UN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       btnLogout= (Button) findViewById(R.id.logout);
       profile= findViewById(R.id.btnProfile);
       delivery= findViewById(R.id.btnDelivery);
       store= findViewById(R.id.btnStore);
       feedback= findViewById(R.id.btnFeed);

        Intent getintent = getIntent();
        UN = getintent.getStringExtra("UN");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),
                        "You are Logout",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),
                        //"You are Logout",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Home.this,UserProfile.class);
                intent.putExtra("UN",UN);
                startActivity(intent);
            }
        });

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, UserItemPage.class);
                startActivity(intent);
            }
        });

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, DeliveryDetails.class);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, addfeedback.class);
                startActivity(intent);
            }
        });

    }
}