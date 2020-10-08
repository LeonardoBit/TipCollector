package com.example.tipcollector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import PageMain.MainPageFragment;

import com.google.android.material.navigation.NavigationView;

import Analysis.AnalysisFragment;
import Timeline.TimelineFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private  NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
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
                        new MainPageFragment()).addToBackStack(null).commit();
                setTitle(item.getTitle());
                break;
            case  R.id.nav_timeline:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TimelineFragment()).addToBackStack(null).commit();
                setTitle(item.getTitle());
                break;
            case  R.id.nav_analysis:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AnalysisFragment()).addToBackStack(null).commit();
                setTitle(item.getTitle());
                break;

            case R.id.nav_share:
                Toast.makeText(this,"shared somewhere",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_settings:

                startActivity(new Intent(this, SettingsActivity.class));


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
