package com.example.myassignmentnangcao.Pager.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myassignmentnangcao.Pager.PagerCourse;
import com.example.myassignmentnangcao.Pager.PagerStudent;

public class CourseAdapter extends FragmentStatePagerAdapter {
    public CourseAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0: frag = new PagerCourse();break;
            case 1: frag = new PagerStudent();break;


        }
        return frag;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0:title= "Course";break;
            case 1:title= "Student";break;
        }
        return title;
    }
}
