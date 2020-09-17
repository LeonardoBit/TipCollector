package com.example.tipcollector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "DatePickerFragment";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

       // Calendar c = Calendar.getInstance();
        LocalDate ld = LocalDate.now();

        int year = ld.getYear();
        int month = ld.getMonth().getValue()-1;
        int day = ld.getDayOfMonth();

        return new DatePickerDialog(getActivity(),DatePickerFragment.this,year,month,day);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        @SuppressLint("SimpleDateFormat") String selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

        Log.d(TAG,"onDateSet: " + selectedDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,new Intent().putExtra("selectedDate",selectedDate));

    }

}
