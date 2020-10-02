package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Home extends AppCompatActivity {

    Button btnLogout,profile;
    String UN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       btnLogout= (Button) findViewById(R.id.logout);
       profile= (Button) findViewById(R.id.profile);

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
    }
}