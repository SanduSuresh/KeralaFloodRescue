package com.example.keralafloodrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class VolunteerSignup extends AppCompatActivity {
    EditText nametxt,emailtxt,mobtxt,loctxt,passtxt,cpasstxt;
    Button signupbtn;
    boolean isName,isEmail,isMob,isLoc,isPass,isCpass;
    int volunteer_count,flag;
    //Firebase Database instance creation
    private FirebaseDatabase mAuthDB = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_signup);
        nametxt = (EditText) findViewById(R.id.fullName);
        emailtxt = (EditText) findViewById(R.id.emailText);
        mobtxt = (EditText) findViewById(R.id.mobText);
        loctxt = (EditText) findViewById(R.id.locText);
        passtxt = (EditText) findViewById(R.id.passwordText);
        cpasstxt = (EditText) findViewById(R.id.cpasswordText);
        signupbtn = (Button) findViewById(R.id.signupBtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpValidation();
                addVolunteerData();

            }
        });
    }

    public void addVolunteerData() {
        //Database Reference Creation
        DatabaseReference myRef = mAuthDB.getReference("volunteers");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Taking the count of volunteers
                volunteer_count = Integer.parseInt((String) dataSnapshot.child("count").getValue());
                flag = 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Error","VolunteerSignUpError");
            }
        });

        //Adding Data to volunteer
        DatabaseReference volunteerRef = myRef.child("volunteer:"+(volunteer_count+1));
        // Map<k,v> Stores Data in Key Value Pair
        Map<String,String> volunteerDetails = new HashMap<>();
        volunteerDetails.put("FullName",nametxt.getText().toString());
        volunteerDetails.put("Email_Id",emailtxt.getText().toString());
        volunteerDetails.put("Mobile_No",mobtxt.getText().toString());
        volunteerDetails.put("Location",loctxt.getText().toString());
        volunteerDetails.put("Password",passtxt.getText().toString());

        volunteerRef.setValue(volunteerDetails);
        myRef.child("count").setValue(String.valueOf(volunteer_count+1));
    }

    public void signUpValidation()
    {
        final String name = nametxt.getText().toString();
        final String email = emailtxt.getText().toString();
        final String mob = mobtxt.getText().toString();
        final String loc = loctxt.getText().toString();
        final String pass =passtxt.getText().toString();
        final String cpass = cpasstxt.getText().toString();
        if(name.isEmpty()) {
            isName = false;
            nametxt.requestFocus();
            nametxt.setError("FIELD CANNOT BE EMPTY");
        }
        else if(!name.matches("[a-zA-Z ]+")) {
            isName = false;
            nametxt.requestFocus();
            nametxt.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }
        else {
            isName = true;
        }

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
        if(mob.isEmpty()) {
            isMob = false;
            mobtxt.requestFocus();
            mobtxt.setError("FIELD CANNOT BE EMPTY");
        }
        else if(mob.length() != 10) {
            isMob = false;
            mobtxt.requestFocus();
            mobtxt.setError("ENTER CORRECT MOB NO");
        }
        else if(!mob.matches("^[0-9]+$")){
            isMob = false;
            mobtxt.requestFocus();
            mobtxt.setError("ENTER NUMBERS ONLY");
        }
        else {
            isMob = true;
        }
        if(loc.isEmpty()) {
            isLoc = false;
            mobtxt.requestFocus();
            mobtxt.setError("FIELD CANNOT BE EMPTY");
        }
        else {
            isLoc = true;
        }
        if(pass.isEmpty()) {
            isPass = false;
            passtxt.requestFocus();
            passtxt.setError("FIELD CANNOT BE EMPTY");
        }
        else if (pass.length()<8) {
            isPass = false;
            passtxt.requestFocus();
            passtxt.setError("MINIMUM CHARACTERS IS 8");
        }
        else {
            isPass = true;
        }
        if(cpass.isEmpty()) {
            isCpass = false;
            cpasstxt.requestFocus();
            cpasstxt.setError("FIELD CANNOT BE EMPTY");
        }
        else if(!pass.equals(cpass)) {
            isCpass = false;
            cpasstxt.requestFocus();
            cpasstxt.setError("PASSWORDS DOES NOT MATCH");
        }
        else {
            isCpass = true;
        }
    }


}
