package com.example.nexttask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nexttask.pagerFragments.FirstFragment;
import com.example.nexttask.pagerFragments.PagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener, FirstFragment.DataPassListener {

    private DrawerLayout drawerLayout;
    private ViewPager2 viewPager2;
    private String city;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        city = intent.getStringExtra(ScreenActivity.EXTRA_TEXT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (city != null) {
            setTitle(city);
        }
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_main_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new PagerAdapter(this));

        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (grantPermission()) {
                getLocation();
                if(city != null) {
                    setTitle(city);
                }
            }
          swipeRefreshLayout.setRefreshing(false);
        });

        TabLayout tabLayout = findViewById(R.id.view_pager_tab);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 1) {
                tab.setText(R.string.second);
            } else tab.setText(R.string.first);
        });
        tabLayoutMediator.attach();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (item.getItemId() == R.id.settings) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            city = addressList.get(0).getLocality();

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
}

