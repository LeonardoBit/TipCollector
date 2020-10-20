package Analysis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tipcollector.R;

import java.time.LocalDate;
import java.util.ArrayList;

import database.DataBaseHelper;


public class CheckAverageDays extends Fragment {


    DataBaseHelper db;
    TextView tvAverage;
    Button btnCheck;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;

    boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    int averageCounter = 0;
    float daysSum = 0 ;





    public CheckAverageDays() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_check_average_days, container, false);

        db = new DataBaseHelper(getContext());
        checkBox1 = v.findViewById(R.id.checkBox1);
        checkBox2 = v.findViewById(R.id.checkBox2);
        checkBox3 = v.findViewById(R.id.checkBox3);
        checkBox4 = v.findViewById(R.id.checkBox4);
        checkBox5 = v.findViewById(R.id.checkBox5);
        checkBox6 = v.findViewById(R.id.checkBox6);
        checkBox7 = v.findViewById(R.id.checkBox7);
        tvAverage = v.findViewById(R.id.tvAverage);
        btnCheck = v.findViewById(R.id.btnCheck);





        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox1.isChecked()){
                    monday = true;
                    daysSum+=db.averageOfMondays();
                    averageCounter++;
                }else  {
                    monday = false;
                    daysSum-=db.averageOfMondays();
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                }
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox2.isChecked()){
                    tuesday = true;
                    daysSum+=db.averageOfTuesdays();
                    averageCounter++;
                }else {
                    tuesday = false;
                    daysSum-=db.averageOfTuesdays();
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                }
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox3.isChecked()){
                    wednesday = true;
                    daysSum+=db.averageOfWednesdays();
                    averageCounter++;
                }else {
                    wednesday = false;
                    daysSum-=db.averageOfWednesdays();
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox4.isChecked()){
                    thursday = true;
                    daysSum+=db.averageOfThursdays();
                    averageCounter++;
                }else {
                    thursday = false;
                    daysSum-=db.averageOfThursdays();
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                }
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox5.isChecked()){
                    friday = true;
                    daysSum+=db.averageOfFridays();
                    averageCounter++;
                }else {
                    friday = false;
                    daysSum-=db.averageOfFridays();
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                }
            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox6.isChecked()){
                    saturday = true;
                    daysSum+=db.averageOfSaturdays();
                    averageCounter++;
                }else {
                    saturday = false;
                    daysSum-=db.averageOfSaturdays();
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                }
            }
        });
        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox7.isChecked()){
                    sunday = true;
                    daysSum+=db.averageOfSundays();
                    averageCounter++;
                }else {
                    sunday = false;
                    daysSum-=db.averageOfSundays();
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                }
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(averageCounter == 0){
                    tvAverage.setText("Please choose at least 1 day");

                } else{
                    tvAverage.setText(String.valueOf(daysSum/averageCounter));

                }
            }
        });




        return v;
    }
}
