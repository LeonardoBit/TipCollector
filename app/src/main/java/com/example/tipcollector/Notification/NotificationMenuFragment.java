package com.example.tipcollector.Notification;


import android.os.Build;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import androidx.preference.Preference;

import androidx.preference.PreferenceFragmentCompat;

import com.example.tipcollector.R;



public class NotificationMenuFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.notification_screen,rootKey);

        Toolbar toolbar = getActivity().findViewById(R.id.mainToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle(R.string.notification);
        }

    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        DialogFragment dialogFragment = null;
        if (preference instanceof TimePickerDialog) {
            dialogFragment = new DialogPrefFragCompat();
            Bundle bundle = new Bundle(1);
            bundle.putString("key", preference.getKey());
            dialogFragment.setArguments(bundle);
        }if(dialogFragment !=null){
            dialogFragment.setTargetFragment(this,0);
            dialogFragment.show(this.getFragmentManager(),null);
        } else super.onDisplayPreferenceDialog(preference);
    }

}
