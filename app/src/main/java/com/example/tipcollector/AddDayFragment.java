package com.example.tipcollector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Locale;


public class AddDayFragment extends Fragment {



    //DATABASE

    //TextViews
    TextView mDisplayDate,tipSum;

    //Strings
    String selectedDate;

    //EditTexts
    EditText tipCash,tipCard;

    //Buttons
    Button btnOk;


    public static final int REQUEST_CODE = 11;



    TextWatcher sumCalculator = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!TextUtils.isEmpty(tipCash.getText().toString().trim()) || !TextUtils.isEmpty(tipCard.getText().toString().trim())) {
                int cashValue = TextUtils.isEmpty(tipCash.getText()) ? 0 : Integer.parseInt(tipCash.getText().toString().trim());
                int cardValue = TextUtils.isEmpty(tipCard.getText()) ? 0 : Integer.parseInt(tipCard.getText().toString().trim());

                int sumValue = cashValue + cardValue;

                tipSum.setText(String.valueOf(sumValue));
            } else {
                tipSum.setText("sum");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private void Transaction(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new MainPageFragment());
        ft.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode== REQUEST_CODE && resultCode == Activity.RESULT_OK){
            selectedDate=data.getStringExtra("selectedDate");
            mDisplayDate.setText(selectedDate);

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        showSoftwareKeyboard(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showSoftwareKeyboard(false);
    }

    private void showSoftwareKeyboard(boolean showKeyboard) {
        final Activity activity = getActivity();
        final InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), showKeyboard ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addday,container,false);




        // id declarations
        mDisplayDate= v.findViewById(R.id.mDisplayDate);
        tipCash = v.findViewById(R.id.tipCash);
        tipCard = v.findViewById(R.id.tipCard);
        tipSum = v.findViewById(R.id.tipSum);
        btnOk = v.findViewById(R.id.btnOk);
        final FragmentManager fm = getActivity().getSupportFragmentManager();




        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(AddDayFragment.this,REQUEST_CODE);
                datePicker.show(fm,"DatePickerFragment");
            }
        });

        tipCash.addTextChangedListener(sumCalculator);
        tipCard.addTextChangedListener(sumCalculator);




        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                DayModel dayModel = null;

                if(TextUtils.isEmpty(mDisplayDate.getText().toString())){

                    Toast.makeText(getActivity(),"Please choose date",Toast.LENGTH_SHORT).show();

                }
                else if (!TextUtils.isEmpty(mDisplayDate.getText().toString())){
                    if (TextUtils.isEmpty(tipCash.getText().toString()) && TextUtils.isEmpty(tipCard.getText().toString())){

                        Toast.makeText(getActivity(),"Day not added empty input",Toast.LENGTH_SHORT).show();

                    } else if(TextUtils.isEmpty(tipCash.getText().toString()) || TextUtils.isEmpty(tipCard.getText().toString())){

                        int cashValue = TextUtils.isEmpty(tipCash.getText()) ? 0 : Integer.parseInt(tipCash.getText().toString().trim());
                        int cardValue = TextUtils.isEmpty(tipCard.getText()) ? 0 : Integer.parseInt(tipCard.getText().toString().trim());
                        tipCash.setText(String.valueOf(cashValue));
                        tipCard.setText(String.valueOf(cardValue));


                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);
                        String test = mDisplayDate.getText().toString();

                        LocalDate mDate = LocalDate.parse(test,formatter);
                        Date date = Date.valueOf(String.valueOf(mDate));


                        dayModel = new DayModel(0, date, weekNumber(),
                                Integer.parseInt(tipCash.getText().toString()),
                                Integer.parseInt(tipCard.getText().toString()),
                                Integer.parseInt(tipSum.getText().toString()));

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                        dataBaseHelper.addOneDay(dayModel);
                        Toast.makeText(getActivity(),"Day added",Toast.LENGTH_SHORT).show();
                        Transaction();
                    }else if(!TextUtils.isEmpty(tipCash.getText().toString()) && !TextUtils.isEmpty(tipCard.getText().toString())) {

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);
                        String test = mDisplayDate.getText().toString();

                        LocalDate mDate = LocalDate.parse(test,formatter);
                        Date date = Date.valueOf(String.valueOf(mDate));


                        dayModel = new DayModel(0, date, weekNumber(),
                                Integer.parseInt(tipCash.getText().toString()),
                                Integer.parseInt(tipCard.getText().toString()),
                                Integer.parseInt(tipSum.getText().toString()));

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                        dataBaseHelper.addOneDay(dayModel);
                        Toast.makeText(getActivity(),"Day added",Toast.LENGTH_SHORT).show();
                        Transaction();

                    }

                } else {
                    Toast.makeText(getActivity(),"????",Toast.LENGTH_SHORT).show();
                }
            }
        });




        return v;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int weekNumber(){



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        String dateOfWeek = mDisplayDate.getText().toString();
        LocalDate ld = LocalDate.parse(dateOfWeek,formatter) ;
        int week = ld.get( IsoFields.WEEK_OF_WEEK_BASED_YEAR ) ;

        return week;
    }


}
