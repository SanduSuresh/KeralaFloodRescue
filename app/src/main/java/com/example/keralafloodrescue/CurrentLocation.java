package com.example.keralafloodrescue;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class CurrentLocation extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        //Initialize fused location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Check Permission
        if(ActivityCompat.checkSelfPermission(CurrentLocation.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission granted
            //Call method
            getCurrentLocation();
        }
        else {
            //When permission denied
            //Request Permission
            ActivityCompat.requestPermissions(CurrentLocation.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

    private void getCurrentLocation() {
        //Initialize task location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                //When success
                if(location != null) {
                    //Sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Initialize Lat and Lng
                            LatLng latLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            //Create marker options
                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am here");
                            //Zoom Map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            //Add marker on map
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When permission granted
                //Call method
                getCurrentLocation();
            }
        }
    }
}
