package com.example.tipcollector;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import database.DataBaseHelper;


public class SettingsFragment extends PreferenceFragmentCompat implements PreferenceManager.OnPreferenceTreeClickListener {



    public static final String PREF_HOURLY_RATE = "hourly_rate_key";
    public static final String PREF_CURRENCY = "currencies";

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if ("clear_all_key".equals(key)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("")
                    .setMessage("Are you sure you delete ALL RECORD from database")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    })
                    .setPositiveButton("Yes, i want delete all data", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                       DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(dbHelper.DAYS_TABLE,null,null);

                            Toast.makeText(getContext(),"Clearing",Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.show();
        }

        return true;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferencesscreen);
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if(key.equals(PREF_HOURLY_RATE)){
                    EditTextPreference hourlyRatePref = findPreference(key);
                    hourlyRatePref.setSummary(sharedPreferences.getString(key,""));
                }else if(key.equals(PREF_CURRENCY)){
                    ListPreference currencyPref = findPreference(key);
                    currencyPref.setSummary(sharedPreferences.getString(key,""));
                }

            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().
                registerOnSharedPreferenceChangeListener(preferenceChangeListener);


        EditTextPreference hourlyRatePref = findPreference(PREF_HOURLY_RATE);
        hourlyRatePref.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_HOURLY_RATE,"Choose your hourly rate"));
        hourlyRatePref.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setHint("Choose your hourly rate");
            }
        });

        ListPreference currencyPref = findPreference(PREF_CURRENCY);
        currencyPref.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_CURRENCY,""));
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().
                unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);

    }
}
