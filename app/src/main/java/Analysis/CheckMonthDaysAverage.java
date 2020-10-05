package Analysis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tipcollector.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckMonthDaysAverage extends Fragment {

    public CheckMonthDaysAverage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_month_days_average, container, false);
    }
}
