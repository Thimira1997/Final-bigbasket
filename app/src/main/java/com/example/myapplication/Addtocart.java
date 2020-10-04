package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Addtocart extends AppCompatActivity {

    public static final String EXTRA_NUMBER="com.example.intentsproj.EXTRA_NUMBER";
    public static final String EXTRA_NUMBER2="com.example.intentsproj.EXTRA_NUMBER2";

    Button button;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        OnclickButtonLister();
    }

    public void OnclickButtonLister(){
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li=getLayoutInflater();
                View layout = li.inflate(R.layout.custom_toast, (ViewGroup)
                        findViewById(R.id.custom_toast_layout));

                TextView toastText=layout.findViewById(R.id.custom_toast_message);
                toastText.setText(("Your Bill is ready...."));
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
                toast.setView(layout);
                toast.show();

                EditText editText1=(EditText) findViewById(R.id.eN1);
                EditText editText2=(EditText) findViewById(R.id.eN2);
                int number2=Integer.parseInt(editText2.getText().toString());
                int number1=Integer.parseInt(editText1.getText().toString());

                Intent intent=new Intent(Addtocart.this,SecondActivity.class);
                intent.putExtra(EXTRA_NUMBER,number1);
                intent.putExtra(EXTRA_NUMBER2,number2);
                startActivity(intent);

            }
        });
    }
}