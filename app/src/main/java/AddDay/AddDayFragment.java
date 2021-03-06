package AddDay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.preference.PreferenceManager;


import database.DataBaseHelper;
import com.example.tipcollector.DayModel;
import PageMain.MainPageFragment;

import com.example.tipcollector.R;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.Locale;


public class AddDayFragment extends Fragment {


    //DATABASE

    //TextViews
    TextView mDisplayDate,tipSum,hoursValue;

    //Strings
    String selectedDate;

    //EditTexts
    EditText tipCash,tipCard,hours;

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

                                        if(cashValue>999999){
                                            tipCash.setText("999999");
                                        }else if(cardValue>999999){
                                            tipCard.setText("999999");
                                        }

                                        cashValue = TextUtils.isEmpty(tipCash.getText()) ? 0 : Integer.parseInt(tipCash.getText().toString().trim());
                                        cardValue = TextUtils.isEmpty(tipCard.getText()) ? 0 : Integer.parseInt(tipCard.getText().toString().trim());

                                int sumValue = cashValue + cardValue ;
                                tipSum.setText(String.valueOf(sumValue));

                } else {
                    tipSum.setText("sum");

                }
            }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher hoursCalculator = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(!TextUtils.isEmpty(hours.getText().toString())){
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                String hourRatePref = sharedPref.getString("hourly_rate_key","");
                if(TextUtils.isEmpty(hourRatePref)){
                    Toast.makeText(getActivity(),"Please choose your hour rate in settings ",Toast.LENGTH_SHORT).show();
                }else{
                    if(Float.parseFloat(hours.getText().toString())>24){

                        hours.setText("24");
                    }else{
                        float hourRate = Float.parseFloat(hourRatePref);
                        float hValue = Float.parseFloat(hours.getText().toString()) * hourRate;

                        hoursValue.setText(String.valueOf(hValue));
                    }
                }


            }else {
                hours.setHint("Hours");
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
        hideKeyboard();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hideKeyboard();

    }

    void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = getActivity().getCurrentFocus();

        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addday,container,false);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        final String hourRate = sharedPref.getString("hourly_rate_key","");
        String currency = sharedPref.getString("currencies","");




        // id declarations
        hoursValue = v.findViewById(R.id.hoursValue);
        mDisplayDate= v.findViewById(R.id.mDisplayDate);
        tipCash = v.findViewById(R.id.tipCash);
        tipCard = v.findViewById(R.id.tipCard);
        tipSum = v.findViewById(R.id.tipSum);
        btnOk = v.findViewById(R.id.btnOk);
        hours = v.findViewById(R.id.hours);

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
            hours.addTextChangedListener(hoursCalculator);








        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                DayModel dayModel = null;

                if(TextUtils.isEmpty(mDisplayDate.getText().toString())){

                    Toast.makeText(getActivity(),"Please choose date",Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(hours.getText().toString())){

                    Toast.makeText(getActivity(),"Please enter hours of work ",Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(hourRate)){

                    Toast.makeText(getActivity(),"Please choose your hour rate in settings ",Toast.LENGTH_SHORT).show();

                }else if (!TextUtils.isEmpty(mDisplayDate.getText().toString())){
                     if (!TextUtils.isEmpty(hours.getText().toString()) && (TextUtils.isEmpty(tipCash.getText().toString()) && TextUtils.isEmpty(tipCard.getText().toString()))){
                         if(Float.parseFloat(hours.getText().toString())>24) {
                             Toast.makeText(getActivity(),"Day have only 24 hours",Toast.LENGTH_SHORT).show();
                         }
                         else {
                             int cashValue = TextUtils.isEmpty(tipCash.getText()) ? 0 : Integer.parseInt(tipCash.getText().toString().trim());
                             int cardValue = TextUtils.isEmpty(tipCard.getText()) ? 0 : Integer.parseInt(tipCard.getText().toString().trim());
                             tipCash.setText(String.valueOf(cashValue));
                             tipCard.setText(String.valueOf(cardValue));

                             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                             String test = mDisplayDate.getText().toString();

                             LocalDate mDate = LocalDate.parse(test, formatter);
                             Date date = Date.valueOf(String.valueOf(mDate));

                             dayModel = new DayModel(0, date, weekNumber(),
                                     Integer.parseInt(tipCash.getText().toString()),
                                     Integer.parseInt(tipCard.getText().toString()),
                                     Integer.parseInt(tipSum.getText().toString()),
                                     Float.parseFloat(hours.getText().toString()),
                                     Float.parseFloat(hoursValue.getText().toString()));

                             DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                             dataBaseHelper.addOneDay(dayModel);
                             Toast.makeText(getActivity(), "Day added", Toast.LENGTH_SHORT).show();
                             Transaction();}



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
                                Integer.parseInt(tipSum.getText().toString()),
                                Float.parseFloat(hours.getText().toString()),
                                Float.parseFloat(hoursValue.getText().toString()));

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
                                Integer.parseInt(tipSum.getText().toString()),
                                Float.parseFloat(hours.getText().toString()),
                                Float.parseFloat(hoursValue.getText().toString()));

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
