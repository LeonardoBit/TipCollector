package Analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tipcollector.R;
import com.google.android.material.tabs.TabLayout;

import Analysis.PagerAdapter.SectionPagerAdapter;

public class AnalysisFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_analysis,container,false);


        viewPager = v.findViewById(R.id.viewPager);
        tabLayout = v.findViewById(R.id.tabLayout);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private  void setUpViewPager(ViewPager viewPager){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CheckAverageDays(), "Check Average Days");
        adapter.addFragment(new CheckMonthDaysAverage(), "Check Average Months Days");

        viewPager.setAdapter(adapter);
    }
}
