package PageMain;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tipcollector.R;

import AddDay.AddDayFragment;

public class MainPageFragment extends Fragment implements View.OnClickListener {

    Button btnAddDay;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_main_page,container,false);

        btnAddDay = v.findViewById(R.id.btnAddDay);
        btnAddDay.setOnClickListener(this);
        Toolbar toolbar = getActivity().findViewById(R.id.mainToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Tip Collector");
        }

    return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddDay:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new AddDayFragment()).addToBackStack(null);
                ft.commit();

        }
    }
}
