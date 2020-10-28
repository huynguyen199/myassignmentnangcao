package com.example.myassignmentnangcao.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myassignmentnangcao.Pager.Adapter.CourseAdapter;
import com.example.myassignmentnangcao.R;
import com.google.android.material.tabs.TabLayout;

public class FragmentCourse extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_school_black_24dp,
            R.drawable.ic_person_pin_black_24dp,
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course,container,false);
        viewPager = v.findViewById(R.id.pager);
        tabLayout = v.findViewById(R.id.tab_layout);
        CourseAdapter adapter = new CourseAdapter(getActivity().getSupportFragmentManager(),0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        return v;
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


}
