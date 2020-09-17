package com.example.tipcollector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);




        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MainPageFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_mainPage);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.nav_mainPage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MainPageFragment()).commit();
                break;
            case  R.id.nav_timeline:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TimelineFragment()).commit();
                break;
            case  R.id.nav_analysis:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AnalysisFragment()).commit();
                break;
            case R.id.nav_share:
                Toast.makeText(this,"shared somewhere",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this,"You are in settings",Toast.LENGTH_LONG).show();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }


}
