package com.example.tipcollector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

public class TimelineFragment extends Fragment {

    ListView daysList;
    ArrayAdapter daysArrayAdapter;
    DataBaseHelper db;
    TextView timeLineCash,timeLineCard,curView;








    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);





        daysList = v.findViewById(R.id.daysList);
        timeLineCash = v.findViewById(R.id.timeLineCash);
        timeLineCard = v.findViewById(R.id.timeLineCard);
        curView = v.findViewById(R.id.curView);

        curView.setText("All records");
        db = new DataBaseHelper(getActivity());
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(),android.R.layout.simple_list_item_1,db.getAll());
        daysList.setAdapter(daysArrayAdapter);


        timeLineCash.setText(String.valueOf(db.sumOfAllCash()));
        timeLineCard.setText(String.valueOf(db.sumOfAllCard()));


        daysList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("")
                        .setMessage("Are you sure you want to remove this day?")
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DayModel clickedDay = (DayModel)parent.getItemAtPosition(position);
                                daysArrayAdapter.remove(clickedDay);

                                db.deleteOne(clickedDay);
                                daysArrayAdapter.notifyDataSetChanged();

                            }

                        });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });


        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.timeline_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        LocalDate ld = LocalDate.now();

        switch (item.getItemId()) {
            case R.id.week_view:              // WEEK VIEW
                 showCurrentWeekDays(db);


                int weekNumber = ld.get( IsoFields.WEEK_OF_WEEK_BASED_YEAR ) ;
                LocalDate firstDayOfWeek = LocalDate.now()
                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                LocalDate lastDayOfWeek = LocalDate.now()
                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
                        .with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

                String firstAndLastDayOfWeek = firstDayOfWeek.toString() + " - " + lastDayOfWeek.toString();






                daysList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("")
                                .setMessage("Are you sure you want to remove this day?")
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                })
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DayModel clickedDay = (DayModel)parent.getItemAtPosition(position);
                                        daysArrayAdapter.remove(clickedDay);

                                        db.deleteOne(clickedDay);
                                        daysArrayAdapter.notifyDataSetChanged();
                                        timeLineCash.setText(String.valueOf(db.sumOfAllWeekCash()));
                                        timeLineCard.setText(String.valueOf(db.sumOfAllWeekCard()));

                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;

                    }
                });
                    curView.setText(firstAndLastDayOfWeek);
                    timeLineCash.setText(String.valueOf(db.sumOfAllWeekCash()));
                    timeLineCard.setText(String.valueOf(db.sumOfAllWeekCard()));
                return true;



                case R.id.month_view:       // MONTH VIEW
                  showCurrentMonthDays(db);

                    String curMonth = String.valueOf(ld.getMonth());




                daysList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("")
                                .setMessage("Are you sure you want to remove this day?")
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                })
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DayModel clickedDay = (DayModel)parent.getItemAtPosition(position);
                                        daysArrayAdapter.remove(clickedDay);

                                        db.deleteOne(clickedDay);
                                        daysArrayAdapter.notifyDataSetChanged();
                                        timeLineCash.setText(String.valueOf(db.sumOfAllMonthCash()));
                                        timeLineCard.setText(String.valueOf(db.sumOfAllMonthCard()));

                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;

                    }
                });








                    curView.setText(curMonth);
                    timeLineCash.setText(String.valueOf(db.sumOfAllMonthCash()));
                    timeLineCard.setText(String.valueOf(db.sumOfAllMonthCard()));
                return true;





            case R.id.all_view:     // ALL RECORDS
                 showAllDays(db);

                daysList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("")
                                .setMessage("Are you sure you want to remove this day?")
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                })
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DayModel clickedDay = (DayModel)parent.getItemAtPosition(position);
                                        daysArrayAdapter.remove(clickedDay);

                                        db.deleteOne(clickedDay);
                                        daysArrayAdapter.notifyDataSetChanged();
                                        timeLineCash.setText(String.valueOf(db.sumOfAllCash()));
                                        timeLineCard.setText(String.valueOf(db.sumOfAllCard()));

                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;

                    }
                });




                    timeLineCash.setText(String.valueOf(db.sumOfAllCash()));
                    timeLineCard.setText(String.valueOf(db.sumOfAllCard()));
                return true;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void showAllDays(DataBaseHelper dataBaseHelper2 ){
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(),android.R.layout.simple_list_item_1,db.getAll());
        daysList.setAdapter(daysArrayAdapter);
        curView.setText("All records");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showCurrentWeekDays(DataBaseHelper dataBaseHelper){
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(),android.R.layout.simple_list_item_1,db.getCurrentWeek());
        daysList.setAdapter(daysArrayAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showCurrentMonthDays(DataBaseHelper dataBaseHelper){
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(),android.R.layout.simple_list_item_1,db.getCurrentMonth());
        daysList.setAdapter(daysArrayAdapter);

    }

}

