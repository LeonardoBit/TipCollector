package com.example.tipcollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {


    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        if(findViewById(R.id.settings_container)!=null){
            if(savedInstanceState!=null)
                return;
             getSupportFragmentManager().beginTransaction().replace(R.id.settings_container, new SettingsFragment()).commit();
        }




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}