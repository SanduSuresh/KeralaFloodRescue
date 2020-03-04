package com.example.keralafloodrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VolunteerLogin extends AppCompatActivity {
    Button signupbtn,loginbtn;
    CheckBox cbshowhide;
    EditText emailtxt,passtxt;

    int volunteer_count,flag=0;

    FirebaseDatabase mAuthDB = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_login);
        signupbtn = (Button) findViewById(R.id.signupBtn);
        loginbtn = (Button) findViewById(R.id.loginBtn);
        emailtxt = (EditText) findViewById(R.id.emailText);
        passtxt = (EditText) findViewById(R.id.passwordText);
        cbshowhide = (CheckBox) findViewById(R.id.cbShowHide);

        cbshowhide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPasswordChange(buttonView,isChecked);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volunteerLogin();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),VolunteerSignup.class);
                startActivity(i);
            }
        });
    }

    public void volunteerLogin() {
        DatabaseReference volunteerLoginRef = mAuthDB.getReference("volunteers");
        volunteerLoginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                volunteer_count = Integer.parseInt((String) dataSnapshot.child("count").getValue());

                for(int i=1;i<=volunteer_count;i++) {
                    if(dataSnapshot.child("volunteer:"+i).child("Email_Id").getValue().equals(emailtxt.getText().toString())
                            && dataSnapshot.child("volunteer:"+i).child("Password").getValue().equals(passtxt.getText().toString())) {

                        Intent intent = new Intent(getApplicationContext(),VolunteerHome.class);
                        startActivity(intent);
                        flag = 1;
                    }
                }
                if(flag!=1) {
                    Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error","VolunteerLoginError");
            }
        });
    }
    public void showPasswordChange(CompoundButton cButton,boolean isChecked)
    {
        // If Checkbox is checked then show password else hide password
        if (isChecked) {
            // change checkbox text
            cbshowhide.setText(R.string.hide_pwd);
            // show password
            passtxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else {
            // change checkbox text
            cbshowhide.setText(R.string.show_pwd);
            // hide password
            passtxt.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }
}
