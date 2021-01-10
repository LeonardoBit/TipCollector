package com.example.tipcollector.Notification;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.DialogPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceDialogFragmentCompat;

public class DialogPrefFragCompat extends PreferenceDialogFragmentCompat implements DialogPreference.TargetFragment {
    TimePicker timePicker = null;


    @Override
    protected View onCreateDialogView(Context context) {
        timePicker = new TimePicker(context);
        return (timePicker);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        timePicker.setIs24HourView(true);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
    }


    @Nullable
    @Override
    public <T extends Preference> T findPreference(@NonNull CharSequence key) {
        return null;
    }
}
