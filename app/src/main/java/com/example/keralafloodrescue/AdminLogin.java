package com.example.keralafloodrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    Button loginbtn;
    EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        loginbtn = (Button) findViewById(R.id.loginBtn);
        email = (EditText) findViewById(R.id.emailText);
        pass = (EditText) findViewById(R.id.passwordText);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("admin")&&pass.getText().toString().equals("admin")) {
                    Intent i = new Intent(getApplicationContext(), AdminHome.class);
                    startActivity(i);
                }
            }
        });
    }
}
