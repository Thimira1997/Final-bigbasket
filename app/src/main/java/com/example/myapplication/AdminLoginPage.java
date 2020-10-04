package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminLoginPage extends AppCompatActivity {

    private EditText eName;
    private EditText ePassword;
    private Button eLogin;
    private FloatingActionButton back;

    private final String Username = "Admin";
    private final String Password = "12345";

    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);

        eName = findViewById(R.id.etUName);
        ePassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        back = findViewById(R.id.fabBack);

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName = eName.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if(inputName.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(AdminLoginPage.this,"Please enter all the details correctly!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    isValid = validate(inputName,inputPassword);

                    if (!isValid) {
                        Toast.makeText(AdminLoginPage.this,"Incorrect Login Details!!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AdminLoginPage.this,"Login Successful!!",Toast.LENGTH_SHORT).show();
                        //navigate to Item Page
                        Intent intent =  new Intent(AdminLoginPage.this, ItemPage.class);
                        startActivity(intent);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(AdminLoginPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate (String name,String password){
        if(name.equals(Username) && password.equals(Password)){
            return true;
        }

        return false;
    }

}