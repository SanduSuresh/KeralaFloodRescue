package com.example.keralafloodrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {
    Button loginbtn;
    EditText emailtxt,passtxt;
    boolean isEmail,isPass;



    //Firebase Database instance creation
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Database Reference  Creation
    DatabaseReference myRef = database.getReference("admins");

    String name;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        loginbtn = (Button) findViewById(R.id.loginBtn);
        emailtxt = (EditText) findViewById(R.id.emailText);
        passtxt = (EditText) findViewById(R.id.passwordText);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation();
                adminLogin();
            }
        });
    }

    public void loginValidation() {
        final String email = emailtxt.getText().toString();
        final String pass =passtxt.getText().toString();

        if(email.isEmpty()) {
            isEmail = false;
            emailtxt.requestFocus();
            emailtxt.setError("FIELD CANNOT BE EMPTY");
        }
        else if(!email.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")) {
            isEmail = false;
            emailtxt.requestFocus();
            emailtxt.setError("ENTER CORRECT EMAIL ID");
        }
        else {
            isEmail = true;
        }
        if(pass.isEmpty()) {
            isPass = false;
            passtxt.requestFocus();
            passtxt.setError("FIELD CANNOT BE EMPTY");
        }
        else {
            isPass = true;
        }
    }

    public void adminLogin() {
        //myRef.child("admin1").child("name").setValue("unni");
        //myRef.child("admin1").child("pass").setValue("123");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                name = (String) dataSnapshot.child("admin1").child("name").getValue();
                password = (String) dataSnapshot.child("admin1").child("pass").getValue();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Error","AdminLoginError");
            }
        });
        if(emailtxt.getText().toString().equals(name)&&passtxt.getText().toString().equals(password)) {
            Intent i = new Intent(getApplicationContext(), AdminHome.class);
            startActivity(i);
        }
    }
}
