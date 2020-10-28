package com.example.myassignmentnangcao.Pager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myassignmentnangcao.Dao.Dao_Course;
import com.example.myassignmentnangcao.Fragment.Adapter.adapterCourse;
import com.example.myassignmentnangcao.Other.lichPicker;
import com.example.myassignmentnangcao.Pager.Adapter.CourseAdapter;
import com.example.myassignmentnangcao.Model.Course;
import com.example.myassignmentnangcao.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class PagerCourse extends Fragment {
    private adapterCourse adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton btnfloat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_course,container,false);
        recyclerView = v.findViewById(R.id.recycler_course);
        LinearLayoutManager llm = new LinearLayoutManager(container.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        recyclerView.setHasFixedSize(true);
        btnfloat = v.findViewById(R.id.floating_course);

//        Dao_Course dao_course = new Dao_Course(container.getContext(),getView());


        FirebaseRecyclerOptions<Course> options =
                new FirebaseRecyclerOptions.Builder<Course>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Course"),Course.class)
                        .build();
        adapter = new adapterCourse(options,container.getContext());

        recyclerView.setAdapter(adapter);


        //them
        btnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcourse();

            }
        });


        return v;
    }

    //su kien them khoa hoc
    private void addcourse(){
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.dialog_course))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        final View holderView = dialog.getHolderView();

        final EditText makhoahoc = holderView.findViewById(R.id.makhoahoc);
        final EditText tenkhoahoc = holderView.findViewById(R.id.tenkhoahoc);
        final EditText mota = holderView.findViewById(R.id.mota);
        final EditText giangvien = holderView.findViewById(R.id.giangvien);
        final Button startday = holderView.findViewById(R.id.startday);
        final Button endday = holderView.findViewById(R.id.endday);
        final Button btnthem = holderView.findViewById(R.id.btnthem);
        final Button btnhuy = holderView.findViewById(R.id.btnclose);
        btnthem.setEnabled(true);
        holderView.setTag(dialog);
        makhoahoc.setText("KH01");
        tenkhoahoc.setText("cntt");
        mota.setText("ko biet");
        giangvien.setText("anh tuan");
//        startday.setText("2000/01/01");
//        endday.setText("2000/12/01");


        //nut bat dau ngay
        startday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new lichPicker(view);
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        //nut end day
        endday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new lichPicker(view);
                newFragment.show(getFragmentManager(), "timePicker2");
            }
        });

        //nut them
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mmakhoahoc = makhoahoc.getText().toString();
                String mtenkhoahoc = tenkhoahoc.getText().toString();
                String mgiangvien = giangvien.getText().toString();
                String mstartday = startday.getText().toString();
                String mendday = endday.getText().toString();
                String mmota = mota.getText().toString();

                if(TextUtils.isEmpty(mmakhoahoc)) {
                    makhoahoc.setError("Your message");
                    return;
                }
                if(TextUtils.isEmpty(mtenkhoahoc)) {
                    tenkhoahoc.setError("Your message");
                    return;
                }
                if(TextUtils.isEmpty(mmota)) {
                    mota.setError("Your message");
                    return;
                }
                if(TextUtils.isEmpty(mgiangvien)) {
                    giangvien.setError("Your message");
                    return;
                }

                Course course = new Course(mmakhoahoc,mtenkhoahoc,mgiangvien,mstartday,mendday,mmota);

                Dao_Course dao_course = new Dao_Course(getContext(),holderView);
                dao_course.insert(course);


            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        adapter.notifyDataSetChanged();

        dialog.show();
    }
    //end//



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }



    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
