package com.caqtus.bikemate;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {



    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;
    String locLat;
    String locLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates();
            }
        });

    }

    private void stopLocationUpdates() {
        System.out.println("stop updates");
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }


    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("Connected to googleApiClient");

        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (lastLocation != null){

            updateUI();


            startLocationUpdates();

        }




    }

    public void startLocationUpdates(){
        createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    public void createLocationRequest(){

        locationRequest = new LocationRequest();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("suspended googleApiClient");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("failed to connect googleApicleint");

    }





    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        updateUI();
    }

    private void updateUI() {
        locLat = String.valueOf(lastLocation.getLatitude());
        locLng = String.valueOf(lastLocation.getLongitude());
        System.out.println("location is " + locLat + " " + locLng);
        Toast.makeText(MainActivity.this, "location is " + locLat + " " + locLng, Toast.LENGTH_SHORT).show();

    }
}
