package com.example.nexttask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.example.nexttask.MainActivityMVP.MainContract;
import com.example.nexttask.MainActivityMVP.MainPresenter;
import com.example.nexttask.FirstFragmentMVP.FirstFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class MainActivity extends AppCompatActivity implements LocationListener, FirstFragment.DataPassListener, MainContract.View {

    private ViewPager2 viewPager2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private boolean isWiFiConn = false;
    private boolean isMobileConn = false;
    private String keyCity;
    private MainContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Network network : connectivityManager.getAllNetworks()){
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWiFiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }


        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Toast.makeText(MainActivity.this, "Connected by WIFI " + isWiFiConn + " and Mobile " + isMobileConn, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Toast.makeText(MainActivity.this, "Lost", Toast.LENGTH_SHORT).show();
            }
        };

        if (grantPermission()) {
            if (checkLocationEnableOrNot()) {
                getLocation();
            } else  new AlertDialog.Builder(this)
                    .setTitle(R.string.alertstart)
                    .setCancelable(false)
                    .setPositiveButton(R.string.start_activity_ok, null)
                    .show();
        }

        viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new PagerAdapter(this));

        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (grantPermission()) {
                getLocation();
                presenter.doWork();
            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            String city = addressList.get(0).getLocality();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLocation() {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5,  this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private boolean grantPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void passFirstData(int data) {
        viewPager2.setCurrentItem(data, true);
    }

    private boolean checkLocationEnableOrNot() {
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
            new AlertDialog.Builder(this)
                    .setTitle(R.string.alerttitle)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_positive, (dialog, which) -> startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1)).setNegativeButton(R.string.alert_negative, null)
                    .show();
            return false;
        } else return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            getLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    @Override
    public void permissionResult(String key) {
        keyCity = key;
    }

    @Override
    public void recievePermissions() {
        Toast.makeText(this, keyCity, Toast.LENGTH_SHORT).show();
    }
}

