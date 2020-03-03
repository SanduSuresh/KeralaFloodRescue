package com.example.keralafloodrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class VolunteerLogin extends AppCompatActivity {
    Button signupbtn;
    CheckBox cbshowhide;
    EditText emailtxt,passtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_login);
        signupbtn = (Button) findViewById(R.id.signupBtn);
        emailtxt = (EditText) findViewById(R.id.emailText);
        passtxt = (EditText) findViewById(R.id.passwordText);
        cbshowhide = (CheckBox) findViewById(R.id.cbShowHide);

        cbshowhide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPasswordChange(buttonView,isChecked);
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
