package com.example.myassignmentnangcao.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myassignmentnangcao.Other.MyAsynTask;
import com.example.myassignmentnangcao.R;

public class FragmentNews extends Fragment {
    ListView lv;
    String link;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listview_news,container,false);
        lv = v.findViewById(R.id.listview_news);
        link = "https://vnexpress.net/rss/suc-khoe.rss";
        MyAsynTask gandulieu = new MyAsynTask(getContext(),link,lv);
        gandulieu.execute();
        return v;
    }

}
