package com.example.keralafloodrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class AdminLogin extends AppCompatActivity {
    Button loginbtn;
    EditText emailtxt,passtxt;
    CheckBox cbshowhide;
    boolean isEmail,isPass;

    private static Animation shakeAnimation;

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
        cbshowhide = (CheckBox) findViewById(R.id.cbShowHide);

        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);

        cbshowhide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPasswordChange(buttonView,isChecked);
            }
        });
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
        //myRef.child("admin1").child("name").setValue("admin@admin.com");
        //myRef.child("admin1").child("pass").setValue("admin123");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(emailtxt.getText().toString().equals(dataSnapshot.child("admin1").child("name").getValue().toString())&&passtxt.getText().toString().equals(dataSnapshot.child("admin1").child("pass").getValue().toString())) {
                    Intent i = new Intent(getApplicationContext(), AdminHome.class);
                    startActivity(i);
                }
                else {
                    loginbtn.startAnimation(shakeAnimation);
                    Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Error","AdminLoginError");
            }
        });
        Log.d("Checking","check : "+password);

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
