package com.example.keralafloodrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VictimReport extends AppCompatActivity {
    EditText nametxt,mobtxt,loctxt,disttxt;
    ImageButton locbtn;
    Button submitbtn;
    //CheckBox evaccheck,medcheck,foodcheck;
    boolean isName,isMob,isLoc,isDist;
    int victim_count;
    //Firebase Database instance creation
    private FirebaseDatabase mAuthDB = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_report);
        nametxt = (EditText) findViewById(R.id.nameText);
        mobtxt = (EditText) findViewById(R.id.mobText);
        loctxt = (EditText) findViewById(R.id.locText);
        disttxt = (EditText) findViewById(R.id.distText);
        submitbtn = (Button) findViewById(R.id.submitBtn);
        locbtn = (ImageButton) findViewById(R.id.locbtn);
        //evaccheck = (CheckBox) findViewById(R.id.checkBox);
        //medcheck = (CheckBox) findViewById(R.id.checkBox2);
        //foodcheck = (CheckBox) findViewById(R.id.checkBox3);

        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CurrentLocationMapsActivity.class);
                startActivity(i);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpValidation();
            }
        });
    }

    public void addVictimData() {
        //Database Reference Creation
        final DatabaseReference myRef = mAuthDB.getReference("victims");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Data reading and writing process are done based on internet speed
                //Taking the count of victims
                victim_count = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("count").getValue()));

                //Adding Data to victim
                DatabaseReference victimRef = myRef.child("victim:"+(victim_count+1));
                // Map<k,v> Stores Data in Key Value Pair
                Map<String,String> victimDetails = new HashMap<>();
                victimDetails.put("FullName",nametxt.getText().toString());
                victimDetails.put("Mobile_No",mobtxt.getText().toString());
                victimDetails.put("Location",loctxt.getText().toString());
                victimDetails.put("District",disttxt.getText().toString());

                victimRef.setValue(victimDetails);
                myRef.child("count").setValue(String.valueOf(victim_count+1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void signUpValidation() {
        final String name = nametxt.getText().toString();
        final String mob = mobtxt.getText().toString();
        final String loc = loctxt.getText().toString();
        final String dist = disttxt.getText().toString();

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
        if(dist.isEmpty()) {
            isDist = false;
            disttxt.requestFocus();
            disttxt.setError("FIELD CANNOT BE EMPTY");
        }
        else if(!name.matches("[a-zA-Z ]+")) {
            isDist = false;
            disttxt.requestFocus();
            disttxt.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }
        else {
            isDist = true;
        }

        if(isName && isMob && isLoc && isDist) {
            addVictimData();
        }
    }
}
