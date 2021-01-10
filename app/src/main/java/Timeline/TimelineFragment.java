package Timeline;


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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.example.tipcollector.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import database.DataBaseHelper;
import com.example.tipcollector.DayModel;


public class TimelineFragment extends Fragment {

    ListView daysList;
    ArrayAdapter daysArrayAdapter;
    DataBaseHelper db;
    TextView timeLineCash, timeLineCard, curView,total,totalTime;
    Button previous, next;
    long weekChanger = 0;
    long monthChanger = 0;


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);


        previous = v.findViewById(R.id.btnPrevious);
        next = v.findViewById(R.id.btnNext);
        daysList = v.findViewById(R.id.daysList);
        timeLineCash = v.findViewById(R.id.timeLineCash);
        timeLineCard = v.findViewById(R.id.timeLineCard);
        curView = v.findViewById(R.id.curView);
        total = v.findViewById(R.id.total);
        totalTime = v.findViewById(R.id.totalTime);


        curView.setText(R.string.all_records);
        allViewRecordsON();
        db = new DataBaseHelper(getActivity());
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(), android.R.layout.simple_list_item_1, db.getAll());
        daysList.setAdapter(daysArrayAdapter);


        if (allViewRecordsON()) {
            timeLineCash.setText("Cash "+db.sumOfAllCash());
            timeLineCard.setText("Card "+ db.sumOfAllCard());

            total.setText( String.valueOf(db.sumOfAllCash()+db.sumOfAllCard()));
            totalTime.setText("Total of all time");
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
                                    DayModel clickedDay = (DayModel) parent.getItemAtPosition(position);
                                    daysArrayAdapter.remove(clickedDay);

                                    db.deleteOne(clickedDay);
                                    daysArrayAdapter.notifyDataSetChanged();
                                    timeLineCash.setText("Cash " + db.sumOfAllCash());
                                    timeLineCard.setText("Card " + db.sumOfAllCard());
                                    total.setText(String.valueOf(db.sumOfAllCash()+db.sumOfAllCard()));
                                }

                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;

                }
            });
        }

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.timeline_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        LocalDate ld = LocalDate.now();

        switch (item.getItemId()) {
            case R.id.week_view:     // WEEK VIEW
                weekChanger = 0;

                showCurrentWeekDays(db);
                weekViewRecordsOn();
                timeLineCash.setText("Cash "+db.sumOfAllWeekCash());
                timeLineCard.setText("Card "+db.sumOfAllWeekCard());
                total.setText(String.valueOf(db.sumOfAllWeekCash()+db.sumOfAllWeekCard()));
                totalTime.setText("Total of week");

                int weekNumber = ld.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                LocalDate firstDayOfWeek = LocalDate.now()
                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                LocalDate lastDayOfWeek = LocalDate.now()
                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
                        .with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

                String firstAndLastDayOfWeek = firstDayOfWeek.toString() + " - " + lastDayOfWeek.toString();
                curView.setText(firstAndLastDayOfWeek);

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
                                        DayModel clickedDay = (DayModel) parent.getItemAtPosition(position);
                                        daysArrayAdapter.remove(clickedDay);

                                        db.deleteOne(clickedDay);
                                        daysArrayAdapter.notifyDataSetChanged();
                                        timeLineCash.setText("Cash "+ db.sumOfAllWeekCash());
                                        timeLineCard.setText("Card" + db.sumOfAllWeekCard());
                                        total.setText(String.valueOf(db.sumOfAllWeekCash()+db.sumOfAllWeekCard()));

                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;

                    }
                });

                if (showCurrentWeekDays(db)) {
                    previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            weekChanger--;
                            showPreviousNextWeekDays(db, weekChanger);
                        }
                    });

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            weekChanger++;
                            showPreviousNextWeekDays(db, weekChanger);
                        }
                    });
                }

                return true;


            case R.id.month_view:       // MONTH VIEW
                monthChanger = 0;
                showCurrentMonthDays(db);
                monthViewRecordsON();
                timeLineCash.setText("Cash " + db.sumOfAllMonthCash());
                timeLineCard.setText("Card " + db.sumOfAllMonthCard());
                total.setText(String.valueOf(db.sumOfAllMonthCash()+db.sumOfAllMonthCard()));
                totalTime.setText("Total of month");

                String month = ld.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
                final String monthDate = month + " " + ld.getYear();
                curView.setText(monthDate);

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
                                        DayModel clickedDay = (DayModel) parent.getItemAtPosition(position);
                                        daysArrayAdapter.remove(clickedDay);

                                        db.deleteOne(clickedDay);
                                        daysArrayAdapter.notifyDataSetChanged();
                                        timeLineCash.setText("Cash "+db.sumOfAllMonthCash());
                                        timeLineCard.setText("Card "+db.sumOfAllMonthCard());
                                        total.setText(String.valueOf(db.sumOfAllMonthCash()+db.sumOfAllMonthCard()));
                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;

                    }
                });


                previous.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        monthChanger--;
                        showPreviousNextMonthDays(db, monthChanger);

                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        monthChanger++;
                        showPreviousNextMonthDays(db, monthChanger);

                    }
                });

                return true;


            case R.id.all_view:     // ALL RECORDS
                showAllDays(db);
                allViewRecordsON();
                timeLineCash.setText("Cash " + db.sumOfAllCash());
                timeLineCard.setText("Card " + db.sumOfAllCard());
                total.setText( String.valueOf(db.sumOfAllCash()+db.sumOfAllCard()));

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
                                        DayModel clickedDay = (DayModel) parent.getItemAtPosition(position);
                                        daysArrayAdapter.remove(clickedDay);

                                        db.deleteOne(clickedDay);
                                        daysArrayAdapter.notifyDataSetChanged();
                                        timeLineCash.setText("Cash " + db.sumOfAllCash());
                                        timeLineCard.setText("Card " + db.sumOfAllCard());
                                        total.setText( String.valueOf(db.sumOfAllCash()+db.sumOfAllCard()));

                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;

                    }
                });

                return true;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void showAllDays(DataBaseHelper dataBaseHelper2) {
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(), android.R.layout.simple_list_item_1, db.getAll());
        daysList.setAdapter(daysArrayAdapter);
        curView.setText("All records");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean showCurrentWeekDays(DataBaseHelper dataBaseHelper) {
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(), android.R.layout.simple_list_item_1, db.getCurrentWeek());
        daysList.setAdapter(daysArrayAdapter);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean showCurrentMonthDays(DataBaseHelper dataBaseHelper) {
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(), android.R.layout.simple_list_item_1, db.getCurrentMonth());
        daysList.setAdapter(daysArrayAdapter);

        return true;
    }


    public boolean allViewRecordsON() {

        previous.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);


        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean monthViewRecordsON() {
        previous.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean weekViewRecordsOn() {

        previous.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showPreviousNextMonthDays(DataBaseHelper dataBaseHelper, long monthChange) {

        if (monthChange >= 0) {

            LocalDate ld = LocalDate.now();
            final LocalDate newDate = ld.plusMonths(monthChanger);
            daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(), android.R.layout.simple_list_item_1, db.previousMonth(newDate));
            daysList.setAdapter(daysArrayAdapter);
            String month = newDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
            String monthDate = month + " " + newDate.getYear();
            curView.setText(monthDate);
            timeLineCash.setText("Cash "+ db.sumOfAllPreviousNextMonthCash(newDate));
            timeLineCard.setText("Card" + db.sumOfAllPreviousNextMonthCard(newDate));
            total.setText(String.valueOf(db.sumOfAllPreviousNextMonthCash(newDate)+db.sumOfAllPreviousNextMonthCard(newDate)));


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
                                    DayModel clickedDay = (DayModel) parent.getItemAtPosition(position);
                                    daysArrayAdapter.remove(clickedDay);

                                    db.deleteOne(clickedDay);
                                    daysArrayAdapter.notifyDataSetChanged();
                                    timeLineCash.setText("Cash "+db.sumOfAllPreviousNextMonthCash(newDate));
                                    timeLineCard.setText("Card "+db.sumOfAllPreviousNextMonthCard(newDate));
                                    total.setText(String.valueOf(db.sumOfAllPreviousNextMonthCash(newDate)+db.sumOfAllPreviousNextMonthCard(newDate)));

                                }

                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;

                }
            });


        } else if (monthChange <= 0) {
            LocalDate ld = LocalDate.now();
            monthChange = Math.abs(monthChange);

            final LocalDate newDate = ld.minusMonths(monthChange);
            String month = newDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
            String monthDate = month + " " + newDate.getYear();

            daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(), android.R.layout.simple_list_item_1, db.nextMonth(newDate));
            daysList.setAdapter(daysArrayAdapter);
            curView.setText(monthDate);
            timeLineCash.setText("Cash "+db.sumOfAllPreviousNextMonthCash(newDate));
            timeLineCard.setText("Card "+ db.sumOfAllPreviousNextMonthCard(newDate));
            total.setText(String.valueOf(db.sumOfAllPreviousNextMonthCash(newDate)+db.sumOfAllPreviousNextMonthCard(newDate)));

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
                                    DayModel clickedDay = (DayModel) parent.getItemAtPosition(position);
                                    daysArrayAdapter.remove(clickedDay);

                                    db.deleteOne(clickedDay);
                                    daysArrayAdapter.notifyDataSetChanged();
                                    timeLineCash.setText("Cash "+ db.sumOfAllPreviousNextMonthCash(newDate));
                                    timeLineCard.setText("Card "+ db.sumOfAllPreviousNextMonthCard(newDate));
                                    total.setText(String.valueOf(db.sumOfAllPreviousNextMonthCash(newDate)+db.sumOfAllPreviousNextMonthCard(newDate)));


                                }

                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;

                }
            });

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showPreviousNextWeekDays(DataBaseHelper dataBaseHelper, long weekChange) {

        if (weekChange >= 0) {
            final LocalDate ld = LocalDate.now(ZoneId.systemDefault()).plusWeeks(weekChange);

            final long weekNumber = ld.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

             final LocalDate firstDayOfWeek = ld.with(DayOfWeek.MONDAY);
            final LocalDate lastDayOfWeek = ld.with(DayOfWeek.SUNDAY);


            String firstAndLastDayOfWeek = firstDayOfWeek.toString() + " - " + lastDayOfWeek.toString();
            daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(), android.R.layout.simple_list_item_1, db.NextWeek(firstDayOfWeek,lastDayOfWeek));
            daysList.setAdapter(daysArrayAdapter);

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
                                    DayModel clickedDay = (DayModel) parent.getItemAtPosition(position);
                                    daysArrayAdapter.remove(clickedDay);

                                    db.deleteOne(clickedDay);
                                    daysArrayAdapter.notifyDataSetChanged();
                                    timeLineCash.setText("Cash "+ db.sumOfAllPreviousNextWeekCash(firstDayOfWeek,lastDayOfWeek));
                                    timeLineCard.setText("Card "+ db.sumOfAllPreviousNextWeekCard(firstDayOfWeek,lastDayOfWeek));
                                    total.setText(String.valueOf(db.sumOfAllPreviousNextWeekCash(firstDayOfWeek,lastDayOfWeek)+db.sumOfAllPreviousNextWeekCard(firstDayOfWeek,lastDayOfWeek)));

                                }

                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;

                }
            });

            curView.setText(firstAndLastDayOfWeek);
            timeLineCash.setText("Cash "+ db.sumOfAllPreviousNextWeekCash(firstDayOfWeek,lastDayOfWeek));
            timeLineCard.setText("Card "+ db.sumOfAllPreviousNextWeekCard(firstDayOfWeek,lastDayOfWeek));
            total.setText(String.valueOf(db.sumOfAllPreviousNextWeekCash(firstDayOfWeek,lastDayOfWeek)+db.sumOfAllPreviousNextWeekCard(firstDayOfWeek,lastDayOfWeek)));



        } else if (weekChange < 0) {
            weekChange = Math.abs(weekChange);
            final LocalDate ld = LocalDate.now().minusWeeks(weekChange);



            final long weekNumber = ld.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            final LocalDate firstDayOfWeek = ld.with(DayOfWeek.MONDAY);
            final LocalDate lastDayOfWeek = ld.with(DayOfWeek.SUNDAY);

            String firstAndLastDayOfWeek = firstDayOfWeek.toString() + " - " + lastDayOfWeek.toString();
            daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(), android.R.layout.simple_list_item_1, db.previousWeek(firstDayOfWeek,lastDayOfWeek));
            daysList.setAdapter(daysArrayAdapter);


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
                                    DayModel clickedDay = (DayModel) parent.getItemAtPosition(position);
                                    daysArrayAdapter.remove(clickedDay);

                                    db.deleteOne(clickedDay);
                                    daysArrayAdapter.notifyDataSetChanged();
                                    timeLineCash.setText("Cash "+db.sumOfAllPreviousNextWeekCash(firstDayOfWeek,lastDayOfWeek));
                                    timeLineCard.setText("Card "+ db.sumOfAllPreviousNextWeekCard(firstDayOfWeek,lastDayOfWeek));
                                    total.setText(String.valueOf(db.sumOfAllPreviousNextWeekCash(firstDayOfWeek,lastDayOfWeek)+db.sumOfAllPreviousNextWeekCard(firstDayOfWeek,lastDayOfWeek)));

                                }

                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;

                }
            });


            curView.setText(firstAndLastDayOfWeek);
            timeLineCash.setText("Cash " + db.sumOfAllPreviousNextWeekCash(firstDayOfWeek,lastDayOfWeek));
            timeLineCard.setText("Card " + db.sumOfAllPreviousNextWeekCard(firstDayOfWeek,lastDayOfWeek));
            total.setText(String.valueOf(db.sumOfAllPreviousNextWeekCash(firstDayOfWeek,lastDayOfWeek)+db.sumOfAllPreviousNextWeekCard(firstDayOfWeek,lastDayOfWeek)));

        }


    }

}

