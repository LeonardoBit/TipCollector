package com.example.tipcollector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {

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
        setupDrawerContent(navigationView);




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

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(@NonNull MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (item.getItemId()){
            case  R.id.nav_mainPage:
                fragmentClass = MainPageFragment.class;
                break;
            case  R.id.nav_timeline:
                fragmentClass = TimelineFragment.class;
                break;
            case  R.id.nav_analysis:
                fragmentClass = AnalysisFragment.class;
                break;
            case R.id.nav_share:
                Toast.makeText(this,"shared somewhere",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this,"You are in settings",Toast.LENGTH_LONG).show();
                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
        item.setChecked(false);
        setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);


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
