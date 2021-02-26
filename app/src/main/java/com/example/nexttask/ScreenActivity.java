package com.example.nexttask;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenActivity extends AppCompatActivity implements LocationListener {

    public static final String EXTRA_TEXT = "com.example.nexttask";
    private LocationManager locationManager;
    private TextView city1;
    private String city;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        city1 = findViewById(R.id.city);
        city = "nothing";
        grantPermission();
        checkLocationEnableOrNot();
        getLocation();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(ScreenActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_TEXT, city);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5,  this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationEnableOrNot() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnable = false;
        boolean netEnable = false;
        try {
            gpsEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            netEnable = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e){
            e.printStackTrace();
        }

        if (!gpsEnable && !netEnable) {
            new AlertDialog.Builder(ScreenActivity.this)
                    .setTitle(R.string.alerttitle)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_positive, (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).setNegativeButton(R.string.alert_negative, null)
                    .show();

        }

    }

    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            city1.setText(addressList.get(0).getLocality());
            city = city1.getText().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}