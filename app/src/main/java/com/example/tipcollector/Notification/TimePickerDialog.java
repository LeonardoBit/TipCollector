package com.example.tipcollector.Notification;
import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.DialogPreference;






public class TimePickerDialog extends DialogPreference {

    public TimePickerDialog(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TimePickerDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimePickerDialog(Context context, AttributeSet attrs) {
        super(context, attrs,android.R.attr.dialogPreferenceStyle);
    }


    public TimePickerDialog(Context context) {
        super(context,null);
    }


}
