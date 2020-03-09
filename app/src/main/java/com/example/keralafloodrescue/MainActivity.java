package com.example.keralafloodrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button adminbtn,victimbtn,volunteerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminbtn = (Button) findViewById(R.id.adminBtn);
        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AdminLogin.class);
                startActivity(i);
            }
        });
        victimbtn = (Button) findViewById(R.id.victimBtn);
        victimbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),VictimReport.class);
                startActivity(i);
            }
        });
        volunteerbtn = (Button) findViewById(R.id.volunteerBtn);
        volunteerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),VolunteerLogin.class);
                startActivity(i);
            }
        });
    }
}
