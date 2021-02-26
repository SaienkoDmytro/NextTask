package com.example.nexttask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager2 viewPager2;
    private Toolbar toolbar;
    private String city;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        city = intent.getStringExtra(ScreenActivity.EXTRA_TEXT);

        toolbar = findViewById(R.id.toolbar);
        if (!city.equals("nothing")) {
            setTitle(city);
        }
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nvView);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new PagerAdapter(this));

        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {

          new getCity();
          setTitle(city);
          swipeRefreshLayout.setRefreshing(false);
        });

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.first);
            }
            if (position == 1) {
                tab.setText(R.string.second);
            }
        });
        tabLayoutMediator.attach();

    }

    public void next (View view){
        viewPager2.setCurrentItem(1, true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu) {
            Intent MainAct = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(MainAct);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (item.getItemId() == R.id.settings) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private class getCity implements LocationListener {

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
    }
}

