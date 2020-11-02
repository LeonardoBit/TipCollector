package Analysis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tipcollector.R;

import database.DataBaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckMonthDaysAverage extends Fragment {

    DataBaseHelper db;
    TextView tvAverageMon;
    Button btnCheckMon;
    CheckBox cbJan, cbFeb, cbMar, cbApr, cbMay, cbJun, cbJul,cbAug,cbSep,cbOct,cbNov,cbDec;
    boolean jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec;
    int monthNum = 0;
    float avgMonSum = 0;
    int averageCounter = 0;
    public CheckMonthDaysAverage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_check_month_days_average, container, false);

        tvAverageMon = v.findViewById(R.id.tvAverageMon);
        btnCheckMon = v.findViewById(R.id.btnCheckMon);
        db = new DataBaseHelper(getContext());
        cbJan = v.findViewById(R.id.cbJan);
        cbFeb = v.findViewById(R.id.cbFeb);
        cbMar= v.findViewById(R.id.cbMar);
        cbApr= v.findViewById(R.id.cbApr);
        cbMay= v.findViewById(R.id.cbMay);
        cbJun= v.findViewById(R.id.cbJun);
        cbJul= v.findViewById(R.id.cbJul);
        cbAug= v.findViewById(R.id.cbAug);
        cbSep= v.findViewById(R.id.cbSep);
        cbOct= v.findViewById(R.id.cbOct);
        cbNov= v.findViewById(R.id.cbNov);
        cbDec= v.findViewById(R.id.cbDec);

        cbJan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbJan.isChecked()){
                    jan=true;
                    monthNum = 1;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    jan=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 1;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbFeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFeb.isChecked()){
                    feb=true;
                    monthNum = 2;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    feb=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 2;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbMar.isChecked()){
                    mar=true;
                    monthNum = 3;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;

                }else{
                    mar=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 3;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbApr.isChecked()){
                    apr=true;
                    monthNum = 4;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    apr=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 4;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbMay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbMay.isChecked()){
                    may=true;
                    monthNum = 5;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    may=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 5;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbJun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbJun.isChecked()){
                    jun=true;
                    monthNum = 6;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    jun=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 6;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbJul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbJul.isChecked()){
                    jul=true;
                    monthNum = 7;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    jul=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 7;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbAug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbAug.isChecked()){
                    aug=true;
                    monthNum = 8;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    aug=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 8;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbSep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbSep.isChecked()){
                    sep=true;
                    monthNum = 9;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    sep=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 9;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbOct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbOct.isChecked()){
                    oct=true;
                    monthNum = 10;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    oct=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 10;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbNov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbNov.isChecked()){
                    nov=true;
                    monthNum = 11;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    nov=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 11;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });
        cbDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbDec.isChecked()){
                    dec=true;
                    monthNum = 12;
                    avgMonSum += db.averageOfMonth(monthNum);
                    averageCounter++;
                }else{
                    dec=false;
                    if(averageCounter<=0){ averageCounter = 0; }else{averageCounter--;}
                    monthNum = 12;
                    avgMonSum -= db.averageOfMonth(monthNum);

                }
            }
        });


        btnCheckMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(averageCounter == 0){
                    tvAverageMon.setText("Please choose at least 1 Month");

                } else{
                    tvAverageMon.setText(String.valueOf(avgMonSum/averageCounter));

                }
            }
        });



        return v;
    }
}
