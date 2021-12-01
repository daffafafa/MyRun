package com.daffa.myrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import org.w3c.dom.Text;

//implements google map API to provide users with visual information
public class RunActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    Chronometer runTime;

    LatLng currLoc;
    LatLng prevLoc;
    Location previousLocation;
    float totalDistance = 0;

    String speedString;
    float distancekm;
    float maxSpeed;
    long totalTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener locationListener = new MyLocationListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1, // minimum time interval between updates
                    2, // minimum distance between updates, in metres
                    locationListener);
        } catch (SecurityException e) {
            Log.d("g53mdp", e.toString());
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        map = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        map.setMyLocationEnabled(true);

        runTime = (Chronometer) findViewById(R.id.runTime);
        runTime.setBase(SystemClock.elapsedRealtime());
        runTime.start();


    }

    //opens activity to finalise run and add record to database
    public void finishRun(View view){
        runTime.stop();
        totalTime=SystemClock.elapsedRealtime()-runTime.getBase();

        Intent RecordSummary = new Intent(this, Summary.class);

        RecordSummary.putExtra("totalTime", totalTime);
        RecordSummary.putExtra("totalDistance", totalDistance);
        RecordSummary.putExtra("maxSpeed", maxSpeed);

        startActivity(RecordSummary);
    }


    //location listener to handle when location changes
    public class MyLocationListener implements LocationListener {
        TextView speedValue = (TextView) findViewById(R.id.SpeedValue);
        TextView distanceValue =(TextView) findViewById(R.id.DistanceValue);


        @Override
        public void onLocationChanged(Location location) {
            Log.d("g53mdp", location.getLatitude() + " " + location.getLongitude() + " " + maxSpeed);

            currLoc = new LatLng(location.getLatitude(), location.getLongitude());


            if(prevLoc != null) {
                Polyline line = map.addPolyline(new PolylineOptions()
                        .add(prevLoc, currLoc)
                        .width(10)
                        .color(Color.BLUE));
            }

            if(previousLocation != null) {
                totalDistance+=location.distanceTo(previousLocation);
            }

            previousLocation = location;

            if(location.getSpeed()>maxSpeed){
                maxSpeed = location.getSpeed();
            }

            speedString = String.format("%.2f m/s", location.getSpeed());

            speedValue.setText(speedString);

            if(totalDistance>=1000){
                distancekm = totalDistance/1000;

                distanceValue.setText(String.format("%.2f km", distancekm));
            }
            else{
                distanceValue.setText(String.format("%.2f m", totalDistance));
            }

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currLoc, 15));
            prevLoc = currLoc;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
// information about the signal, i.e. number of satellites
            Log.d("g53mdp", "onStatusChanged: " + provider + " " + status);
        }
        @Override
        public void onProviderEnabled(String provider) {
// the user enabled (for example) the GPS
            Log.d("g53mdp", "onProviderEnabled: " + provider);
        }
        @Override
        public void onProviderDisabled(String provider) {
// the user disabled (for example) the GPS
            Log.d("g53mdp", "onProviderDisabled: " + provider);
        }
    }


}