package com.example.tipcollector;

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

public class TimelineFragment extends Fragment {

    ListView daysList;
    ArrayAdapter daysArrayAdapter;
    DataBaseHelper db;







    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);





        daysList = v.findViewById(R.id.daysList);

        db = new DataBaseHelper(getActivity());
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(),android.R.layout.simple_list_item_1,db.getAll());
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
        switch (item.getItemId()) {
            case R.id.week_view:
            showCurrentWeekDays(db);
                return true;
            case R.id.month_view:
            showCurrentMonthDays(db);
                return true;
            case R.id.all_view:
                showAllDays(db);
                return true;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void showAllDays(DataBaseHelper dataBaseHelper2 ){
        daysArrayAdapter = new ArrayAdapter<DayModel>(getActivity(),android.R.layout.simple_list_item_1,db.getAll());
        daysList.setAdapter(daysArrayAdapter);
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

