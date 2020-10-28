package com.example.myassignmentnangcao.Pager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myassignmentnangcao.Dao.Dao_Course;
import com.example.myassignmentnangcao.Dao.Dao_Student;
import com.example.myassignmentnangcao.Fragment.Adapter.adapterCourse;
import com.example.myassignmentnangcao.Fragment.Adapter.studentAdapter;
import com.example.myassignmentnangcao.Model.Course;
import com.example.myassignmentnangcao.Model.Student;
import com.example.myassignmentnangcao.Other.lichPicker;
import com.example.myassignmentnangcao.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PagerStudent extends Fragment {
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    studentAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton btnfloat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_student,container,false);
        recyclerView = v.findViewById(R.id.recycler_student);
        LinearLayoutManager llm = new LinearLayoutManager(container.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        btnfloat = v.findViewById(R.id.floating_student);

        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Student"),Student.class)
                        .build();
        adapter = new studentAdapter(options,container.getContext());

        recyclerView.setAdapter(adapter);




        btnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addstudent();
            }
        });

        return v;
    }

    //su kien them sinh vien
    private void addstudent(){
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.dialog_student))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        final View holderView = dialog.getHolderView();

        final EditText masinhvien = holderView.findViewById(R.id.masinhvien);
        final EditText tensinhvien = holderView.findViewById(R.id.tensinhvien);
        final EditText gioitinh = holderView.findViewById(R.id.gioitinh);
        final EditText ngaysinh = holderView.findViewById(R.id.ngaysinh);
        final Spinner makhoahoc = holderView.findViewById(R.id.makhoahoc);
        final Button themsinhvien = holderView.findViewById(R.id.themsinhvien);
        final Button btndate = holderView.findViewById(R.id.btnngaysinh);
        final Button huy = holderView.findViewById(R.id.huy);



        //doc list spinner
        Dao_Student dao = new Dao_Student(getContext(),holderView);
        dao.readlist();

        masinhvien.setText("ps001");
        tensinhvien.setText("nguyen van a");
        gioitinh.setText("nam");
        ngaysinh.setText("2000-01-01");


        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = ngaysinh;
                DialogFragment newFragment = new lichPicker(view);
                newFragment.show(getFragmentManager(), "timePicker3");
            }
        });



        //nut them
        themsinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String mmasinhvien = masinhvien.getText().toString();
                    String mtensinhvien = tensinhvien.getText().toString();
                    String mgioitinh = gioitinh.getText().toString();
                    sdf.parse(ngaysinh.getText().toString());
                    String date = ngaysinh.getText().toString();
                    String mmakhoahoc = makhoahoc.getSelectedItem().toString();


                    Student student = new Student(mmasinhvien,mtensinhvien,date,mgioitinh,mmakhoahoc);
                    Dao_Student dao_student = new Dao_Student(getContext(),holderView);
                    dao_student.insert(student);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } catch (ParseException e) {
                    ngaysinh.setError("sai ngay sinh");
                }
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
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
