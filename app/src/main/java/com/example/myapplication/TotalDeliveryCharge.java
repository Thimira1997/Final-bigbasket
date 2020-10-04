package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TotalDeliveryCharge extends AppCompatActivity {

    private Button PAY;
    TextView tv5,tv7;

    String s1;
    String s2 = "1000" ;

    int n1;
    int n2 = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_delivery_charge);



        tv7 = findViewById(R.id.textView11);
        tv5 = findViewById(R.id.textView22);

        Intent intent = getIntent();

        s1 = intent.getStringExtra("st");
        //s2 = intent.getStringExtra("st2");
        tv7.setText(s1);

        // tv3.setText(n2);
        n1 = Integer.parseInt(s1);
        n2 = Integer.parseInt(s2);

        //tv3.setText(s2);
        tv5.setText(s2 +" * " + s1 + " = " +(n2*n1));


    }
}